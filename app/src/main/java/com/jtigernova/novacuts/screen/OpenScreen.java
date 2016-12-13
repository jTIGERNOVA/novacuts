package com.jtigernova.novacuts.screen;

import android.os.Bundle;
import android.widget.TextView;
import com.jtigernova.novacuts.R;
import jtiger.ui.android.base.BaseActivity;

public class OpenScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.open);
	
	final TextView msg = (TextView) findViewById(R.id.msg);
	
	new Thread(new Runnable() {
	    
	    @Override
	    public void run() {
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		runOnUiThread(new Runnable() {
		    public void run() {
			msg.setText("Loading Barbers");
		    }
		});
		try {
		    Thread.sleep(2000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		runOnUiThread(new Runnable() {
		    public void run() {
			pushActivity(HomeScreen.class);
			
			finish();
		    }
		});
	    }
	}).start();
    }

    @Override
    protected void attachEvents() {
    }

    @Override
    public void onClick(int viewID) {
    }

}
