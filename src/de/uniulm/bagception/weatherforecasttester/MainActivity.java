package de.uniulm.bagception.weatherforecasttester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.uniulm.bagception.intentservicecommunication.MyResultReceiver;
import de.uniulm.bagception.intentservicecommunication.MyResultReceiver.Receiver;
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
import android.widget.Toast;

public class MainActivity extends Activity implements Receiver{

	private MyResultReceiver mResultreceiver;
	
    private Button mGetWeatherForecastButton;
    private EditText mLatEditText;
    private EditText mLongEditText;
    private EditText mAnswerBox;
    
    private JSONObject result;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mResultreceiver = new MyResultReceiver(new Handler());
		mResultreceiver.setReceiver(this);
		
		this.mLatEditText = (EditText) findViewById(R.id.latEditText);
		this.mLongEditText = (EditText) findViewById(R.id.longEditText);
		this.mAnswerBox = (EditText) findViewById(R.id.answerBox);
		this.mGetWeatherForecastButton = (Button) findViewById(R.id.getWeatherForeCast_btn);
		mGetWeatherForecastButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Get text from editViews
//				double lat1 = Double.parseDouble(mLatEditText.getText().toString());
//				double long1 = Double.parseDouble(mLongEditText.getText().toString());
				
				String serviceString = "de.uniulm.bagception.weatherforecast.WeatherForecastService";
				
				Intent i = new Intent(serviceString);
				i.putExtra("origin","A" );
//				i.putExtra("lat1", lat1);
//				i.putExtra("long1", long1);
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
		mAnswerBox.setText(result.toString());
	}
}
