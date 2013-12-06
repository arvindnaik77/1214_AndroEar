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

public class Startup3 extends Activity implements SimpleGestureListener {

	private SensorManager sensorManager;
	private SimpleGestureFilter detector;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		boolean firstTime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("firstTime", true);
		getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
				.putBoolean("firstTime", false).commit();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup3);
		detector = new SimpleGestureFilter(this, this);

		TextView welcome = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		welcome.setTypeface(tf);
		welcome.setText("Congratulations!");
		TextView instructions = (TextView) findViewById(R.id.textView2);
		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		instructions.setTypeface(tf1);

		/*
		 * TextView swipe = (TextView) findViewById(R.id.textView3); Typeface
		 * tf2 = Typeface.createFromAsset(getAssets(),
		 * "fonts/RobotoItalic.ttf"); swipe.setTypeface(tf2);
		 * swipe.setText("Swipe to finish setup >>");
		 */

		instructions
				.setText(Html
						.fromHtml("<p>You have successfully completed setting up the app. Swipe to start exploring."));

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {

		this.detector.onTouchEvent(me);

		return super.dispatchTouchEvent(me);

	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this)
				.setTitle("Setup not completed")
				.setMessage("Are you sure you want to exit the application?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent();
								i.setAction(Intent.ACTION_MAIN);
								i.addCategory(Intent.CATEGORY_HOME);
								Startup3.this.startActivity(i);
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				}).show();

	}

	@Override
	public void onSwipe(int direction) {
		Intent myIntent;
		// TODO Auto-generated method stub
		switch (direction) {
	/*	case SimpleGestureFilter.SWIPE_LEFT:
			new AlertDialog.Builder(this)
			.setTitle("Setup is completed!")
			.setMessage("Kindly relaunch the app to for the setup to finalize!")
			.setPositiveButton("Okay",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							Intent i = new Intent();
							i.setAction(Intent.ACTION_MAIN);
							i.addCategory(Intent.CATEGORY_HOME);
							//Startup3.this.startActivity(i);
							finish();
						}
					}).show();*/
		case SimpleGestureFilter.SWIPE_LEFT:
			finish();
	
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/*
		 * overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		 * Toast.makeText(Forums.this, "destroyed!", Toast.LENGTH_SHORT).show();
		 */
	}

}
