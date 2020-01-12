package com.call;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;

public class tabactivity extends TabActivity {
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, calllogs.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("All").setIndicator("",
	                      res.getDrawable(R.drawable.allcall))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    
	    
	    
	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, IncomingCallActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    spec = tabHost.newTabSpec("Income").setIndicator("",
	                      res.getDrawable(R.drawable.incoming))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, MissedCallActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    spec = tabHost.newTabSpec("Missed").setIndicator("",
	                      res.getDrawable(R.drawable.missedcall))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    intent = new Intent().setClass(this, OutGoingCallActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    spec = tabHost.newTabSpec("Out").setIndicator("",
	                      res.getDrawable(R.drawable.outgoing))
	                  .setContent(intent);
	    tabHost.addTab(spec);
	    tabHost.setCurrentTab(0);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK ) {
	     onDestroy();
	    }

	    return super.onKeyDown(keyCode, event);
	}
	protected void onDestroy() {
		  super.onDestroy();
		  android.os.Process.killProcess(android.os.Process.myPid());
		 }
	
}