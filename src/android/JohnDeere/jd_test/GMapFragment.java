package android.JohnDeere.jd_test;

import android.JohnDeere.jd_test.Address;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.JohnDeere.jd_test.HttpRequest;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



import android.os.AsyncTask;
import android.os.Bundle;


import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GMapFragment extends Fragment {

	private MapView myMapView;
	private GoogleMap map;
	private AutoCompleteTextView enter_posView;
	private Button enter_pos;
	private List<US_Region> suggestionList;
	private String tag="GMapFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
		//View view = super.onCreateView( inflater, container, savedInstanceState );
		super.onCreate(savedInstanceState);
		View view =inflater.inflate(R.layout.map_frag, container, false);
		
		enter_posView = (AutoCompleteTextView)view.findViewById(R.id.position);
		enter_pos = (Button)view.findViewById(R.id.enter);
		SetButton();		
		InitializeSugList();
        ArrayAdapter<US_Region> adapter = new ArrayAdapter<US_Region>(getActivity(),
                android.R.layout.select_dialog_item,this.suggestionList);

        enter_posView.setThreshold(1);
        enter_posView.setAdapter(adapter);
		try {
		     MapsInitializer.initialize(getActivity());
		} catch (GooglePlayServicesNotAvailableException e) {
		     e.printStackTrace();
		}
		myMapView = (MapView)view.findViewById(R.id.gg_map);	
		myMapView.onCreate(savedInstanceState);
		map = myMapView.getMap();
		if(map == null){
			Log.i(tag, "map is null\n");
		}
		
		// Setting a custom info window adapter for the google map
	    map.setInfoWindowAdapter(new InfoWindowAdapter() {
	        // Use default InfoWindow frame
	        @Override
	        public View getInfoWindow(Marker arg0) {
	            return null;
	        }
	        // Defines the contents of the InfoWindow
	        @Override
	        public View getInfoContents(Marker marker) {

	            // Getting view from the layout file info_window_layout
	            View v = getActivity().getLayoutInflater().inflate(R.layout.we_info_win, null);

	            // Getting reference to the TextView to set latitude
	            TextView we_city = (TextView) v.findViewById(R.id.we_city_name);
	            // Getting reference to the TextView to set longitude
	            TextView we_temp = (TextView) v.findViewById(R.id.we_temperature);
	            TextView we_cond = (TextView) v.findViewById(R.id.we_cond);
	            TextView we_temp_unit = (TextView) v.findViewById(R.id.we_temp_unit);
	            TextView we_wind = (TextView) v.findViewById(R.id.we_wind);

	            String[] tokens = marker.getTitle().split("\t");
	            we_city.setText(tokens[0]);
	            //we_temp.setText(Html.fromHtml(tokens[1]+"<sup>\u2109<sup>"));
	            we_temp.setText(tokens[1]);
	            we_temp_unit.setText(R.string.fahrenheit);
	            we_wind.setText(tokens[2]);
	            we_cond.setText(tokens[3]);
	          
	            Log.i(tag, "The token is "+tokens[4]);
	            int cond_id;
	            if(Integer.parseInt(tokens[4]) > -1  && Integer.parseInt(tokens[4]) < 48 ){	            	
	            	cond_id = getActivity().getResources().getIdentifier("we"+tokens[4],"drawable",getActivity().getPackageName());	            	
	            }else{
	            	cond_id = R.drawable.sunny_test;
	            }
	            we_temp_unit.setCompoundDrawablesWithIntrinsicBounds(null, null,getResources().getDrawable(cond_id),null);
	       
	  
	            return v;

	        }
	    });
		
	
		return view;
	}
	
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	 }
	
	 @Override
     public void onDestroy() {
             super.onDestroy();
             myMapView.onDestroy();
     }
	 
	 @Override
     public void onResume() {
             super.onResume();
             myMapView.onResume();
     }
	 
	 private void InitializeSugList(){
		 this.suggestionList = new ArrayList<US_Region>();
		 try{
			 InputStream inputStream = getActivity().getAssets().open("FipsCountyCodes.csv.succinct");
			 ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		     int i;
		     i = inputStream.read();
		     while (i != -1){
		    	 byteArrayOutputStream.write(i);
		    	 i = inputStream.read();
		     }
		     inputStream.close();
		  
			 String[] data = byteArrayOutputStream.toString().split("\n");
			 for(i=0; i < data.length;i++){
				 this.suggestionList.add(new US_Region(data[i]));
			 }
			 
		 }catch(IOException e){
			 Log.i(tag,"can not open Fips data file",e);
		 }
		 
	 }
	 
	 private void SetButton(){		 
		 enter_pos.setOnClickListener(new OnClickListener(){
				@Override 
				public void onClick(View view){
					 String entered_pos = enter_posView.getText().toString();
					 for(int i=0; i < suggestionList.size();i++){
						 if( suggestionList.get(i).name.equals(entered_pos)){
							 //Log.i(tag,suggestionList.get(i).fips+suggestionList.get(i).crop_image_url);
							 AsyncTask<String,Void,Bitmap> mytask = new DownloadImageTask().execute("http://nassgeodata.gmu.edu:8080/axis2/services/CDLService/GetCDLImage?files=http://nassgeodata.gmu.edu/nass_data_cache/byfips/CDL_2012_"+
									 suggestionList.get(i).fips+".tif&format=png");
							 LatLngBounds bbox = new LatLngBounds(new LatLng(suggestionList.get(i).south,suggestionList.get(i).west),
									 						new LatLng(suggestionList.get(i).north,suggestionList.get(i).east));
							 try {
								Bitmap image = mytask.get();
								map.addGroundOverlay(new GroundOverlayOptions()
						   		 .image(BitmapDescriptorFactory.fromBitmap(image))
						   		 .positionFromBounds(bbox)
						   		 .transparency((float)0)
								);
							 } catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							 } catch (ExecutionException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							 }							 
							 map.moveCamera( CameraUpdateFactory.newLatLngBounds(bbox ,1));
							 
							 PseudoGeoCoder coder = new PseudoGeoCoder();
							 Address county_loc =  coder.PseudoGeoCode(suggestionList.get(i).name);
							 if(county_loc != null){
								 YahooWeather we_api = new YahooWeather();
								 String[] tokens = suggestionList.get(i).name.split(",");
								 String we_info = we_api.GetWeather(tokens[1]);
								 tokens = we_info.split("\t");
								 int cond_id;
						         if(Integer.parseInt(tokens[4]) > -1  && Integer.parseInt(tokens[4]) < 48 ){	            	
						            	cond_id = getActivity().getResources().getIdentifier("we"+tokens[4],"drawable",getActivity().getPackageName());	            	
						         }else{
						            	cond_id = R.drawable.sunny_test;
						         }
								 map.addMarker(new MarkerOptions()
		                           .position(new LatLng(county_loc.latitude,county_loc.longtitude))
		                           .title(we_info)
		                           .icon(BitmapDescriptorFactory.fromResource(cond_id)));
							 }else{
								 Log.i(tag, "can not locate the county by Geocoder\n");
							 }
							 break;
						 }
					 }
				 }
		 });
		 return;
	 }
	 
	 private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
	     protected Bitmap doInBackground(String...urls ) {
	    	 HttpRequest test_request = new HttpRequest(urls[0]);	    	 
			 return test_request.GetImage(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
	     }

	     protected void onPostExecute(Bitmap result) {
	    	 
	    	 //text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, d);
	     }
	 }
	 
	 


}
