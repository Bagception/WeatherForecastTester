package de.uniulm.bagception.weatherforecasttester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.uniulm.bagception.broadcastconstants.BagceptionBroadcastContants;
import de.uniulm.bagception.intentservicecommunication.MyResultReceiver;
import de.uniulm.bagception.intentservicecommunication.MyResultReceiver.Receiver;
import de.uniulm.bagception.services.ServiceNames;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements Receiver{

	private MyResultReceiver mResultreceiver;
	
    private Button mGetWeatherForecastButton;
    private EditText mLatEditText;
    private EditText mLongEditText;
    private TextView mCityTextView;
    private TextView mCityTextViewValue;
    private TextView mTempTextView;
    private TextView mTempTextViewValue;
    private TextView mWindTextView;
    private TextView mWindTextViewValue;
    private TextView mCloudsTextView;
    private TextView mCloudsTextViewValue;
    private TextView mTempMinTextView;
    private TextView mTempMinTextViewValue;
    private TextView mTempMaxTextView;
    private TextView mTempMaxTextViewValue;
    private TextView mRainTextView;
    private TextView mRainTextViewValue;
    
    
    private JSONObject result;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mResultreceiver = new MyResultReceiver(new Handler());
		mResultreceiver.setReceiver(this);
		
		this.mLatEditText = (EditText) findViewById(R.id.latEditText);
		this.mLongEditText = (EditText) findViewById(R.id.longEditText);
		
		this.mCityTextView = (TextView) findViewById(R.id.cityTextView);
		this.mCityTextViewValue = (TextView) findViewById(R.id.cityTextViewValue);
		this.mTempTextView = (TextView) findViewById(R.id.tempTextView);
		this.mTempTextViewValue = (TextView) findViewById(R.id.tempTextViewValue);
		this.mWindTextView = (TextView) findViewById(R.id.windTextView);
		this.mWindTextViewValue = (TextView) findViewById(R.id.windTextViewValue);
		this.mCloudsTextView = (TextView) findViewById(R.id.cloudsTextView);
		this.mCloudsTextViewValue = (TextView) findViewById(R.id.cloudsTextViewValue);
		this.mTempMinTextView = (TextView) findViewById(R.id.tempMinTextView);
		this.mTempMinTextViewValue = (TextView) findViewById(R.id.tempMinTextViewValue);
		this.mTempMaxTextView = (TextView) findViewById(R.id.tempMaxTextView);
		this.mTempMaxTextViewValue = (TextView) findViewById(R.id.tempMaxTextViewValue);
		this.mRainTextView = (TextView) findViewById(R.id.rainTextView);
		this.mRainTextViewValue = (TextView) findViewById(R.id.rainTextViewValue);
		this.mGetWeatherForecastButton = (Button) findViewById(R.id.getWeatherForeCast_btn);
		this.mLatEditText.setText("48.404192");
		this.mLongEditText.setText("9.980428");
		
		this.mTempMinTextView.setVisibility(TextView.INVISIBLE);
		this.mTempMinTextViewValue.setVisibility(TextView.INVISIBLE);
		this.mTempMaxTextView.setVisibility(TextView.INVISIBLE);
		this.mTempMaxTextViewValue.setVisibility(TextView.INVISIBLE);
		this.mRainTextView.setVisibility(TextView.INVISIBLE);
		this.mRainTextViewValue.setVisibility(TextView.INVISIBLE);
		
		mGetWeatherForecastButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Get text from editViews
//				double lat = Double.parseDouble(mLatEditText.getText().toString());
//				double lng = Double.parseDouble(mLongEditText.getText().toString());
				
				
				String serviceString = ServiceNames.WEATHER_FORECAST_SERVICE;
				
				Intent i = new Intent(serviceString);
//				i.putExtra("lat", lat);
//				i.putExtra("lng", lng);
				i.putExtra("lat", 48.404192);
				i.putExtra("lng", 9.980428);
				i.putExtra("receiverTag", mResultreceiver);
				startService(i);
			}
		});
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		String s = resultData.getString("payload");
		
		try {
			JSONObject object = new JSONObject(s);
			setWeatherData(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setWeatherData(JSONObject result) throws JSONException{
		Log.d("RESUUUUUUHUHULT", result.toString());
		mCityTextViewValue.setText(result.getString("city"));
		mTempTextViewValue.setText(result.getString("temp") + " °C");
		mWindTextViewValue.setText(result.getString("wind") + " km/h");
		mCloudsTextViewValue.setText(result.getString("clouds") + " %");
		
		String tempMin = result.getString("tempMin") + " °C";
		String tempMax = result.getString("tempMax") + " °C";
		String rain    = "" + 100*Double.parseDouble(result.getString("rain")) + " %";
		if(tempMin!=null){
			mTempMinTextView.setVisibility(TextView.VISIBLE);
			mTempMinTextViewValue.setVisibility(TextView.VISIBLE);
			mTempMinTextViewValue.setText(tempMin);
		}else{
			mTempMinTextView.setVisibility(TextView.INVISIBLE);
			mTempMinTextViewValue.setVisibility(TextView.INVISIBLE);
		}
		if(tempMax!=null){
			mTempMaxTextView.setVisibility(TextView.VISIBLE);
			mTempMaxTextViewValue.setVisibility(TextView.VISIBLE);
			mTempMaxTextViewValue.setText(tempMax);
		}else{
			mTempMaxTextView.setVisibility(TextView.INVISIBLE);
			mTempMaxTextViewValue.setVisibility(TextView.INVISIBLE);
		}
		if(rain!=null){
			mRainTextView.setVisibility(TextView.VISIBLE);
			mRainTextViewValue.setVisibility(TextView.VISIBLE);
			mRainTextViewValue.setText(rain);
		}else{
			mRainTextView.setVisibility(TextView.INVISIBLE);
			mRainTextViewValue.setVisibility(TextView.INVISIBLE);
		}
	}
}
