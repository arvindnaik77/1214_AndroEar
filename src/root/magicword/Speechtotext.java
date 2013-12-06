package root.magicword;

import java.util.ArrayList;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class Speechtotext extends Activity implements OnInitListener,
		SimpleGestureListener {

	ImageButton mic;
	ImageButton set;
	private TextView result;
	private TextToSpeech tts;
	private int SPEECH_REQUEST_CODE = 1234;
	private SensorManager sensorManager;
	private DetectorThread detectorThread;
	private RecorderThread recorderThread;
	private SimpleGestureFilter detector;
	String mostLikelyThingHeard;
	public static final int DETECT_NONE = 0;
	public static final int DETECT_WHISTLE = 1;
	public static int selectedDetection = DETECT_NONE;
	Vibrator vibe;
	Context context;
	static Speechtotext mainApp;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.speech_to_text);
		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		
		context = this;
		mainApp = this;

		//stopped();
		/*
		 * recorderThread = new RecorderThread();
		 * recorderThread.stopRecording(); detectorThread = new
		 * DetectorThread(recorderThread); detectorThread.stopDetection();
		 */

		

		detector = new SimpleGestureFilter(this, this);
		mic = (ImageButton) findViewById(R.id.imageButton1);
		set = (ImageButton) findViewById(R.id.imageButton2);
		result = (TextView) findViewById(R.id.textView2);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		mic.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vibe.vibrate(100);
				sendRecognizeIntent();
			}
		});

		set.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				vibe.vibrate(100);
				Intent intent = new Intent(Intent.ACTION_MAIN);
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
/*				if (currentapiVersion >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
					// Do something for ics and below versions
					intent.setClassName(
							"com.google.android.googlequicksearchbox",
							"com.google.android.voicesearch.VoiceSearchPreferences");
				} else {
					*/
/*					intent.setClassName("com.google.android.voicesearch",
							"com.google.android.voicesearch.VoiceSearchPreferences");
					
					
					// do something for phones running an SDK after ics
					intent.setClassName(
							"com.google.android.googlequicksearchbox",
							"com.google.android.voicesearch.VoiceSearchPreferences");
				}*/
				intent.setClassName("com.android.quicksearchbox",
						"com.google.android.voicesearch");

				// intent.setClassName("com.google.android.googlequicksearchbox",
				// "com.google.android.voicesearch.VoiceSearchPreferences");
				startActivity(intent);
			}
		});

		mic.setEnabled(false);
		tts = new TextToSpeech(this, this);

	}

/*	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	//	finish();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent myIntent;
		myIntent = new Intent(Speechtotext.this, MagicWord.class);
		Speechtotext.this.startActivity(myIntent);
	}*/

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
			mic.setEnabled(true);
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
					tts.speak("Heard nothing", TextToSpeech.QUEUE_FLUSH, null);
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
					}
				}
				// result.setText("heard: " + matches);
				result.setText("" + mostLikelyThingHeard);
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

			Intent myIntent = new Intent(Speechtotext.this, MagicWord.class);
			Speechtotext.this.startActivity(myIntent);
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {

		this.detector.onTouchEvent(me);

		return super.dispatchTouchEvent(me);

	}
	public void stopped()
	{
		
/*		Toast.makeText(Speechtotext.this,
				  "stopped", Toast.LENGTH_SHORT).show();*/
		
		
		selectedDetection = DETECT_NONE;
		//recorderThread = new RecorderThread();
		recorderThread.stopRecording();
		//detectorThread = new DetectorThread(recorderThread);
		//detectorThread.setOnSignalsDetectedListener(Speechtotext.mainApp);
		//detectorThread.stopDetection();
		// goListeningView();
		 

		
		}
	
	

	@Override
	public void onSwipe(int direction) {
		Intent myIntent;
		// TODO Auto-generated method stub
		switch (direction) {
		case SimpleGestureFilter.SWIPE_LEFT:
			myIntent = new Intent(Speechtotext.this, Texttospeech.class);
			Speechtotext.this.startActivity(myIntent);
			// Toast.makeText(this, "Left", Toast.LENGTH_SHORT).show();
			break;
		case SimpleGestureFilter.SWIPE_RIGHT:
			myIntent = new Intent(Speechtotext.this, Texttospeech.class);
			Speechtotext.this.startActivity(myIntent);
			// Toast.makeText(this, "Right", Toast.LENGTH_SHORT).show();
		}

	}


}
