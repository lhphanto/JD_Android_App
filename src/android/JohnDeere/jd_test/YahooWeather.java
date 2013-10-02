package android.JohnDeere.jd_test;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.os.AsyncTask;
import android.util.Log;


public class YahooWeather {

	
	static String baseURL = "http://weather.yahooapis.com/forecastrss?w=";
	static String baseURL1 = "http://where.yahooapis.com/v1/places.q(";
	static String baseURL2 = ")?appid=";
	static String appid = "jcelBR7V34EdxTfy_m9BCzglOyvQsnJFT.Bbrc1Ijbyo86jj6O5j_2f.YLU1sJTknAHsYRTXRwk9dC.nVOvaI4nUJHWHjXA-";
    String cityURL = null;
    String woeidURL = null;
    String WeatherImage = null;
    private String woeid = null;	
	
	public String GetWeather(String city) {
		cityURL = baseURL1 + city + baseURL2 + appid;
		Log.i("Debug", "input city is "+city);
		cityURL = cityURL.replaceAll("\\s","%20");
		//cityURL = cityURL.replaceAll(",,",",");		
		
		AsyncTask<String,Void,String[]> mytask = new UrlTask().execute(cityURL);
		String[] result = null; 
		try {
			result = mytask.get(10000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(result[0] != null && result[0].equals("Error")){
   		 	return "Failed1: " + result[1];
   	 	}else{
			woeid = result[0];
   	 	}
	   
	   	woeidURL = baseURL + woeid;
		
		woeidURL.replaceAll("\\s","");
		
		Log.i("Weather","url is "+ woeidURL);
		
		mytask = new UrlTask().execute(woeidURL);
		try {
			result = mytask.get(10000,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(result[0] != null && result[0].equals("Error")){
   		 	return "Failed1: " + result[1];
   	 	}else{
   	 		return result[1];
   	 	}    	
		
	}
	
	

	 private class UrlTask extends AsyncTask<String,Void,String[]> {
	     protected String[] doInBackground(String...urls ) {	  
	    	URL APIsite;
	    	String[] res= new String[2];
			try {
				APIsite = new URL(urls[0]);
				HandleXMLData xmldata = new HandleXMLData();
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();			
				xr.setContentHandler(xmldata);
				xr.parse(new InputSource(APIsite.openStream()));
				res[0] = xmldata.getWOEID();
				res[1] = xmldata.getInfo();				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				res[0]="Error";
				res[1]=e.getMessage();
			} catch (IOException e) {				// TODO Auto-generated catch block
				e.printStackTrace();
				res[0]="Error";
				res[1]=e.getMessage();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				res[0]="Error";
				res[1]=e.getMessage();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				res[0]="Error";
				res[1]=e.getMessage();
			}
			return res;
	     }

	     protected void onPostExecute(String[] result){
	    	 
	     }
	 }    
} 
