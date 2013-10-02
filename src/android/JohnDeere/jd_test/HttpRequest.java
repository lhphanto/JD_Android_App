package android.JohnDeere.jd_test;


import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpRequest {
	private String Url;
	private String ltag="HttpRequest";
	
	public HttpRequest(String url){
		this.Url = url;
	}
	
	public Bitmap GetImage(Bitmap ini){		
		Bitmap bitmap=ini;
		HttpClient httpclient = new DefaultHttpClient();
	        // Prepare a request object
	    HttpGet httpget = new HttpGet(this.Url); 
	    Log.i(ltag, "Finish preparation");
	        // Execute the request
	    HttpResponse response;
	    try {
	    	response = httpclient.execute(httpget);
	            // Examine the response status
	    	StatusLine statusLine = response.getStatusLine();
	        Log.i(ltag,statusLine.toString());
	 
	        
	        int statusCode = statusLine.getStatusCode();
	        if (statusCode == 200) {
	        	String content_type = response.getHeaders("Content-Type")[0].getValue();
	        	Log.i(ltag, "content type is "+content_type);
	        	
	        	if(content_type.matches("(.*)application/xml(.*)")){
	        		String img_url = ParseXML(response.getEntity().getContent());
	        		if(img_url == "NA"){
	        			throw new IOException("Download failed, bad HTTP response with no URL ");
	        		}else{
	        			httpget = new HttpGet(img_url);
	        			response = httpclient.execute(httpget);
	    	            // Examine the response status
	        			statusLine = response.getStatusLine();
	        			Log.i(ltag,statusLine.toString());
	        			statusCode = statusLine.getStatusCode();
	        		    if (statusCode == 200) {
	        		    	content_type = response.getHeaders("Content-Type")[0].getValue();
	        		    }else{
	        		    	throw new IOException("Download image failed, HTTP response code "
	        	                    + statusCode + " - " + statusLine.getReasonPhrase());
	        		    }
	        			
	        		}
	        	}
	        	
	        	if(content_type.matches("(.*)image/(tif|png)(.*)")){
	        		Log.i(ltag,"Successfully get tif image");
	        		HttpEntity entity = response.getEntity();
	        		try{
	        			//final BufferedImage tif = ImageIO.read(new File("test.tif"));
	        		   // ImageIO.write(tif, "png", new File("test.png"));
	        			bitmap = BitmapFactory.decodeStream(entity.getContent());
	        		}catch (IOException e){
	        			Log.i(ltag, "stream can not be created", e);
	        		}catch (IllegalStateException e){
	        			Log.i(ltag, "content stream can not be created", e);
	        		}
	        		/*
	        		byte[] bytes = EntityUtils.toByteArray(entity);
		            Log.i(ltag, "CropScape_Bytes:"+bytes.length);
		            bitmap = BitmapFactory.decodeByteArray(bytes, 0,
		                    bytes.length);
		                    */
	        	}
	        	
	            /*
	        	HttpEntity entity = response.getEntity();
	            byte[] bytes = EntityUtils.toByteArray(entity);
	            Log.i(ltag, "CropScape_Bytes:"+bytes.length);
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
	    //bitmap=ini;
	    return bitmap;
	}
	
	
	private String ParseXML(InputStream input){
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		String ret_url ="NA";
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(input);
			doc.getDocumentElement().normalize();
			//String tmp = doc.getChildNodes().item(0).getChildNodes().item(0).getNodeName();
			//Log.i(ltag,"Node name is "+tmp);
			ret_url = doc.getElementsByTagName("returnURLArray").item(0).getTextContent();
		}catch (IOException e){
			Log.i(ltag,"Have error in parsing xml",e);
		}catch (SAXException e){
			Log.i(ltag,"Have error in parsing xml",e);
		}catch(ParserConfigurationException e){
			Log.i(ltag,"Can not build document Builder",e);
		}catch (DOMException e){
			Log.i(ltag,"Can not get return URL in xml",e);
		}
		return ret_url;
	}

}
