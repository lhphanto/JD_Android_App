package android.JohnDeere.jd_test;


import java.io.IOException;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpRequest {
	private String Url;
	private String Op;
	public HttpRequest(String url,String operation){
		this.Url = url;
		this.Op  = operation;
	}
	
	public Bitmap GetImage(Bitmap ini){		
		Bitmap bitmap=ini;
		String cmd =this.Url+"?"+this.Op;
		HttpClient httpclient = new DefaultHttpClient();
	        // Prepare a request object
	    HttpGet httpget = new HttpGet(cmd); 
	    Log.i("HttpRequest", "Finish preparation");
	        // Execute the request
	    HttpResponse response;
	    try {
	    	response = httpclient.execute(httpget);
	            // Examine the response status
	    	StatusLine statusLine = response.getStatusLine();
	        Log.i("HttpRequest",statusLine.toString());
	 
	        
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	        	Header[] myheaders = response.getAllHeaders();
	        	for(int i=0; i < myheaders.length;i++){
	        		Log.i("HttpRequest", "header "+i+" is "+myheaders[i].toString());
	        	}
	            /*
	        	HttpEntity entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	            Log.i("HttpRequest", "CropScape_Bytes:"+bytes.length);
	            bitmap = BitmapFactory.decodeByteArray(bytes, 0,
	                    bytes.length);
	                    */
	            
	        } else {
	            throw new IOException("Download failed, HTTP response code "
	                    + statusCode + " - " + statusLine.getReasonPhrase());
	        }	         
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (IOException e){
	    	e.printStackTrace();
	    }
	    
	    return bitmap;
	}
	
	
	

}
