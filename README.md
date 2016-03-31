# 介绍

当下多数在java下执行lua脚本的程序都是用了**luajava**。然而luajava存在一些严重的问题，它会将byte数组和string等同对待，而且它的反射执行效率比较低。为了弥补这些问题，我参考luajava，重写了它的java和jni代码，并以mLua为名重新发布。

# 特点描述

和luajava类似的，mLua也有内置的全局lua函数；java对象和lua对象可以通过jni层代码进行交换。但是mLua禁止lua直接操作java对象，如果想在lua中使用java对象，必须使用内置的全局函数实现。

mLua区分byte数组和string。在mLua中，java的byte数组对lua端而言，只是一个普通的userdata。

在跨语言数据交换的时候，string是被复制的，因此当一个string从lua传递到java后再于lua中修改它，它在java端的对应版本并不会随着改变。

将lua端的number传递给java后，会被优先解释为byte类型，否则将依照byte - short - int - long - float - double链条来尝试解释。

mLua不对外暴露lua解析器实例，所有的操作都基于MLua实例完成。

# java端方法描述

mLua的java端方法集中在MLua中：

方法名称                     | 方法解释
--------------------------- | -------------
setBasedir(String)          | 设置lua代码的最外层目录，所有lua代码都应该存放在这个目录或其子目录下
pushGlobal(String, Object)  | 设置全局lua的全局变量或函数，可以push普通的Object，或者JavaFunction。<br/>后者表示一个lua函数的java实现。只有在start方法执行前，设置的数据才会生<br/>效
start(String)               | 启动lua解析器，传递的参数表示lua代码的入口文件
stop()                      | 停止lua解析器并释放资源

除此之外，JavaFunction也是使用者可能需要用到的接口。它表示一个lua函数的java实现。其回调方法execute(Object[])方法会传入从lua端输入的数据，并输出一个结果传回lua端。如果方法本身不需要返回数据，则返回null即可。

# lua端函数描述

在mLua下，lua原来的require、print函数已经被改写。

## require

require必须使用设置在java端的basedir为根目录的相对路径引用其他lua脚本：

``` lua
require "dir1/dir2/script1"
require "script2"
```

## print

支持输出一个或多个对象，但是不能将string与java对象作拼接：

``` lua
-- 正确的做法 --
print("hello mLua")
print("context: ", getContext())
print("string " .. 111)

-- 错误的做法 --
print("context: " .. getContext())
```

通过逗号分隔的对象会在java端以tab号分隔显示

## 操作java对象

mLua也采用反射来操作java对象，不过**mobTools**的ReflectHelper具备缓存功能，理论上会比luajava每次直接反射更快。mLua提供了如下的内置函数：

函数名称                                       | 函数解释
--------------------------------------------- | ----------
import(className)                             | 向ReflectHelper类缓存中导入一个类，此函数将返回一个<br/>string，用于后续代码从缓存中重新获取导入的类实例
import(name, className)                       | 向ReflectHelper类缓存中导入一个类，并将此缓存的key<br/>设置为指定名称
new(className, ...)                           | 构造一个java实例，参数className是import函数的返回值，<br/>后续参数为java构造方法的输入参数
invokeStatic(className, methodName, ...)      | 调用一个java的静态方法
invoke(receiver, methodName, ...)             | 调用一个Java的实例方法
getStatic(className, fieldName)               | 获取一个java的静态字段
setStatic(className, fieldName, value)        | 设置一个java的静态字段
get(receiver, fieldName)                      | 获取一个java的实例字段
set(receiver, fieldName, value)               | 设置一个java的实例字段
createProxy(proxyTable, ...)                  | 构造一个java接口代理。参数proxyTable是一个lua的table，<br/>其中的key必须与java接口类的方法名称相同，key对应的<br/>value是一个lua的function，function的参数列表和返回值也<br/>必须与java接口相同。proxyTable后的参数是被实现的接口<br/>列表名称，皆为string，由import函数返回。此函数将返回一<br/>个java接口代理实例，可将此实例传回java端并进行操作，<br/>当实例中的接口函数被调用时，mLua会调用proxyTable中的<br/>对应funtion代码完成操作

# 例子

## java端代码

``` java
public class MainActivity extends Activity {
	private MLua lua;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 构造一个解析器实例
		lua = new MLua();
		// 设置lua代码存放位置
		lua.setBasedir("/sdcard/mLua/LuaTest");
		// push全局对象
		lua.pushGlobal("getContext", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				return getApplication();
			}
		});
		try {
			// 启动解析器，设置main.lua为入口代码
			lua.start("main");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	protected void onDestroy() {
		// 关闭解析器
		lua.stop();
		super.onDestroy();
	}
}
```

## lua端代码

``` lua
-- 导入ReflectHelper.ReflectRunnable类，并命名为ReflectRunnable --
import("ReflectRunnable", "com.mob.tools.utils.ReflectHelper$ReflectRunnable")

local function main()
  -- 演示print和invoke --
  print("hello world from mLua")
  local context = getContext()
  print("current context: ", context)
  local packageName = invoke(context, "getPackageName")
  print("packageName: ", packageName)
  
  -- 演示java接口代理 --
  local luaCode = {
    run = function(arg)
      print("luaCode.run(), input: ", arg)
      return "yoyoyo"
    end
  }
  local proxy = createProxy(luaCode, "ReflectRunnable")
  local res = invoke(proxy, "run", packageName)
  print("luaCode.run(), output: ", res)
  
  -- 演示数组复制 --
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
```

# 扩展

mLua默认只能从文件系统中加载lua代码，但是如果对MLua的setBasedir方法进行重写，以其他的方式实现SourceLoader，则可以加载任意方式的lua代码，包括assets中的，和加密的。
