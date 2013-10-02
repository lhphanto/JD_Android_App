package android.JohnDeere.jd_test;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class HandleXMLData extends DefaultHandler{

	private FinalValue data = new FinalValue();
	private boolean is_desc =false;
	private boolean is_item =false;
	
	public String getInfo(){
		return data.dataToOutput();
	}
	
	public String getWOEID(){
		return data.woeidstring();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {

		String temps = null;
		if (qName.equals("yweather:location")){
			String city = attributes.getValue("city");
			temps = attributes.getValue("region");
			if (temps.length() > 0)
				city = city + ", " + temps;
			city = city + ", " + attributes.getValue("country");
			data.setCity(city);
		}
		else if (qName.equals("yweather:condition")){
			int temp = Integer.parseInt(attributes.getValue("temp"));
			data.setTemp(temp);
			int code = Integer.parseInt(attributes.getValue("code"));
			data.setCode(code);
			data.setDesc(attributes.getValue("text"));
		}
		else if (qName.equals("locality1")) {
			String woeid = attributes.getValue("woeid");
			data.setWOEID(woeid);
		}
		else if (qName.equals("yweather:wind")) {
			String wind = attributes.getValue("speed");
			data.setWind(wind);
		}		
		else if (qName.equals("description")) {
			is_desc = true;
		}
		else if (qName.equals("item")) {
			is_item = true;			
		}
		
	}
	
	public void endElement(String uri, String localName,
			String qName) throws SAXException {
	 
		if (qName.equals("item")) {
			is_item = false;			
		}		
		else if (qName.equals("description")) {
			is_desc = false;			
		}	 
	}
	
	public void characters(char ch[], int start, int length) throws SAXException {
		
	}

	
}
