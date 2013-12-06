package root.magicword;

import root.magicword.SimpleGestureFilter.SimpleGestureListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Startup2 extends Activity implements SimpleGestureListener {

	private SensorManager sensorManager;
	private SimpleGestureFilter detector;
	Context context;

	EditText phonenumber;
	String phno, phno1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startup2);
		context = this;
		detector = new SimpleGestureFilter(this, this);
		phonenumber = (EditText) findViewById(R.id.editText1);

		TextView welcome = (TextView) findViewById(R.id.textView1);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		welcome.setTypeface(tf);
		welcome.setText("Emergency SMS");

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
						.fromHtml("<p>Please set a phone number to SMS in emergency situations."
								+ "Messages will be sent to this number when the phone is shook "
								+ "hard a number of times. Normal SMS rates applied."));

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
	        	Startup2.this.startActivity(i); 
	        	finish();  
	        }
	     })
	    .setNegativeButton("No", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        
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

			phno = phonenumber.getText().toString();
			//if (!(phno.equals(""))) {
			if (phno.length()>=10) {
				/*
				 * String phno1 = getSharedPreferences("PREFERENCE",
				 * MODE_PRIVATE) .getString("phone", "");
				 * getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
				 * .putString("phone", phno).commit();
				 */

				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(context);
				// phno = prefs.getString("phone", "");

				PreferenceManager.getDefaultSharedPreferences(context).edit()
						.putString("phone", phno).commit();

				myIntent = new Intent(Startup2.this, Startup3.class);
				Startup2.this.startActivity(myIntent);
				finish();

			} else if(phno.length()==0)
				Toast.makeText(Startup2.this, "Please enter a phone number",
						Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(Startup2.this, "Please enter a valid phone number",
						Toast.LENGTH_SHORT).show();

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
