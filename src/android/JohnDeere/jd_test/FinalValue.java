package android.JohnDeere.jd_test;

public class FinalValue {
	
	private int temp = 0;
	private String city = null;
	private String woeid = null;
	private String wind = null;
	private String desc = null;
	private int code = -1;
	
	public void setCity(String c) {
		this.city = c;
	}
	public void setTemp(int t) {
		this.temp = t;
	}
	
	public String dataToOutput() {		
		return city + "\t" + temp + "\t" + wind + "\t"+desc+"\t"+code;
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
	
	public void setDesc(String w) {
		this.desc = w;
	}
	public void setCode(int c) {
		this.code = c;
	}
	public String codeToImgURL() {
		return "http://l.yimg.com/a/i/us/we/52/" + code + ".gif";
	}
	public int getCode () {
		return code;
	}
}
