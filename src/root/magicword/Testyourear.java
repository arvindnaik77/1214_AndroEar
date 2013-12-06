package root.magicword;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Testyourear extends Activity {

	TextView instructions;
	TextView header;
	Button cont;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_your_ear);

		instructions = (TextView) findViewById(R.id.textView2);
		header = (TextView) findViewById(R.id.textView1);
		cont = (Button) findViewById(R.id.button1);
		
		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		header.setTypeface(tf1);
		instructions.setTypeface(tf1);
/*		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoItalic.ttf");
		instructions.setTypeface(tf2);*/
		

		instructions
				.setText(Html
						.fromHtml("<p>This test will determine the range of frequencies "
								+ "that your ear can hear.</p>"
								+ "<p> Put on a pair of headphones or ear buds and find a quite place. "
								+ "Warbled tones will be played softly at first. Simply mark the hear tone. "
								+ "If you can't hear the sound, mark that you cannot hear it.</p> "
								+ "At the end, your results will be shown."));

		cont.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(Testyourear.this, Test.class);
				Testyourear.this.startActivity(myIntent);
			}
		});

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
