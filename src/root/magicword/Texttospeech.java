package root.magicword;

import java.util.ArrayList;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import root.magicword.SimpleGestureFilter.SimpleGestureListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class Texttospeech extends Activity implements OnInitListener,
		SimpleGestureListener {

	EditText speak;
	ImageButton speaker;
	private TextView result;
	private TextToSpeech tts;
	private int SPEECH_REQUEST_CODE = 1234;
	private SensorManager sensorManager;
	private SimpleGestureFilter detector;
	String mostLikelyThingHeard;
	String value;
	Vibrator vibe;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_to_speech);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());

		detector = new SimpleGestureFilter(this, this);
		speaker = (ImageButton) findViewById(R.id.imageButton1);
		speak = (EditText) findViewById(R.id.editText1);
		speak.setText("Press Speaker to speak the typed sentence.", TextView.BufferType.EDITABLE);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		speak.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus == true) {
					if (speak
							.getText()
							.toString()
							.compareTo(
									"Press Speaker to speak the typed sentence.") == 0) {
						speak.setText("");
					}
				}
			}
		});

		speaker.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// sendRecognizeIntent();
				vibe.vibrate(100);
				value = speak.getText().toString();
				tts.speak(" " + value, TextToSpeech.QUEUE_FLUSH, null);

			}
		});

		speaker.setEnabled(false);
		tts = new TextToSpeech(this, this);

	}

	private void sendRecognizeIntent() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "You can now speak");
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
		startActivityForResult(intent, SPEECH_REQUEST_CODE);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			speaker.setEnabled(true);
		} else {
			// failed to init
			finish();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SPEECH_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				ArrayList<String> matches = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (matches.size() == 0) {
					// tts.speak("Heard nothing", TextToSpeech.QUEUE_FLUSH,
					// null);
				} else {
					mostLikelyThingHeard = matches.get(0);
					String magicWord = this.getResources().getString(
							R.string.magicword);
					if (mostLikelyThingHeard.equals(magicWord)) {
						// tts.speak("You said the magic word!",
						// TextToSpeech.QUEUE_FLUSH, null);
					} else {
						// tts.speak("The magic word is  " +
						// mostLikelyThingHeard
						// + " try again", TextToSpeech.QUEUE_FLUSH, null);

						tts.speak(" hello " + value, TextToSpeech.QUEUE_FLUSH,
								null);
					}
				}
				// result.setText("heard: " + matches);
				// result.setText("" + mostLikelyThingHeard);
			} else {
				// Log.d(TAG, "result NOT ok");
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		if (tts != null) {
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			Intent myIntent = new Intent(Texttospeech.this, MagicWord.class);
			Texttospeech.this.startActivity(myIntent);
		}
		return super.onKeyDown(keyCode, event);
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
			myIntent = new Intent(Texttospeech.this, Speechtotext.class);
			Texttospeech.this.startActivity(myIntent);
			// Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
			break;
		case SimpleGestureFilter.SWIPE_RIGHT:
			myIntent = new Intent(Texttospeech.this, Speechtotext.class);
			Texttospeech.this.startActivity(myIntent);
			// Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
		}

	}

}
