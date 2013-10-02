package android.JohnDeere.jd_test;

import android.JohnDeere.jd_test.YahooWeather;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeatherFragment extends Fragment implements OnClickListener {

	private TextView output;
	private EditText city;
	
	static String baseURL = "http://weather.yahooapis.com/forecastrss?w=";
	static String baseURL1 = "http://where.yahooapis.com/v1/places.q(";
	static String baseURL2 = ")?appid=";
	static String appid = "jcelBR7V34EdxTfy_m9BCzglOyvQsnJFT.Bbrc1Ijbyo86jj6O5j_2f.YLU1sJTknAHsYRTXRwk9dC.nVOvaI4nUJHWHjXA-";
    String cityURL = null;
    String woeidURL = null;
    String WeatherImage = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View view =inflater.inflate(R.layout.weather_frag, container, false);
        //setContentView(R.layout.activity_main);
        Button but = (Button)view.findViewById(R.id.button1);
        output = (TextView)view.findViewById(R.id.textView1);
        city = (EditText)view.findViewById(R.id.editText1);
        but.setOnClickListener(this);
        return view;        
    }

	@Override
	public void onClick(View arg0) {
		
		String c = city.getText().toString();
		YahooWeather api = new YahooWeather();
		output.setText(api.GetWeather(c));	
	}    
} 
