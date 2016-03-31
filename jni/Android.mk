LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := mlua
LOCAL_SRC_FILES := lua/lapi.c \
                   lua/lauxlib.c \
                   lua/lbaselib.c \
                   lua/lcode.c \
                   lua/ldblib.c \
                   lua/ldebug.c \
                   lua/ldo.c \
                   lua/ldump.c \
                   lua/lfunc.c \
                   lua/lgc.c \
                   lua/linit.c \
                   lua/liolib.c \
                   lua/llex.c \
                   lua/lmathlib.c \
                   lua/lmem.c \
                   lua/loadlib.c \
                   lua/lobject.c \
                   lua/lopcodes.c \
                   lua/loslib.c \
                   lua/lparser.c \
                   lua/lstate.c \
                   lua/lstring.c \
                   lua/lstrlib.c \
                   lua/ltable.c \
                   lua/ltablib.c \
                   lua/ltm.c \
                   lua/lundump.c \
                   lua/lvm.c \
                   lua/lzio.c \
                   m_lua_Lua.c
LOCAL_LDLIBS    := -lm

include $(BUILD_SHARED_LIBRARY)
