package android.JohnDeere.jd_test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends FragmentActivity {
	private FragmentTabHost TabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("JD_test_handleText","onCreate");
		handleIntent();
		TabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        TabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        TabHost.addTab(TabHost.newTabSpec("map").setIndicator("Map"),
                GMapFragment.class, null);
        TabHost.addTab(TabHost.newTabSpec("weather").setIndicator("Weather"),
                WeatherFragment.class, null);
        
       // mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentByTag("map")).getMap();
       // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onRestart(){
		super.onRestart();
		handleIntent();
	}
	
	void handleIntent(){
		// Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();

	    if (Intent.ACTION_SEND.equals(action) && type != null) {
	        if ("text/plain".equals(type)) {
	            handleSendText(intent); // Handle text being sent
	        } else if (type.startsWith("image/")) {
	           // Handle single image being sent
	        }
	    }
	}
	
	void handleSendText(Intent intent) {
	    String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
	    intent.removeExtra(Intent.EXTRA_TEXT);
	    if (sharedText != null) {
	        
	    	/***
	    	// Update UI to reflect text being shared
	    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
	    	alert.setTitle("Intent Message");
	    	alert.setMessage(sharedText);
	    	alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	    		public void onClick(DialogInterface dialog, int whichButton) {
	    		  // Do something with value!
	    		  }
	    		});

	    	alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	    		  public void onClick(DialogInterface dialog, int whichButton) {
	    		    // Canceled.
	    		  }
	    		});
	    		
	    	AlertDialog alertDialog = alert.create();
	    	alertDialog.show();
	    	*/
	    }
	}

}
