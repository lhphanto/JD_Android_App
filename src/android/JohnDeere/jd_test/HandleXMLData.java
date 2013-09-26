package android.JohnDeere.jd_test;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HandleXMLData extends DefaultHandler{

	private FinalValue data = new FinalValue();
	
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
			String t = attributes.getValue("temp");
			int temp = Integer.parseInt(t);
			data.setTemp(temp);
		}
		else if (qName.equals("locality1")) {
			String woeid = attributes.getValue("woeid");
			data.setWOEID(woeid);
		}
		else if (qName.equals("yweather:wind")) {
			String wind = attributes.getValue("speed");
			data.setWind(wind);
		}
	}

	
}
