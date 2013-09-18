package android.JohnDeere.jd_test;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.JohnDeere.jd_test.HttpRequest;
import android.JohnDeere.jd_test.Albers_Coors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GMapFragment extends SupportMapFragment {

	//private MapView myMapView;
	   
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
		View view = super.onCreateView( inflater, container, savedInstanceState );
		//View view =inflater.inflate(R.layout.map_frag, container, false);
		//myMapView = (MapView)view.findViewById(R.id.gg_map);
		GoogleMap map = getMap();
		map.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(40.08897,-88.23875) , 14.0f));
		
		LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
		
		Albers_Coors ne_bound = new Albers_Coors(bounds.northeast);
		Albers_Coors sw_bound = new Albers_Coors(bounds.southwest);
		
		//new DownloadImageTask().execute("http://nassgeodata.gmu.edu:8080/axis2/services/CDLService/GetCDLFile",
		//		   "year=2012&bbox=130783,2203171,153923,2217961");
		
		new DownloadImageTask().execute("http://129.174.131.7/cgi/wms_cdlall.cgi",
				"SERVICE=WMS&VERSION=1.1.1&REQUEST=GetMap&LAYERS=cdl_2012&STYLES=&SRS=EPSG:4326&BBOX=-96,40,-90,43&WIDTH=800&HEIGHT=400&FORMAT=image/png");
	       
		 
		return view;
	}
	
	
	
	 @Override
     public void onDestroy() {
             super.onDestroy();
     }
	 
	 @Override
     public void onResume() {
             super.onResume();
     }
	 
	 
	 private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
	     protected Bitmap doInBackground(String...urls ) {
	    	 HttpRequest test_request = new HttpRequest(urls[0],urls[1]);
			 return test_request.GetImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
	     }

	     protected void onPostExecute(Bitmap result) {
	    	 Drawable d = new BitmapDrawable(getResources(),result);
	    	 //text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, d);
	     }
	 }


}
