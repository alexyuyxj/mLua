package m.lua.demo;

import m.lua.JavaFunction;
import m.lua.MLua;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
	private MLua lua;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lua = new MLua();
		lua.setBasedir("/sdcard/mLua/LuaTest");
		lua.pushGlobal("getContext", new JavaFunction() {
			public Object execute(Object[] args) throws Throwable {
				return getApplication();
			}
		});
		try {
			lua.start("main");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	protected void onDestroy() {
		lua.stop();
		super.onDestroy();
	}
	
}
