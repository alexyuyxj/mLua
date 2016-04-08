package m.lua;

public class LuaObject {
	int ref;
	Lua lua;
	
	public int type() {
		lua.rawGetI(Lua.LUA_REGISTRYINDEX, ref);
		int type = lua.type(-1);
		lua.pop(1);
		return type;
	}
	
	public LuaObject getField(String fieldName) {
		lua.rawGetI(Lua.LUA_REGISTRYINDEX, ref);
		lua.pushString(fieldName);
		lua.getTable(-2);
		lua.remove(-2);
		lua.pushValue(-1);
		int ref = lua.Lref(Lua.LUA_REGISTRYINDEX);;
		lua.pop(1);
		
		LuaObject field = new LuaObject();
		field.lua = lua;
		field.ref = ref;
		return field;
	}
	
	public Object call(Object[] args, int nres) throws Throwable {
		synchronized (lua) {
			if (type() != Lua.LUA_TFUNCTION) {
				throw new Throwable("Invalid object. Not a function, table or userdata .");
			}
			
			int top = lua.getTop();
			lua.rawGetI(Lua.LUA_REGISTRYINDEX, ref);
			int nargs;
			if (args != null) {
				nargs = args.length;
				for (int i = 0; i < nargs; i++) {
					pushObjectValue(args[i]);
				}
			} else {
				nargs = 0;
			}

			int err = lua.pcall(nargs, nres, 0);
			if (err != 0) {
				String str;
				if (lua.isString(-1)) {
					str = lua.toString(-1);
					lua.pop(1);
				} else {
					str = "";
				}
				
				if (err == Lua.LUA_ERRRUN) {
					throw new Throwable("Runtime error. " + str);
				} else if (err == Lua.LUA_ERRMEM) {
					throw new Throwable("Memory allocation error. " + str);
				} else if (err == Lua.LUA_ERRERR) {
					throw new Throwable("Error while running the error handler function. " + str);
				} else {
					throw new Throwable("Lua Error code " + err + ". " + str);
				}
			}

			if (nres == Lua.LUA_MULTRET) {
				nres = lua.getTop() - top;
			}
			
			if (lua.getTop() - top < nres) {
				throw new Throwable("Invalid Number of Results .");
			}

			Object[] res = new Object[nres];
			for (int i = nres; i > 0; i--) {
				res[i - 1] = lua.toJavaObject(-1);
				lua.pop(1);
			}
			if (nres > 0) {
				return res[0];
			} else {
				return null;
			}
		}
	}
	
	private void pushObjectValue(Object obj) throws Throwable {
		if (obj == null) {
			lua.pushNil();
		} else if (obj instanceof JavaFunction) {
			lua.pushJavaFunction((JavaFunction) obj);
		} else if (obj instanceof LuaObject) {
			lua.rawGetI(Lua.LUA_REGISTRYINDEX, ((LuaObject) obj).ref);
		} else {
			lua.pushJavaObject(obj);
		}
	}

	public String toString() {
		return String.valueOf(ref);
	}
	
}
