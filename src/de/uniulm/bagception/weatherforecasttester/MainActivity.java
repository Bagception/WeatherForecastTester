package de.uniulm.bagception.weatherforecasttester;

import de.uniulm.bagception.weatherforecasttester.MyResultReceiver.Receiver;
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

public class MainActivity extends Activity implements Receiver{

    public MyResultReceiver mReceiver; 
    private Button mGetWeatherForecastButton;
    private EditText mLatEditText;
    private EditText mLongEditText;
 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.mLatEditText = (EditText) findViewById(R.id.latEditText);
		this.mLongEditText = (EditText) findViewById(R.id.longEditText);
		this.mGetWeatherForecastButton = (Button) findViewById(R.id.getWeatherForeCast_btn);
		mGetWeatherForecastButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: Get text from editViews
				double lat1 = 40.0;
				double long1 = 10.0;
//				Intent i = new Intent(this, MyIntentService.class);
				Intent i = getPackageManager().getLaunchIntentForPackage("de.uniulm.bagception.weatherforecast");
                i.putExtra("nameTag","sohail" );
                i.putExtra("receiverTag", mReceiver);
                startActivity(i);
			}
		});
		
		mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Log.d("hahahaha", "it works!!!!!!!!!!!!!!!!!!!");
	}

}
