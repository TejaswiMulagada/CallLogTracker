package com.call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainScreen extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainscreen);
		
		Thread logiTimer=new Thread(){
			public void run(){
				try{
					short logoTimer=50;
					while (logoTimer<1000) {
						sleep(300);
						logoTimer+=100;
					}
					startActivity(new Intent("com.call.tabactivity"));
				}
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally{
					finish();
				}
				
			}
			
			
		};
		logiTimer.start();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	

}
