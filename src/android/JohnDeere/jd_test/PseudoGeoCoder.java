package android.JohnDeere.jd_test;

import android.JohnDeere.jd_test.Address;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PseudoGeoCoder {
	public Address PseudoGeoCode(String in_addr){		
		JSONObject json=null;
    	Address result = new Address();
    	AsyncTask<String,Void,JSONObject> mytask = new GetLocationInfo().execute(in_addr);
    	String status="";
    	
    	try {
			json = mytask.get(10000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {         	
         	status = json.getString("status");         	
         	if(status.equals("OK")){

 	         	JSONArray georesults = ((JSONArray)json.getJSONArray("results"));
 	         	
 	         	//for(int i=0;i<georesults.length() ;i++)
 	 			//{
 	         	result.formattedAddr=georesults.getJSONObject(0).getString("formatted_address");
 	         	result.latitude =georesults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
 	         	result.longtitude =georesults.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
 	         	result.valid = true;
         	}else{
         		result.formattedAddr=status;
         		result.latitude = 0;
         		result.longtitude = 0;
         		result.valid = false;
         	}
  			//}         

         } catch (JSONException e) {
             e.printStackTrace();
         }
         
         return result;
	}
	
	 private class GetLocationInfo extends AsyncTask<String,Void,JSONObject> {
	     protected JSONObject doInBackground(String...urls ) {
	    	 JSONObject json=null;
	         try {

	         	String address = urls[0].replaceAll("\n", " ");
	 	        address = address.replaceAll(" ","%20");            
	 	      
	 	        HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");
	 	        HttpClient client = new DefaultHttpClient();
	 	        HttpResponse response;	        

	             response = client.execute(httppost);
	             HttpEntity entity = response.getEntity();
	             InputStream instream = entity.getContent();
	             
	             try {
	 	            json=new JSONObject(convertStreamToString(instream));
	             } catch (JSONException e) {
	                 // TODO Auto-generated catch block
	                 e.printStackTrace();
	             }
	 	        instream.close();            
	             
	         } catch (ClientProtocolException e) {
	         } catch (IOException e) {
	         }
	         
	         return json;
	     }	     
	 }

	public String convertStreamToString(InputStream is) {
     /*
      * To convert the InputStream to String we use the BufferedReader.readLine()
      * method. We iterate until the BufferedReader return null which means
      * there's no more data to read. Each line will appended to a StringBuilder
      * and returned as String.
      */
	     BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	     StringBuilder sb = new StringBuilder();
	
	     String line = null;
	     try {
	         while ((line = reader.readLine()) != null) {
	             sb.append(line + "\n");
	         }
	     } catch (IOException e) {
	         e.printStackTrace();
	     } finally {
	         try {
	             is.close();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
	     }
	     return sb.toString();
	 }

}
