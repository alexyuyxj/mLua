#include "m_lua_Lua.h"

void pushJNIEnv(JNIEnv* env, lua_State* L){
	lua_pushstring(L, "__JNIEnv");
	lua_rawget(L, LUA_REGISTRYINDEX);
	if (!lua_isnil(L, -1)) {
		JNIEnv** udEnv = (JNIEnv**) lua_touserdata(L, -1);
		*udEnv = env;
		lua_pop(L, 1);
	} else {
		lua_pop(L, 1);
		JNIEnv** udEnv = (JNIEnv**) lua_newuserdata(L, sizeof(JNIEnv*));
		*udEnv = env;
		lua_pushstring(L, "__JNIEnv");
		lua_insert(L, -2);
		lua_rawset(L, LUA_REGISTRYINDEX);
	}
}

JNIEnv* getJNIEnv(lua_State* L) {
	lua_pushstring(L, "__JNIEnv");
	lua_rawget(L, LUA_REGISTRYINDEX);

	if (!lua_isuserdata(L, -1)) {
		lua_pop(L, 1);
		return 0;
	}

	JNIEnv** udEnv = (JNIEnv**) lua_touserdata(L, -1);
	lua_pop(L, 1);
	return *udEnv;
}

JNIEXPORT jlong JNICALL Java_m_lua_Lua_nativeOpen
		(JNIEnv* env, jobject thiz) {
	lua_State* nativeObj = lua_open();
	pushJNIEnv(env, nativeObj);
	return (jlong) nativeObj;
}

JNIEXPORT void JNICALL Java_m_lua_Lua_close
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_close((lua_State*) nativeObj);
}

JNIEXPORT jlong JNICALL Java_m_lua_Lua_newthread
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
   return (jlong) lua_newthread((lua_State*) nativeObj);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_getTop
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	return (jint) lua_gettop((lua_State*) nativeObj);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_setTop
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_settop((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushValue
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_pushvalue((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_remove
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_remove((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_insert
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_insert((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_replace
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_replace((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_checkStack
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint sz) {
	return (jint) lua_checkstack((lua_State*) nativeObj, (int) sz);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_nativeXmove
		(JNIEnv* env, jobject thiz, jlong nativeFrom, jlong nativeTo, jint n) {
	lua_xmove((lua_State*) nativeFrom, (lua_State*) nativeTo, (int) n);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isNumber
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isnumber((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isstring((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isCFunction
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_iscfunction((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isUserdata
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isuserdata((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_type
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_type((lua_State*) nativeObj, (int) idx);
}

JNIEXPORT jstring JNICALL Java_m_lua_Lua_typeName
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint tp) {
	const char * name = lua_typename((lua_State*) nativeObj, (int) tp);
	return (*env)->NewStringUTF(env, name);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_equal
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx1, jint idx2) {
	return (jint) lua_equal((lua_State*) nativeObj, idx1, idx2);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_rawequal
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx1, jint idx2) {
	return (jint) lua_rawequal((lua_State*) nativeObj, idx1, idx2);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_lessthan
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx1, jint idx2) {
	return (jint) lua_lessthan((lua_State*) nativeObj, idx1, idx2);
}

JNIEXPORT jdouble JNICALL Java_m_lua_Lua_toNumber
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jdouble) lua_tonumber((lua_State*) nativeObj, idx);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_toInteger
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_tointeger((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_toBoolean
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_toboolean((lua_State*) nativeObj, idx);
}

JNIEXPORT jstring JNICALL Java_m_lua_Lua_toString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	jsize len = (jsize) lua_strlen((lua_State*) nativeObj, idx);
	if (len <= 0) {
		return (*env)->NewByteArray(env, 0);
	}

	const char* str = lua_tostring((lua_State*) nativeObj, idx);
	if (str == NULL) {
		return NULL;
	}

	return (*env)->NewStringUTF(env, str);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_objlen
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_objlen((lua_State*) nativeObj, idx);
}

JNIEXPORT jlong JNICALL Java_m_lua_Lua_toThread
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jlong) lua_tothread((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushNil
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushnil((lua_State*) nativeObj);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushNumber
		(JNIEnv* env, jobject thiz, jlong nativeObj, jdouble number) {
	lua_pushnumber((lua_State*) nativeObj, (lua_Number) number);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushInteger
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint integer) {
	lua_pushinteger((lua_State*) nativeObj, (lua_Integer) integer);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushString__JLjava_lang_String_2
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring str) {
	const char* uniStr = (*env)->GetStringUTFChars(env, str, JNI_FALSE);
	lua_pushstring((lua_State*) nativeObj, uniStr);
	(*env)->ReleaseStringUTFChars(env, str, uniStr);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushBoolean
		(JNIEnv* env, jobject thiz, jlong nativeObj, jboolean pbool) {
	lua_pushboolean((lua_State*) nativeObj, (pbool == JNI_TRUE ? 1 : 0));
}

JNIEXPORT void JNICALL Java_m_lua_Lua_getTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_gettable((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_getField
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jstring k) {
	const char* uniStr = (*env)->GetStringUTFChars(env, k, JNI_FALSE);
	lua_getfield((lua_State*) nativeObj, idx, uniStr);
	(*env)->ReleaseStringUTFChars(env, k, uniStr);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_rawGet
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_rawget((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_rawGetI
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jint n) {
	lua_rawgeti((lua_State*) nativeObj, idx, n);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_createTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jint nrec) {
	lua_createtable((lua_State*) nativeObj, idx, nrec);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_getMetaTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_getmetatable((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_getFEnv
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_getfenv((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_setTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_settable((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_setField
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jstring k) {
	const char* uniStr = (*env)->GetStringUTFChars(env, k, JNI_FALSE);
	lua_setfield((lua_State*) nativeObj, idx, uniStr);
	(*env)->ReleaseStringUTFChars(env, k, uniStr);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_rawSet
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	lua_rawset((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_rawSetI
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jint n) {
	lua_rawseti((lua_State*) nativeObj, idx, n);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_setMetaTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_setmetatable((lua_State*) nativeObj, idx);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_setFEnv
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_setfenv((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_call
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArgs, jint nResults) {
	lua_call((lua_State*) nativeObj, nArgs, nResults);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_pcall
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArgs, jint results, jint errFunc) {
	return (jint) lua_pcall((lua_State*) nativeObj, nArgs, results, errFunc);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_yield
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nResults) {
	return (jint) lua_yield((lua_State*) nativeObj, nResults);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_resume
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArgs) {
	return (jint) lua_resume((lua_State*) nativeObj, nArgs);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_status
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	return (jint) lua_status((lua_State*) nativeObj);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_gc
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint what, jint data) {
	return (jint) lua_gc((lua_State*) nativeObj, what, data);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_error
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	return (jint) lua_error((lua_State*) nativeObj);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_next
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_next((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_concat
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint n) {
	lua_concat((lua_State*) nativeObj, n);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pop
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint n) {
	lua_pop((lua_State*) nativeObj, n);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_newTable
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_newtable((lua_State*) nativeObj);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_strlen
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jint) lua_strlen((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isFunction
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isfunction((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_istable((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isNil
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isnil((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isBoolean
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isboolean((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isThread
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isthread((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isNone
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isnone((lua_State*) nativeObj, idx);
}

JNIEXPORT jboolean JNICALL Java_m_lua_Lua_isNoneOrNil
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	return (jboolean) lua_isnoneornil((lua_State*) nativeObj, idx);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_setGlobal
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring name) {
	const char* str = (*env)->GetStringUTFChars(env, name, JNI_FALSE);
	lua_setglobal((lua_State*) nativeObj, str);
	(*env)->ReleaseStringUTFChars(env, name, str);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_getGlobal
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring name) {
	const char* str = (*env)->GetStringUTFChars(env, name, JNI_FALSE);
	lua_getglobal((lua_State*) nativeObj, str);
	(*env)->ReleaseStringUTFChars(env, name, str);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_getGcCount
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	return (jint) lua_getgccount((lua_State*) nativeObj);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LdoFile
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring fileName) {
	const char* file = (*env)->GetStringUTFChars(env, fileName, JNI_FALSE);
	int ret = luaL_dofile((lua_State*) nativeObj, file);
	(*env)->ReleaseStringUTFChars(env, fileName, file);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LdoString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring string) {
	const char* str = (*env)->GetStringUTFChars(env, string, JNI_FALSE);
	int ret = luaL_dostring((lua_State*) nativeObj, str);
	(*env)->ReleaseStringUTFChars(env, string, str);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LgetMetaField
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint obj, jstring e) {
	const char* str = (*env)->GetStringUTFChars(env, e, JNI_FALSE);
	int ret = luaL_getmetafield((lua_State*) nativeObj, obj, str);
	(*env)->ReleaseStringUTFChars(env, e, str);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LcallMeta
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint obj, jstring e) {
	const char* event = (*env)->GetStringUTFChars(env, e, JNI_FALSE);
	int ret = luaL_callmeta((lua_State*) nativeObj, obj, event);
	(*env)->ReleaseStringUTFChars(env, e, event);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_Ltyperror
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArg, jstring tName) {
	const char* name = (*env)->GetStringUTFChars(env, tName, JNI_FALSE);
	int ret = luaL_typerror((lua_State*) nativeObj, nArg, name);
	(*env)->ReleaseStringUTFChars(env, tName, name);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LargError
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg, jstring extraMsg) {
	const char* msg = (*env)->GetStringUTFChars(env, extraMsg, JNI_FALSE);
	int ret = luaL_argerror((lua_State*) nativeObj, numArg, msg);
	(*env)->ReleaseStringUTFChars(env, extraMsg, msg);
	return (jint) ret;
}

JNIEXPORT jstring JNICALL Java_m_lua_Lua_LcheckString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg) {
	const char* str = luaL_checkstring((lua_State*) nativeObj, numArg);
	if (str == NULL) {
		return NULL;
	}
	
	return (*env)->NewStringUTF(env, str);
}

JNIEXPORT jstring JNICALL Java_m_lua_Lua_LoptString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg) {
	if (lua_isnoneornil((lua_State*) nativeObj, numArg)) {
		return 0;
	}

	const char* str = luaL_checklstring((lua_State*) nativeObj, numArg, 0);
	if (str == NULL) {
		return NULL;
	}

	return (*env)->NewStringUTF(env, str);
}

JNIEXPORT jdouble JNICALL Java_m_lua_Lua_LcheckNumber
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg) {
	return (jdouble) luaL_checknumber((lua_State*) nativeObj, numArg);
}

JNIEXPORT jdouble JNICALL Java_m_lua_Lua_LoptNumber
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg, jdouble def) {
	return (jdouble) luaL_optnumber((lua_State*) nativeObj, numArg, (lua_Number) def);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LcheckInteger
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg) {
	return (jint) luaL_checkinteger((lua_State*) nativeObj, numArg);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LoptInteger
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint numArg, jint def) {
	return (jint) luaL_optinteger((lua_State*) nativeObj, numArg, (lua_Integer) def);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LcheckStack
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint sz, jstring msg) {
	const char* cmsg = (*env)->GetStringUTFChars(env, msg, JNI_FALSE);
	luaL_checkstack((lua_State*) nativeObj, sz, cmsg);
	(*env)->ReleaseStringUTFChars(env, msg, cmsg);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LcheckType
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArg, jint t) {
	luaL_checktype((lua_State*) nativeObj, nArg, t);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LcheckAny
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint nArg) {
	luaL_checkany((lua_State*) nativeObj, nArg);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LnewMetatable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring tName) {
	const char* name = (*env)->GetStringUTFChars(env, tName, JNI_FALSE);
	int ret = luaL_newmetatable((lua_State*) nativeObj, name);
	(*env)->ReleaseStringUTFChars(env, tName, name);
	return (jint) ret;
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LgetMetatable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring tName) {
	const char* name = (*env)->GetStringUTFChars(env, tName, JNI_FALSE);
	luaL_getmetatable((lua_State*) nativeObj, name);
	(*env)->ReleaseStringUTFChars(env, tName, name);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_Lwhere
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint lvl) {
	luaL_where((lua_State*) nativeObj, lvl);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_Lref
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint t) {
	return (jint) luaL_ref((lua_State*) nativeObj, t);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LunRef
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint t, jint ref) {
	luaL_unref((lua_State*) nativeObj, t, ref);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LgetN
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint t) {
	return (jint) luaL_getn((lua_State*) nativeObj, t);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_LsetN
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint t, jint n) {
	luaL_setn((lua_State*) nativeObj, t, n);
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LloadFile
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring fileName) {
	const char* file = (*env)->GetStringUTFChars(env, fileName, JNI_FALSE);
	int ret = luaL_loadfile((lua_State*) nativeObj, file);
	(*env)->ReleaseStringUTFChars(env, fileName, file);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LloadBuffer
		(JNIEnv* env, jobject thiz, jlong nativeObj, jbyteArray buff, jlong sz, jstring name) {
	jbyte* cBuff = (*env)->GetByteArrayElements(env, buff, JNI_FALSE);
	const char* cname = (*env)->GetStringUTFChars(env, name, JNI_FALSE);
	int ret = luaL_loadbuffer((lua_State*) nativeObj, (const char*) cBuff, sz, name);
	(*env)->ReleaseStringUTFChars(env, name, cname);
	(*env)->ReleaseByteArrayElements(env, buff, cBuff, 0);
	return (jint) ret;
}

JNIEXPORT jint JNICALL Java_m_lua_Lua_LloadString
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring s) {
	const char* str = (*env)->GetStringUTFChars(env, s, JNI_FALSE);
	int ret = luaL_loadstring((lua_State*) nativeObj, str);
	(*env)->ReleaseStringUTFChars(env, s, str);
	return (jint) ret;
}

JNIEXPORT jbyteArray JNICALL Java_m_lua_Lua_Lgsub
		(JNIEnv* env, jobject thiz, jlong nativeObj, jstring s, jstring p, jstring r) {
	const char* cs = (*env)->GetStringUTFChars(env, s, JNI_FALSE);
	const char* cp = (*env)->GetStringUTFChars(env, p, JNI_FALSE);
	const char* cr = (*env)->GetStringUTFChars(env, r, JNI_FALSE);
	const char* ret = luaL_gsub((lua_State*) nativeObj, cs, cp, cr);
	jsize len = (jsize) lua_strlen((lua_State*) nativeObj, -1);
	jbyteArray arr = (*env)->NewByteArray(env, len);
	(*env)->SetByteArrayRegion(env, arr, 0, len, ret);
	(*env)->ReleaseStringUTFChars(env, r, cr);
	(*env)->ReleaseStringUTFChars(env, p, cp);
	(*env)->ReleaseStringUTFChars(env, s, cs);
	return arr;
}

JNIEXPORT jstring JNICALL Java_m_lua_Lua_LfindTable
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx, jstring fname, jint szhint) {
	const char* name = (*env)->GetStringUTFChars(env, fname, JNI_FALSE);
	const char* ret = luaL_findtable((lua_State*) nativeObj, idx, name, szhint);
	(*env)->ReleaseStringUTFChars(env, fname, name);
	if (ret == 0) {
		return 0;
	}
	return (*env)->NewStringUTF(env, ret);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openBase
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_base);
	lua_pushstring((lua_State*) nativeObj, "");
	lua_call((lua_State*) nativeObj, 1, 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openTable
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_table);
	lua_pushstring((lua_State*) nativeObj, LUA_TABLIBNAME);
	lua_call((lua_State*) nativeObj, 1, 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openIo
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_io);
	lua_pushstring((lua_State*) nativeObj, LUA_IOLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openOs
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_os);
	lua_pushstring((lua_State*) nativeObj, LUA_OSLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openString
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_string);
	lua_pushstring((lua_State*) nativeObj, LUA_STRLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openMath
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_math);
	lua_pushstring((lua_State*) nativeObj, LUA_MATHLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openDebug
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_debug);
	lua_pushstring((lua_State*) nativeObj, LUA_DBLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openPackage
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	lua_pushcfunction((lua_State*) nativeObj, luaopen_package);
	lua_pushstring((lua_State*) nativeObj, LUA_LOADLIBNAME);
	lua_call((lua_State*) nativeObj, 1 , 0);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_openLibs
		(JNIEnv* env, jobject thiz, jlong nativeObj) {
	luaL_openlibs((lua_State*) nativeObj);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_newUserData
		(JNIEnv* env, jobject thiz, jlong nativeObj, jobject obj) {
	jobject* globalRef = (*env)->NewGlobalRef(env, obj);
	jobject* userData = (jobject*) lua_newuserdata((lua_State*) nativeObj, sizeof(jobject));
	*userData = globalRef;
}

int functionWrapper(lua_State* L) {
	jobject* obj = lua_touserdata(L, 1);
	JNIEnv* javaEnv = getJNIEnv(L);
	if (javaEnv == NULL) {
		lua_pushstring(L, "Invalid JNI Environment.");
		lua_error(L);
	}

	jclass clz = (*javaEnv)->GetObjectClass(javaEnv, *obj);
	jmethodID mth = (*javaEnv)->GetMethodID(javaEnv, clz, "execute", "()I");
	int ret = (*javaEnv)->CallIntMethod(javaEnv, *obj, mth);
	jthrowable exp = (*javaEnv)->ExceptionOccurred(javaEnv);
	if (exp != NULL) {
		(*javaEnv)->ExceptionClear(javaEnv);
		clz = (*javaEnv)->FindClass(javaEnv, "java/lang/Throwable");
		mth = (*javaEnv)->GetMethodID(javaEnv, clz, "getMessage", "()Ljava/lang/String;");
		jstring jstr = (jstring) (*javaEnv)->CallObjectMethod(javaEnv, exp, mth);
		const char* str = (*javaEnv)->GetStringUTFChars(javaEnv, jstr, JNI_FALSE);
		lua_pushstring(L, str);
		(*javaEnv)->ReleaseStringUTFChars(javaEnv, jstr, str);
		lua_error( L );
	}

	return ret;
}

JNIEXPORT jlong JNICALL Java_m_lua_Lua_getFunctionWrapper
		(JNIEnv* env, jobject thiz) {
	return (jlong) (functionWrapper);
}

int gcWrapper(lua_State* L) {
	jobject* pObj = (jobject*) lua_touserdata(L, 1);
	JNIEnv* javaEnv = getJNIEnv(L);
	if (javaEnv == NULL) {
		lua_pushstring(L, "Invalid JNI Environment.");
		lua_error(L);
	}
	(*javaEnv)->DeleteGlobalRef(javaEnv, *pObj);
	return 0;
}

JNIEXPORT jlong JNICALL Java_m_lua_Lua_getGCWrapper
		(JNIEnv* env, jobject thiz) {
	return (jlong) (gcWrapper);
}

JNIEXPORT void JNICALL Java_m_lua_Lua_pushCFunction
		(JNIEnv* env, jobject thiz, jlong nativeObj, jlong function) {
	lua_pushcfunction((lua_State*) nativeObj, (lua_CFunction) function);
}

JNIEXPORT jobject JNICALL Java_m_lua_Lua_toUserData
		(JNIEnv* env, jobject thiz, jlong nativeObj, jint idx) {
	jobject* obj = (jobject*) lua_touserdata((lua_State*) nativeObj, idx);
	return *obj;
}

