package android.JohnDeere.jd_test;

import com.google.android.gms.maps.model.LatLng;

public class Albers_Coors {
	public double x;
	public double y;
	
	private double ref_lon;
	private double ref_lat;
	private double std1;
	private double std2;
	
	public Albers_Coors(double x,double y){
		this.ref_lon = -96.0;
		this.ref_lat = 23.0;
		this.std1 = 29.5;
		this.std2 = 45.5;
		this.x =x;
		this.y =y;
	}
	
	public Albers_Coors(LatLng mylatlng ){
		double n = (Math.sin(std1)+Math.sin(std2))/2.0;
		double theta = n*(mylatlng.longitude-this.ref_lon);
		double C = Math.pow(Math.cos(std1), 2)+2*n*Math.sin(std1);
		double rou = Math.sqrt(C-2*n*Math.sin(mylatlng.latitude))/n;
		double rou0 = Math.sqrt(C-2*n*Math.sin(this.ref_lat))/n;
		this.x = rou*Math.sin(theta);
		this.y = rou0-rou*Math.cos(theta);
	}

}
