package root.magicword;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class Aboutus extends Activity {

	TextView header;
	TextView version;
	TextView developers;
	TextView names;
	TextView college;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);

		header = (TextView) findViewById(R.id.textView1);
		version = (TextView) findViewById(R.id.textView2);
		developers = (TextView) findViewById(R.id.textView3);
		names = (TextView) findViewById(R.id.textView4);
		college = (TextView) findViewById(R.id.textView5);

		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoBold.ttf");
		header.setTypeface(tf1);
		header.setText("AndroEar");

		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		// version.setTypeface(tf1);

		Typeface tf3 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoItalic.ttf");
		version.setTypeface(tf1);

		version.setText("Version 1.0.0");
		developers.setTypeface(tf2);
		names.setTypeface(tf2);
		college.setTypeface(tf2);
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
