package android.JohnDeere.jd_test;


public class US_Region {
	public String fips;
	public String name;
	public String crop_image_url =null;
	public double north=0.0;
	public double west =0.0;
	public double south=0.0;
	public double east =0.0;
	public int rotation = 0;
	US_Region(String input){
		String[] tokens = input.split(",");
		
		this.fips = tokens[0];
		int county = 0;
		if(tokens.length > 2 && tokens[2].contains("\"")){
			this.name = tokens[1].substring(1,tokens[1].length())+","+tokens[2].substring(0,tokens[2].length()-1);
			county = 1;
		}else{
			this.name = tokens[1];
		}
		if(tokens.length > 3){
			this.crop_image_url = tokens[2+county];
			this.north = Double.parseDouble(tokens[3+county]);
			this.west = Double.parseDouble(tokens[4+county]);
			this.south = Double.parseDouble(tokens[5+county]);
			this.east = Double.parseDouble(tokens[6+county]);
			this.rotation = Integer.parseInt(tokens[7+county]);
		}
		//Log.i("US_Region",this.name);
	}
	
	public String toString(){
		return this.name;
	}
}
