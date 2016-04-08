package m.lua;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import android.util.Log;

import com.mob.tools.utils.ReflectHelper;

public class MLua {
	private static final String TAG = "mLua";
	private LuaWrapper lua;
	
	public MLua() {
		lua = new LuaWrapper();
		registerFunctions();
	}
	
	public void setBasedir(final String basedir) {
		lua.setSourceLoader(new SourceLoader() {
			public byte[] loadFile(String path) throws Throwable {
				File file = new File(basedir, path);
				if (!file.exists() && !path.endsWith(".lua")) {
					file = new File(basedir, path + ".lua");
				}
				
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];
					int len = fis.read(buf);
					while (len > 0) {
						baos.write(buf, 0, len);
						len = fis.read(buf);
					}
					baos.close();
					fis.close();
					return baos.toByteArray();
				}
				return null;
			}
		});
	}
	
	public void start(String launcher) throws Throwable {
		lua.start(launcher);
	}
	
	public void stop() {
		lua.stop();
	}
	
	private void registerFunctions() {
		addPrint();
		addToChar();
		addToInt();
		addToFloat();
		
		addImportClass();
		addNewInstance();
		addInvokeStaticMethod();
		addInvokeInstanceMethod();
		addGetStaticField();
		addSetStaticField();
		addGetInstanceField();
		addSetInstanceField();
		addGetClass();
		addCreateProxy();
	}
	
	public void pushGlobal(String name, Object value) {
		if (value instanceof JavaFunction) {
			lua.addJavaFunction(name, (JavaFunction) value);
		} else {
			lua.addJavaObject(name, value);
		}
	}
	
	// ================================================
	
	private void addPrint() {
		lua.addJavaFunction("print", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				StringBuffer sb = new StringBuffer();
				if (args == null) {
					sb.append("null");
				} else {
					for (Object arg : args) {
						if (sb.length() > 0) {
							sb.append('\t');
						}
						sb.append(arg);
					}
				}
				Log.d(TAG, sb.toString());
				return null;
			}
		});
	}
	
	private void addToChar() {
		lua.addJavaFunction("toChar", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Double d = (Double) args[0];
				return (char) d.shortValue();
			}
		});
	}
	
	private void addToInt() {
		lua.addJavaFunction("toInt", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Double d = (Double) args[0];
				double dValue = d.doubleValue();
				if (dValue > Integer.MAX_VALUE || dValue < Integer.MIN_VALUE) {
					return d.longValue();
				} else if (dValue > Short.MAX_VALUE || dValue < Short.MIN_VALUE) {
					return d.intValue();
				} else if (dValue > Byte.MAX_VALUE || dValue < Byte.MIN_VALUE) {
					return d.shortValue();
				} else {
					return d.byteValue();
				}
			}
		});
	}
	
	private void addToFloat() {
		lua.addJavaFunction("toFloat", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Double d = (Double) args[0];
				return d.floatValue();
			}
		});
	}
	
	// ================================================
	
	private void addImportClass() {
		lua.addJavaFunction("import", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String ret = null;
				if (args.length == 1) {
					String className = (String) args[0];
					ret = ReflectHelper.importClass(className);
				} else {
					String name = (String) args[0];
					String className = (String) args[1];
					ret = ReflectHelper.importClass(name, className);
				}
				return ret;
			}
		});
	}
	
	private void addNewInstance() {
		lua.addJavaFunction("new", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String className = (String) args[0];
				Object ret;
				if (args.length > 1) {
					Object[] params = new Object[args.length - 1];
					System.arraycopy(args, 1, params, 0, params.length);
					ret = ReflectHelper.newInstance(className, params);
				} else {
					ret = ReflectHelper.newInstance(className);
				}
				return ret;
			}
		});
	}
	
	private void addInvokeStaticMethod() {
		lua.addJavaFunction("invokeStatic", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String className = (String) args[0];
				String methodName = (String) args[1];
				Object ret;
				if (args.length > 2) {
					Object[] params = new Object[args.length - 2];
					System.arraycopy(args, 2, params, 0, params.length);
					ret = ReflectHelper.invokeStaticMethod(className, methodName, params);
				} else {
					ret = ReflectHelper.invokeStaticMethod(className, methodName);
				}
				return ret;
			}
		});
	}

	private void addInvokeInstanceMethod() {
		lua.addJavaFunction("invoke", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Object receiver = args[0];
				String methodName = (String) args[1];
				Object ret;
				if (args.length > 2) {
					Object[] params = new Object[args.length - 2];
					System.arraycopy(args, 2, params, 0, params.length);
					ret = ReflectHelper.invokeInstanceMethod(receiver, methodName, params);
				} else {
					ret = ReflectHelper.invokeInstanceMethod(receiver, methodName);
				}
				return ret;
			}
		});
	}
	
	private void addGetStaticField() {
		lua.addJavaFunction("getStatic", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String className = (String) args[0];
				String fieldName = (String) args[1];
				return ReflectHelper.getStaticField(className, fieldName);
			}
		});
	}
	
	private void addSetStaticField() {
		lua.addJavaFunction("setStatic", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String className = (String) args[0];
				String fieldName = (String) args[1];
				Object value = args[2];
				ReflectHelper.setStaticField(className, fieldName, value);
				return null;
			}
		});
	}
	
	private void addGetInstanceField() {
		lua.addJavaFunction("get", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Object receiver = args[0];
				String fieldName = (String) args[1];
				return ReflectHelper.getInstanceField(receiver, fieldName);
			}
		});
	}
	
	private void addSetInstanceField() {
		lua.addJavaFunction("set", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				Object receiver = args[0];
				String fieldName = (String) args[1];
				Object value = args[2];
				ReflectHelper.setInstanceField(receiver, fieldName, value);
				return null;
			}
		});
	}
	
	private void addGetClass() {
		lua.addJavaFunction("getClass", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				String name = (String) args[0];
				return ReflectHelper.getClass(name);
			}
		});
	}
	
	private void addCreateProxy() {
		lua.addJavaFunction("createProxy", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				final LuaObject table = (LuaObject) args[0];
				Class<?>[] proxyIntefaces = new Class<?>[args.length - 1];
				for (int i = 0; i < proxyIntefaces.length; i++) {
					String name = (String) args[i + 1];
					proxyIntefaces[i] = ReflectHelper.getClass(name);
				}
				
				ClassLoader loader = getClass().getClassLoader();
				InvocationHandler handler = new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						LuaObject function = table.getField(method.getName());
						Class<?> retType = method.getReturnType();
						if (retType.equals(Void.class) || retType.equals(void.class)) {
							return function.call(args, 0);
						} else {
							return function.call(args, 1);
						}
					}
				};
				
				return Proxy.newProxyInstance(loader, proxyIntefaces, handler);
			}
		});
	}
	
}
