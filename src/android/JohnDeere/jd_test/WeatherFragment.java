package android.JohnDeere.jd_test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends Fragment {
	private TextView text;
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
		   View view =inflater.inflate(R.layout.weather_frag, container, false);
		   text = (TextView) view.findViewById(R.id.wt_title);
		   text.setText("USDA Test");
		   text.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.ic_launcher));
		    // Inflate the layout for this fragment
		 
		
		 
	       return view;
	    }
	   
	   
	   
	
	

}
