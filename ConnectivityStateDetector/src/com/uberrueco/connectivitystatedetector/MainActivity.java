package com.uberrueco.connectivitystatedetector;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private BroadcastReceiver myConnectivityStateReceiver = new BroadcastReceiver(){

		@Override
	    public void onReceive(Context context, Intent intent) {
	        NetworkInfo networkStateInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
	        if(null != networkStateInfo){
	        	setNetworkStateMessage(networkStateInfo.getState());
	        }
	    }
	    
	    private void setNetworkStateMessage(NetworkInfo.State state){
	    	String res = "Unknown";
	        switch(state){
	            case CONNECTED:         
	            	res = "Connected :D";
	            	stateTextView.setTextColor(Color.GREEN);
	            break;
	            
	            case CONNECTING:    
	            	res = "Connecting :)";
	            	stateTextView.setTextColor(Color.BLUE); 
	            break;
	            
	            case DISCONNECTED:  
	            	res = "Disconnected D:";
	            	stateTextView.setTextColor(Color.RED);   
	            break;
	            
	            case DISCONNECTING: 
	            	res = "Disconnecting :(";
	            	stateTextView.setTextColor(Color.YELLOW); 
	            break;
	            
	            case SUSPENDED:         
	            	res = "Suspended -.-";
	            	stateTextView.setTextColor(Color.GRAY);              
	            break;
	            
	            default:                
	            	res = "?";
	            	stateTextView.setTextColor(Color.GREEN);               
	            break;
	        }
	        stateTextView.setText(res);
	    }
	};
	TextView stateTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		stateTextView = (TextView) findViewById(R.id.stateTextView);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.registerReceiver(myConnectivityStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(myConnectivityStateReceiver);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
