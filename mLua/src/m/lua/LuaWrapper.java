package m.lua;

import java.util.HashMap;
import java.util.Map.Entry;

public class LuaWrapper {
	private HashMap<String, Object> objects;
	private HashMap<String, JavaFunction> functions;
	private SourceLoader loader;
	private Lua lua;
	
	public LuaWrapper() {
		objects = new HashMap<String, Object>();
		functions = new HashMap<String, JavaFunction>();
	}
	
	public void setSourceLoader(SourceLoader loader) {
		this.loader = loader;
	}
	
	public void addJavaObject(String name, Object object) {
		objects.put(name, object);
	}
	
	public void addJavaFunction(String name, JavaFunction function) {
		functions.put(name, function);
	}
	
	public synchronized void start(String path) throws Throwable {
		if (lua == null) {
			openLua();
			overwriteRequire();
			pushJavaObjects();
			pushJavaFunctions();
			eval(path);
		}
	}
	
	private void openLua() throws Throwable {
		lua = new Lua();
		lua.open();
		lua.openLibs();
	}
	
	private void overwriteRequire() throws Throwable {
		JavaFunction requireImpl = new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String name = (String) args[0];
				
				byte[] bytes = null;
				if (loader != null) {
					bytes = loader.loadFile(name);
				}
				
				if (bytes == null) {
					throw new Throwable("Cannot load module " + name);
				} else {
					lua.LloadBuffer(bytes, bytes.length, name);
				}
				
				return name;
			}
		};
		
		lua.getGlobal("package");
		lua.getField(-1, "loaders");
		int nLoaders = lua.objlen(-1);
		
		lua.pushJavaFunction(requireImpl);
		lua.rawSetI(-2, nLoaders + 1);
		lua.pop(1);
	}
	
	private void pushJavaObjects() throws Throwable {
		for (Entry<String, Object> ent : objects.entrySet()) {
			lua.pushJavaObject(ent.getValue());
			lua.setGlobal(ent.getKey());
		}
		objects.clear();
	}
	
	private void pushJavaFunctions() throws Throwable {
		for (Entry<String, JavaFunction> ent : functions.entrySet()) {
			lua.pushJavaFunction(ent.getValue());
			lua.setGlobal(ent.getKey());
		}
		functions.clear();
	}
	
	private void eval(String path) throws Throwable {
		byte[] script = null;
		if (loader != null) {
			script = loader.loadFile(path);
		}
		if (script == null) {
			throw new Throwable();
		}
		
		int ok = lua.LloadString(new String(script, "utf-8"));
		if (ok == 0) {
			lua.getGlobal("debug");
			lua.getField(-1, "traceback");
			lua.remove(-2);
			lua.insert(-2);
			ok = lua.pcall(0, 0, -2);
			if (ok == 0) {
				return;
			}
		}
		
		String msg = lua.toString(-1);
		throw new Throwable(errorReason(ok) + ": " + msg);
	}
	
	private String errorReason(int error) {
		switch (error) {
			case 4: return "Out of memory";
			case 3: return "Syntax error";
			case 2: return "Runtime error";
			case 1: return "Yield error";
		}
		return "Unknown error " + error;
	}
	
	public synchronized void stop() {
		if (lua != null) {
			lua.close();
			lua = null;
		}
	}
	
}
