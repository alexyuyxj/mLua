import("ReflectRunnable", "com.mob.tools.utils.ReflectHelper$ReflectRunnable")

local function main()
  print("hello world from mLua")
  local context = getContext()
  print("current context: ", context)
  local packageName = invoke(context, "getPackageName")
  print("packageName: ", packageName)
  
  local luaCode = {
    run = function(arg)
      print("luaCode.run(), input: ", arg)
      return "yoyoyo"
    end
  }
  local proxy = createProxy(luaCode, "ReflectRunnable")
  local res = invoke(proxy, "run", packageName)
  print("luaCode.run(), output: ", res)
  
  local bArray = new("[B", 16)
  for i = 0, 15 do
    set(bArray, "[" .. i .. "]", i + 1)
  end
  
  local bArray2 = new("[B", get(bArray, "length"))
  invokeStatic("System", "arraycopy", bArray, 0, bArray2, 0, 16)
  
  for i = 0, 15 do
    print("bArray2[" .. i .. "]: ", get(bArray2, "[" .. i .. "]"))
  end
end

main()
