package root.magicword;

import root.magicword.SimpleGestureFilter.SimpleGestureListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Html;
import android.view.MotionEvent;
import android.widget.TextView;

public class Startup extends Activity implements SimpleGestureListener {

	private SensorManager sensorManager;
	private SimpleGestureFilter detector;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup);
		detector = new SimpleGestureFilter(this, this);

		TextView welcome = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		welcome.setTypeface(tf);

		TextView instructions = (TextView) findViewById(R.id.textView2);
		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		instructions.setTypeface(tf1);

		TextView swipe = (TextView) findViewById(R.id.textView3);
		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoItalic.ttf");
		swipe.setTypeface(tf2);
		swipe.setText("Swipe for next >>");

		instructions
				.setText(Html
						.fromHtml("<p>Greetings from AndroEar! "
								+ "Kindly proceed through the application to setup for the first time"));

	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this)
	    .setTitle("Setup not completed")
	    .setMessage("Are you sure you want to exit the application?")
	    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	Intent i = new Intent();
	        	i.setAction(Intent.ACTION_MAIN);
	        	i.addCategory(Intent.CATEGORY_HOME);
	        	Startup.this.startActivity(i); 
	        	finish();  
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	     .show();

	}
		
	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {

		this.detector.onTouchEvent(me);

		return super.dispatchTouchEvent(me);

	}

	@Override
	public void onSwipe(int direction) {
		Intent myIntent;
		// TODO Auto-generated method stub
		switch (direction) {
		case SimpleGestureFilter.SWIPE_LEFT:
			myIntent = new Intent(Startup.this, Startup2.class);
			Startup.this.startActivity(myIntent);
			finish();
		}

	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	/*	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		Toast.makeText(Forums.this, "destroyed!",
				Toast.LENGTH_SHORT).show();*/
	}

}
