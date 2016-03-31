package m.lua;

import java.util.ArrayList;

public class Lua {
	public static final int LUA_GLOBALSINDEX  = -10002;
	public static final int LUA_REGISTRYINDEX = -10000;
	
	public static final int LUA_TNONE     = -1;
	public static final int LUA_TNIL      = 0;
	public static final int LUA_TBOOLEAN  = 1;
	public static final int LUA_TLIGHTUSERDATA = 2;
	public static final int LUA_TNUMBER   = 3;
	public static final int LUA_TSTRING   = 4;
	public static final int LUA_TTABLE    = 5;
	public static final int LUA_TFUNCTION = 6;
	public static final int LUA_TUSERDATA = 7;
	public static final int LUA_TTHREAD   = 8;
	
	public static final int LUA_MULTRET   = -1;
	public static final int LUA_ERRRUN    = 1;
	public static final int LUA_ERRMEM    = 4;
	public static final int LUA_ERRERR    = 5;
	
	private long nativeObj;
	
	static {
		System.loadLibrary("mlua");
	}
	
	public void open() {
		nativeObj = nativeOpen();
	}

	public void close() {
		close(nativeObj);
	}
	
	public Lua newthread() {
		Lua lua = new Lua();
		lua.nativeObj = newthread(nativeObj);
		return lua;
	}
	
	public int getTop() {
		return getTop(nativeObj);
	}

	public void setTop(int idx) {
		setTable(nativeObj, idx);
	}

	public void pushValue(int idx) {
		pushValue(nativeObj, idx);
	}

	public void remove(int idx) {
		remove(nativeObj, idx);
	}

	public void insert(int idx) {
		insert(nativeObj, idx);
	}

	public void replace(int idx) {
		replace(nativeObj, idx);
	}

	public int checkStack(int sz) {
		return checkStack(nativeObj, sz);
	}

	public void xmove(Lua from, Lua to, int n) {
		nativeXmove(from.nativeObj, to.nativeObj, n);
	}

	public boolean isNumber(int idx) {
		return isNumber(nativeObj, idx);
	}

	public boolean isString(int idx) {
		return isString(nativeObj, idx);
	}

	public boolean isCFunction(int idx) {
		return isCFunction(nativeObj, idx);
	}

	public boolean isUserdata(int idx) {
		return isUserdata(nativeObj, idx);
	}

	public int type(int idx) {
		return type(nativeObj, idx);
	}

	public String typeName(int tp) {
		return typeName(nativeObj, tp);
	}

	public int equal(int idx1, int idx2) {
		return equal(nativeObj, idx1, idx2);
	}

	public int rawequal(int idx1, int idx2) {
		return rawequal(nativeObj, idx1, idx2);
	}

	public int lessthan(int idx1, int idx2) {
		return lessthan(nativeObj, idx1, idx2);
	}

	public double toNumber(int idx) {
		return toNumber(nativeObj, idx);
	}

	public int toInteger(int idx) {
		return toInteger(nativeObj, idx);
	}

	public boolean toBoolean(int idx) {
		return toBoolean(nativeObj, idx);
	}

	public String toString(int idx) {
		return toString(nativeObj, idx);
	}

	public int objlen(int idx) {
		return objlen(nativeObj, idx);
	}

	public Lua toThread(int idx) {
		Lua lua = new Lua();
		lua.nativeObj = toThread(nativeObj, idx);
		return lua;
	}

	public void pushNil() {
		pushNil(nativeObj);
	}

	public void pushNumber(double number) {
		pushNumber(nativeObj, number);
	}

	public void pushInteger(int integer) {
		pushInteger(nativeObj, integer);
	}

	public void pushString(String str) {
		pushString(nativeObj, str);
	}

	public void pushBoolean(boolean bool) {
		pushBoolean(nativeObj, bool);
	}

	public void getTable(int idx) {
		getTable(nativeObj, idx);
	}

	public void getField(int idx, String k) {
		getField(nativeObj, idx, k);
	}

	public void rawGet(int idx) {
		rawGet(nativeObj, idx);
	}

	public void rawGetI(int idx, int n) {
		rawGetI(nativeObj, idx, n);
	}

	public void createTable(int narr, int nrec) {
		createTable(nativeObj, narr, nrec);
	}

	public int getMetaTable(int idx) {
		return getMetaTable(nativeObj, idx);
	}

	public void getFEnv(int idx) {
		getFEnv(nativeObj, idx);
	}

	public void setTable(int idx) {
		setTable(nativeObj, idx);
	}

	public void setField(int idx, String k) {
		setField(nativeObj, idx, k);
	}

	public void rawSet(int idx) {
		rawSet(nativeObj, idx);
	}

	public void rawSetI(int idx, int n) {
		rawSetI(nativeObj, idx, n);
	}

	public int setMetaTable(int idx) {
		return setMetaTable(nativeObj, idx);
	}

	public int setFEnv(int idx) {
		return setFEnv(nativeObj, idx);
	}

	public void call(int nArgs, int nResults) {
		call(nativeObj, nArgs, nResults);
	}

	public int pcall(int nArgs, int results, int errFunc) {
		return pcall(nativeObj, nArgs, results, errFunc);
	}

	public int yield(int nResults) {
		return yield(nativeObj, nResults);
	}

	public int resume(int nArgs) {
		return resume(nativeObj, nArgs);
	}

	public int status() {
		return status(nativeObj);
	}

	public int gc(int what, int data) {
		return gc(nativeObj, what, data);
	}

	public int error() {
		return error(nativeObj);
	}

	public int next(int idx) {
		return next(nativeObj, idx);
	}

	public void concat(int n) {
		concat(nativeObj, n);
	}

	public void pop(int n) {
		pop(nativeObj, n);
	}

	public void newTable() {
		newTable(nativeObj);
	}

	public int strlen(int idx) {
		return strlen(nativeObj, idx);
	}

	public boolean isFunction(int idx) {
		return isFunction(nativeObj, idx);
	}

	public boolean isTable(int idx) {
		return isTable(nativeObj, idx);
	}

	public boolean isNil(int idx) {
		return isNil(nativeObj, idx);
	}

	public boolean isBoolean(int idx) {
		return isBoolean(nativeObj, idx);
	}

	public boolean isThread(int idx) {
		return isThread(nativeObj, idx);
	}

	public boolean isNone(int idx) {
		return isNone(nativeObj, idx);
	}

	public boolean isNoneOrNil(int idx) {
		return isNoneOrNil(nativeObj, idx);
	}

	public void setGlobal(String name) {
		setGlobal(nativeObj, name);
	}

	public void getGlobal(String name) {
		getGlobal(nativeObj, name);
	}

	public int getGcCount() {
		return getGcCount(nativeObj);
	}

	public int LdoFile(String fileName) {
		return LdoFile(nativeObj, fileName);
	}

	public int LdoString(String string) {
		return LdoString(nativeObj, string);
	}

	public int LgetMetaField(int obj, String e) {
		return LgetMetaField(nativeObj, obj, e);
	}

	public int LcallMeta(int obj, String e) {
		return LcallMeta(nativeObj, obj, e);
	}

	public int Ltyperror(int nArg, String tName) {
		return Ltyperror(nativeObj, nArg, tName);
	}

	public int LargError(int numArg, String extraMsg) {
		return LargError(nativeObj, numArg, extraMsg);
	}

	public String LcheckString(int numArg) {
		return LcheckString(nativeObj, numArg);
	}

	public String LoptString(int numArg) {
		return LoptString(nativeObj, numArg);
	}

	public double LcheckNumber(int numArg) {
		return LcheckNumber(nativeObj, numArg);
	}

	public double LoptNumber(int numArg, double def) {
		return LoptNumber(nativeObj, numArg, def);
	}

	public int LcheckInteger(int numArg) {
		return LcheckInteger(nativeObj, numArg);
	}

	public int LoptInteger(int numArg, int def) {
		return LoptInteger(nativeObj, numArg, def);
	}

	public void LcheckStack(int sz, String msg) {
		LcheckStack(nativeObj, sz, msg);
	}

	public void LcheckType(int nArg, int t) {
		LcheckType(nativeObj, nArg, t);
	}

	public void LcheckAny(int nArg) {
		LcheckAny(nativeObj, nArg);
	}

	public int LnewMetatable(String tName) {
		return LnewMetatable(nativeObj, tName);
	}

	public void LgetMetatable(String tName) {
		LgetMetatable(nativeObj, tName);
	}

	public void Lwhere(int lvl) {
		Lwhere(nativeObj, lvl);
	}

	public int Lref(int t) {
		return Lref(nativeObj, t);
	}

	public void LunRef(int t, int ref) {
		LunRef(nativeObj, t, ref);
	}

	public int LgetN(int t) {
		return LgetN(nativeObj, t);
	}

	public void LsetN(int t, int n) {
		LsetN(nativeObj, t, n);
	}

	public int LloadFile(String fileName) {
		return LloadFile(nativeObj, fileName);
	}

	public int LloadBuffer(byte[] buff, long sz, String name) {
		return LloadBuffer(nativeObj, buff, sz, name);
	}

	public int LloadString(String s) {
		return LloadString(nativeObj, s);
	}

	public byte[] Lgsub(String s, String p, String r) {
		return Lgsub(nativeObj, s, p, r);
	}

	public String LfindTable(int idx, String fname, int szhint) {
		return LfindTable(nativeObj, idx, fname, szhint);
	}

	public void openBase() {
		openBase(nativeObj);
	}

	public void openTable() {
		openTable(nativeObj);
	}

	public void openIo() {
		openIo(nativeObj);
	}

	public void openOs() {
		openOs(nativeObj);
	}

	public void openString() {
		openString(nativeObj);
	}

	public void openMath() {
		openMath(nativeObj);
	}

	public void openDebug() {
		openDebug(nativeObj);
	}

	public void openPackage() {
		openPackage(nativeObj);
	}

	public void openLibs() {
		openLibs(nativeObj);
	}
	
	public void newUserData(Object obj) {
		newUserData(nativeObj, obj);
	}
	
	public Object toUserData(int idx) {
		return toUserData(nativeObj, idx);
	}
	
	public boolean pushJavaObject(Object obj) {
		newUserData(obj);
		
		newTable();
		
		pushString("__gc");
		pushCFunction(nativeObj, getGCWrapper());
		rawSet(-3);
		
		pushString("__IsJavaObject");
		pushBoolean(true);
		rawSet(-3);

		return setMetaTable(-2) != 0;
	}
	
	public boolean pushJavaFunction(final JavaFunction function) {
		JavaFunctionWrapper wrapper = new JavaFunctionWrapper() {
			public int execute() throws Throwable {
				ArrayList<Object> args = new ArrayList<Object>();
				for (int i = 2; i <= getTop(); i++) {
					args.add(toJavaObject(i));
				}
				
				Object[] arr = new Object[args.size()];
				for (int i = 0; i < arr.length; i++) {
					arr[i] = args.get(i);
				}
				
				Object ret = function.execute(arr);
				if (ret != null) {
					pushJavaObject(ret);
					return 1;
				} else {
					return 0;
				}
			}
		};
		newUserData(wrapper);
		
		newTable();
		
		pushString("__call");
		pushCFunction(nativeObj, getFunctionWrapper());
		rawSet(-3);
		
		pushString("__gc");
		pushCFunction(nativeObj, getGCWrapper());
		rawSet(-3);
		
		pushString("__IsJavaObject");
		pushBoolean(true);
		rawSet(-3);
		
		return setMetaTable(-2) != 0;
	}
	
	public synchronized Object toJavaObject(int idx) throws Throwable {
		Object obj = null;
		if (isBoolean(idx)) {
			obj = toBoolean(idx);
		} else if (type(idx) == LUA_TSTRING) {
			obj = toString(idx);
		} else if (type(idx) == LUA_TNUMBER) {
			double dValue = toNumber(idx);
			if (dValue == 0) {
				obj = (byte) 0;
			} else if (dValue > Float.MAX_VALUE || dValue < Float.MIN_VALUE) {
				obj = dValue;
			} else {
				float fValue = (float) dValue;
				if (fValue != dValue) {
					obj = dValue;
				} else if (dValue > Long.MAX_VALUE || dValue < Long.MIN_VALUE) {
					obj = fValue;
				} else {
					if (dValue > Integer.MAX_VALUE || dValue < Integer.MIN_VALUE) {
						obj = (long) dValue;
					} else if (dValue > Short.MAX_VALUE || dValue < Short.MIN_VALUE) {
						obj = (int) dValue;
					} else if (dValue > Byte.MAX_VALUE || dValue < Byte.MIN_VALUE) {
						obj = (short) dValue;
					} else {
						obj = (byte) dValue;
					}
				}
			} 
		} else if (isFunction(idx) || isTable(idx)) {
			obj = toLuaObject(idx);
		} else if (isUserdata(idx)) {
			if (isJavaObject(idx)) {
				obj = toUserData(idx);
			} else {
				obj = toLuaObject(idx);
			}
		} else if (isNil(idx)) {
			obj = null;
		}

		return obj;
	}
	
	private LuaObject toLuaObject(int idx) {
		pushValue(idx);
		LuaObject obj = new LuaObject();
		obj.ref = Lref(LUA_REGISTRYINDEX);
		obj.lua = this;
		return obj;
	}
	
	private boolean isJavaObject(int idx) {
		boolean res = false;
		if (isUserdata(idx)) {
			if (getMetaTable(idx) != 0) {
				pushString("__IsJavaObject");
				rawGet(-2);
				res = !isNil(-1);
				pop(2);
			}
		}
		
		return res;
	}
	
	public String toString() {
		return String.valueOf(nativeObj);
	}

	private synchronized native long nativeOpen();
	private synchronized native void close(long nativeObj);
	private synchronized native long newthread(long nativeObj);
	private synchronized native int getTop(long nativeObj);
	private synchronized native void setTop(long nativeObj, int idx);
	private synchronized native void pushValue(long nativeObj, int idx);
	private synchronized native void remove(long nativeObj, int idx);
	private synchronized native void insert(long nativeObj, int idx);
	private synchronized native void replace(long nativeObj, int idx);
	private synchronized native int checkStack(long nativeObj, int sz);
	private synchronized native void nativeXmove(long nativeFrom, long nativeTo, int n);
	private synchronized native boolean isNumber(long nativeObj, int idx);
	private synchronized native boolean isString(long nativeObj, int idx);
	private synchronized native boolean isCFunction(long nativeObj, int idx);
	private synchronized native boolean isUserdata(long nativeObj, int idx);
	private synchronized native int type(long nativeObj, int idx);
	private synchronized native String typeName(long nativeObj, int tp);
	private synchronized native int equal(long nativeObj, int idx1, int idx2);
	private synchronized native int rawequal(long nativeObj, int idx1, int idx2);
	private synchronized native int lessthan(long nativeObj, int idx1, int idx2);
	private synchronized native double toNumber(long nativeObj, int idx);
	private synchronized native int toInteger(long nativeObj, int idx);
	private synchronized native boolean toBoolean(long nativeObj, int idx);
	private synchronized native String toString(long nativeObj, int idx);
	private synchronized native int objlen(long nativeObj, int idx);
	private synchronized native long toThread(long nativeObj, int idx);
	private synchronized native void pushNil(long nativeObj);
	private synchronized native void pushNumber(long nativeObj, double number);
	private synchronized native void pushInteger(long nativeObj, int integer);
	private synchronized native void pushString(long nativeObj, String str);
	private synchronized native void pushBoolean(long nativeObj, boolean bool);
	private synchronized native void getTable(long nativeObj, int idx);
	private synchronized native void getField(long nativeObj, int idx, String k);
	private synchronized native void rawGet(long nativeObj, int idx);
	private synchronized native void rawGetI(long nativeObj, int idx, int n);
	private synchronized native void createTable(long nativeObj, int narr, int nrec);
	private synchronized native int getMetaTable(long nativeObj, int idx);
	private synchronized native void getFEnv(long nativeObj, int idx);
	private synchronized native void setTable(long nativeObj, int idx);
	private synchronized native void setField(long nativeObj, int idx, String k);
	private synchronized native void rawSet(long nativeObj, int idx);
	private synchronized native void rawSetI(long nativeObj, int idx, int n);
	private synchronized native int setMetaTable(long nativeObj, int idx);
	private synchronized native int setFEnv(long nativeObj, int idx);
	private synchronized native void call(long nativeObj, int nArgs, int nResults);
	private synchronized native int pcall(long nativeObj, int nArgs, int results, int errFunc);
	private synchronized native int yield(long nativeObj, int nResults);
	private synchronized native int resume(long nativeObj, int nArgs);
	private synchronized native int status(long nativeObj);
	private synchronized native int gc(long nativeObj, int what, int data);
	private synchronized native int error(long nativeObj);
	private synchronized native int next(long nativeObj, int idx);
	private synchronized native void concat(long nativeObj, int n);
	private synchronized native void pop(long nativeObj, int n);
	private synchronized native void newTable(long nativeObj);
	private synchronized native int strlen(long nativeObj, int idx);
	private synchronized native boolean isFunction(long nativeObj, int idx);
	private synchronized native boolean isTable(long nativeObj, int idx);
	private synchronized native boolean isNil(long nativeObj, int idx);
	private synchronized native boolean isBoolean(long nativeObj, int idx);
	private synchronized native boolean isThread(long nativeObj, int idx);
	private synchronized native boolean isNone(long nativeObj, int idx);
	private synchronized native boolean isNoneOrNil(long nativeObj, int idx);
	private synchronized native void setGlobal(long nativeObj, String name);
	private synchronized native void getGlobal(long nativeObj, String name);
	private synchronized native int getGcCount(long nativeObj);
	private synchronized native int LdoFile(long nativeObj, String fileName);
	private synchronized native int LdoString(long nativeObj, String string);
	private synchronized native int LgetMetaField(long nativeObj, int obj, String e);
	private synchronized native int LcallMeta(long nativeObj, int obj, String e);
	private synchronized native int Ltyperror(long nativeObj, int nArg, String tName);
	private synchronized native int LargError(long nativeObj, int numArg, String extraMsg);
	private synchronized native String LcheckString(long nativeObj, int numArg);
	private synchronized native String LoptString(long nativeObj, int numArg);
	private synchronized native double LcheckNumber(long nativeObj, int numArg);
	private synchronized native double LoptNumber(long nativeObj, int numArg, double def);
	private synchronized native int LcheckInteger(long nativeObj, int numArg);
	private synchronized native int LoptInteger(long nativeObj, int numArg, int def);
	private synchronized native void LcheckStack(long nativeObj, int sz, String msg);
	private synchronized native void LcheckType(long nativeObj, int nArg, int t);
	private synchronized native void LcheckAny(long nativeObj, int nArg);
	private synchronized native int LnewMetatable(long nativeObj, String tName);
	private synchronized native void LgetMetatable(long nativeObj, String tName);
	private synchronized native void Lwhere(long nativeObj, int lvl);
	private synchronized native int Lref(long nativeObj, int t);
	private synchronized native void LunRef(long nativeObj, int t, int ref);
	private synchronized native int LgetN(long nativeObj, int t);
	private synchronized native void LsetN(long nativeObj, int t, int n);
	private synchronized native int LloadFile(long nativeObj, String fileName);
	private synchronized native int LloadBuffer(long nativeObj, byte[] buff, long sz, String name);
	private synchronized native int LloadString(long nativeObj, String s);
	private synchronized native byte[] Lgsub(long nativeObj, String s, String p, String r);
	private synchronized native String LfindTable(long nativeObj, int idx, String fname, int szhint);
	private synchronized native void openBase(long nativeObj);
	private synchronized native void openTable(long nativeObj);
	private synchronized native void openIo(long nativeObj);
	private synchronized native void openOs(long nativeObj);
	private synchronized native void openString(long nativeObj);
	private synchronized native void openMath(long nativeObj);
	private synchronized native void openDebug(long nativeObj);
	private synchronized native void openPackage(long nativeObj);
	private synchronized native void openLibs(long nativeObj);
	private synchronized native void newUserData(long nativeObj, Object obj);
	private synchronized native long getFunctionWrapper();
	private synchronized native long getGCWrapper();
	private synchronized native void pushCFunction(long nativeObj, long func);
	private synchronized native Object toUserData(long nativeObj, int idx);
	
	private static interface JavaFunctionWrapper {
		public int execute() throws Throwable;
	}
	
}
