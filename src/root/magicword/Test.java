package root.magicword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Test extends Activity {

	TextView header;
	Button play, heard, didnthear;
	String link;
	private MediaPlayer mp;

	int i = 1;
	boolean flag = false;
	int hearcount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tests);
		final Context context = this;
		header = (TextView) findViewById(R.id.textView1);
		play = (Button) findViewById(R.id.button1);
		heard = (Button) findViewById(R.id.button2);
		didnthear = (Button) findViewById(R.id.button3);
		header.setText("Test " + 1 + "/20");

		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		header.setTypeface(tf1);

		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = true;
				switch (i) {
				case 1:
					mp = MediaPlayer.create(context, R.raw._20hz);
					mp.start();

					break;
				case 2:
					mp = MediaPlayer.create(context, R.raw._30hz);
					mp.start();

					break;

				case 3:
					mp = MediaPlayer.create(context, R.raw._40hz);
					mp.start();

					break;

				case 4:
					mp = MediaPlayer.create(context, R.raw._50hz);
					mp.start();
					break;
				case 5:
					mp = MediaPlayer.create(context, R.raw._60hz);
					mp.start();
					break;
				case 6:
					mp = MediaPlayer.create(context, R.raw._100hz);
					mp.start();
					break;
				case 7:
					mp = MediaPlayer.create(context, R.raw._200hz);
					mp.start();
					break;
				case 8:
					mp = MediaPlayer.create(context, R.raw._500hz);
					mp.start();
					break;
				case 9:

					mp = MediaPlayer.create(context, R.raw._1000hz);
					mp.start();
					break;
				case 10:
					mp = MediaPlayer.create(context, R.raw._2000hz);
					mp.start();
					break;
				case 11:
					mp = MediaPlayer.create(context, R.raw._5000hz);
					mp.start();
					break;

				case 12:
					mp = MediaPlayer.create(context, R.raw._8000hz);
					mp.start();
					break;
				case 13:
					mp = MediaPlayer.create(context, R.raw._10000hz);
					mp.start();
					break;
				case 14:
					mp = MediaPlayer.create(context, R.raw._12000hz);
					mp.start();
					break;
				case 15:
					mp = MediaPlayer.create(context, R.raw._15000hz);
					mp.start();
					break;
				case 16:
					mp = MediaPlayer.create(context, R.raw._16000hz);
					mp.start();
					break;
				case 17:
					mp = MediaPlayer.create(context, R.raw._17000hz);
					mp.start();
					break;
				case 18:
					mp = MediaPlayer.create(context, R.raw._18000hz);
					mp.start();
					break;
				case 19:
					mp = MediaPlayer.create(context, R.raw._19000hz);
					mp.start();
					break;
				case 20:
					mp = MediaPlayer.create(context, R.raw._20000hz);
					mp.start();
					break;

				}

			}
		});

		heard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if (flag) {
					if (i < 20) {
						mp.release();
						i++;
						header.setText("Test " + i + "/20");
						hearcount++;
						flag = false;
					} else {
						hearcount++;
						Intent myIntent = new Intent(Test.this,
								Testresult.class);
						myIntent.putExtra("hearcount", hearcount);

						Test.this.startActivity(myIntent);
					}
				} else
					Toast.makeText(
							context,
							"Please play the sound before clicking the button!!!",
							Toast.LENGTH_LONG).show();

			}
		});

		didnthear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (flag) {
					if (i < 20) {
						mp.release();
						i++;
						header.setText("Test " + i + "/20");
						flag = false;
					} else {
						Intent myIntent = new Intent(Test.this,
								Testresult.class);
						myIntent.putExtra("hearcount", hearcount);

						Test.this.startActivity(myIntent);
					}

				} else
					Toast.makeText(
							context,
							"Please play the sound before clicking the button!!!",
							Toast.LENGTH_LONG).show();

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