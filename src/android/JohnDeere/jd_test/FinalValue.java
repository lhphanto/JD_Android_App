package android.JohnDeere.jd_test;

public class FinalValue {
	
	private int temp = 0;
	private String city = null;
	private String woeid = null;
	private String wind = null;
	
	public void setCity(String c) {
		this.city = c;
	}
	public void setTemp(int t) {
		this.temp = t;
	}
	
	public String dataToOutput() {
		return "City: " + city + " \nTemp: " + temp + " F\nWind: " + wind + "mph";
	}
	
	public void setWOEID(String w) {
		this.woeid = w;
	}

	public String woeidstring() {
		return woeid;
	}
	
	public void setWind(String w) {
		this.wind = w;
	}

}
