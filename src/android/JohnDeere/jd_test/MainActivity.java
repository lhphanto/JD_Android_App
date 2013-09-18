package android.JohnDeere.jd_test;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;


public class MainActivity extends FragmentActivity {
	private FragmentTabHost TabHost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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

}
