/**
 * this is free software 
 * It is under the The MIT License http://www.opensource.org/licenses/mit-license.php
 */
package root.magicword;

import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.SessionStore;

public class MagicWord extends Activity implements OnInitListener,
		OnSignalsDetectedListener {
	private static final String TAG = "MagicWord";

	static MagicWord mainApp;

	public static final int DETECT_NONE = 0;
	public static final int DETECT_WHISTLE = 1;
	public static int selectedDetection = DETECT_NONE;
	SharedPreferences prefs;
	// detection parameters
	private DetectorThread detectorThread;
	private RecorderThread recorderThread;
	private int numWhistleDetected = 0;

	// views
	private View mainView, listeningView;
	private Button whistleButton;

	private boolean doubleBackToExitPressedOnce = false;
	private TextView result;
	private TextToSpeech tts;
	private Button speak;
	private int SPEECH_REQUEST_CODE = 1234;
	private SensorManager sensorManager;
	String mostLikelyThingHeard;

	ImageButton speech_to_text;
	ImageButton text_to_speech;
	ImageButton settings;
	ImageButton test_your_ear;
	ImageButton like;
	ImageButton forums;
	ImageButton video;
	ImageButton aboutus;

	int cnt;

	TextView quotes;
	Button about;
	Vibrator vibe;
	Context context;
	/* ShakeListener mShaker; */
	private SharedPreferences mPreferences;
	private Facebook mFacebook;
	/* private CheckBox mFacebookBtn; */
	private ProgressDialog mProgress;
	Intent myIntent;
	int i = 1;
	final String review = "Hi, I'm uisng AndroEar for Android. Its an awesome app. I think you must give it a try!!!";

	private static final String[] PERMISSIONS = new String[] {
			"publish_stream", "read_stream", "offline_access" };

	private Handler mRunOnUi = new Handler();

	private static final String APP_ID = "638727409478071";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		context = this;
		mainApp = this;
		started();
		boolean firstTime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getBoolean("firstTime", true);
		if (firstTime) {
			/*
			 * getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
			 * .putBoolean("firstTime", false).commit();
			 */
			Intent myIntent = new Intent(MagicWord.this, Startup.class);
			MagicWord.this.startActivity(myIntent);
		}

		/*
		 * if (i == 1) { mShaker = new ShakeListener(this); mPreferences =
		 * PreferenceManager .getDefaultSharedPreferences(context); String val =
		 * mPreferences.getString("force", "1500"); Toast.makeText(this, " " +
		 * val, Toast.LENGTH_SHORT).show(); // mShaker.setThreshold(val); }
		 */

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		//started();
			
		startService(new Intent(this, Broadcastreceiver.class));
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		speech_to_text = (ImageButton) findViewById(R.id.imageButton3);
		text_to_speech = (ImageButton) findViewById(R.id.imageButton1);
		aboutus = (ImageButton) findViewById(R.id.imageButton8);
		settings = (ImageButton) findViewById(R.id.imageButton6);
		like = (ImageButton) findViewById(R.id.imageButton7);
		test_your_ear = (ImageButton) findViewById(R.id.imageButton2);
		forums = (ImageButton) findViewById(R.id.imageButton4);
		video = (ImageButton) findViewById(R.id.imageButton5);
		quotes = (TextView) findViewById(R.id.textView2);
		quotes.setTextColor(Color.WHITE);
		quotes.setSelected(true);

		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/MyriadPro.otf");
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setTypeface(tf);
		tv.setText("AndroEar");

		Typeface tf2 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoItalic.ttf");
		quotes.setTypeface(tf2);

		cnt = getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).getInt("cnt", 1);
		// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
		displayquote();

		speech_to_text.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				stopped();
				Intent myIntent = new Intent(MagicWord.this, Speechtotext.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

		video.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				Intent myIntent = new Intent(MagicWord.this, Video.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

		/*
		 * about.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * 
		 * Intent myIntent = new Intent(MagicWord.this, Aboutus.class);
		 * MagicWord.this.startActivity(myIntent);
		 * 
		 * }
		 * 
		 * });
		 */

		forums.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				/*
				 * recorderThread.stopRecording();
				 * detectorThread.stopDetection();
				 */
				Intent myIntent = new Intent(MagicWord.this, Forums.class);
				MagicWord.this.startActivity(myIntent);
				overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

			}

		});

		test_your_ear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				Intent myIntent = new Intent(MagicWord.this, Testyourear.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

		aboutus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				Intent myIntent = new Intent(MagicWord.this, Aboutus.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

		text_to_speech.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				recorderThread.stopRecording();
				detectorThread.stopDetection();
				Intent myIntent = new Intent(MagicWord.this, Texttospeech.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

		like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				/*
				 * Intent myIntent = new Intent(MagicWord.this,
				 * TestConnect.class); MagicWord.this.startActivity(myIntent);
				 */
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

				callfacebook();

			}

		});

		settings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				Intent myIntent = new Intent(MagicWord.this, Settings.class);
				MagicWord.this.startActivity(myIntent);
				// overridePendingTransition(R.anim.hold, R.anim.fade_in);

			}

		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		displayquote();

	}

	void started() {

		selectedDetection = DETECT_WHISTLE;
		recorderThread = new RecorderThread();
		recorderThread.start();
		detectorThread = new DetectorThread(recorderThread);
		detectorThread.setOnSignalsDetectedListener(MagicWord.mainApp);
		detectorThread.start();
		goListeningView();
	}

	public void stopped() {
		/*Toast.makeText(MagicWord.this, "Stopped", Toast.LENGTH_SHORT).show();*/
		recorderThread.stopRecording();
		detectorThread.stopDetection();
	}

	private void goHomeView() {
		// setContentView(mainView);
		if (recorderThread != null) {
			recorderThread.stopRecording();
			recorderThread = null;
		}
		if (detectorThread != null) {
			detectorThread.stopDetection();
			detectorThread = null;
		}
		selectedDetection = DETECT_NONE;
	}

	private void goListeningView() {
		// setContentView(listeningView);
	}

	/*
	 * speak = (Button) findViewById(R.id.bt_speak);
	 * speak.setOnClickListener(new View.OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { sendRecognizeIntent(); } });
	 * 
	 * speak.setEnabled(false); result = (TextView)
	 * findViewById(R.id.tv_result);
	 * 
	 * tts = new TextToSpeech(this, this);
	 * 
	 * }
	 */

	public void callfacebook() {
		mProgress = new ProgressDialog(this);
		mFacebook = new Facebook(APP_ID);
		SessionStore.restore(mFacebook, this);
		mFacebook.authorize(this, PERMISSIONS, -1, new FbLoginDialogListener());

	}

	public void lgout() {
		fbLogout();

	}

	private void postToFacebook(String review) {
		mProgress.setMessage("Posting ...");
		mProgress.show();

		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);

		Bundle params = new Bundle();

		params.putString("message", review);
		params.putString("name", "AndroEar");
		params.putString("caption",
				"AndroEar : An Android app for the hearing impaired");
		// params.putString("link", "http://www.londatiga.net");
		params.putString("description", "Jaseem Abbas likes AndroEar");
		// params.putString("picture", "http://twitpic.com/show/thumb/6hqd44");

		mAsyncFbRunner.request("me/feed", params, "POST",
				new WallPostListener());
	}

	private final class WallPostListener extends BaseRequestListener {
		public void onComplete(final String response) {
			mRunOnUi.post(new Runnable() {
				@Override
				public void run() {
					mProgress.cancel();

					Toast.makeText(MagicWord.this, "Posted to Facebook",
							Toast.LENGTH_SHORT).show();

				}
			});
		}
	}

	private final class FbLoginDialogListener implements DialogListener {
		public void onComplete(Bundle values) {
			SessionStore.save(mFacebook, MagicWord.this);

			/*
			 * mFacebookBtn.setText("  Facebook (No Name)");
			 * mFacebookBtn.setChecked(true);
			 * mFacebookBtn.setTextColor(Color.WHITE);
			 */
			postToFacebook(review);
			getFbName();
		}

		public void onFacebookError(FacebookError error) {
			Toast.makeText(MagicWord.this, "Facebook connection failed",
					Toast.LENGTH_SHORT).show();

			/* mFacebookBtn.setChecked(false); */
		}

		public void onError(DialogError error) {
			Toast.makeText(MagicWord.this, "Facebook connection failed",
					Toast.LENGTH_SHORT).show();

			/* mFacebookBtn.setChecked(false); */
		}

		public void onCancel() {/*
								 * mFacebookBtn.setChecked(false);
								 */
		}
	}

	private void getFbName() {
		mProgress.setMessage("Finalizing ...");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				String name = "";
				int what = 1;

				try {
					String me = mFacebook.request("me");

					JSONObject jsonObj = (JSONObject) new JSONTokener(me)
							.nextValue();
					name = jsonObj.getString("name");
					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mFbHandler.sendMessage(mFbHandler.obtainMessage(what, name));
			}
		}.start();
	}

	private void fbLogout() {
		// mProgress.setMessage("Disconnecting from Facebook");
		// mProgress.show();

		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(MagicWord.this);

				int what = 1;

				try {
					mFacebook.logout(MagicWord.this);

					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}

	private Handler mFbHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 0) {
				String username = (String) msg.obj;
				username = (username.equals("")) ? "No Name" : username;

				SessionStore.saveName(username, MagicWord.this);

				/* mFacebookBtn.setText("  Facebook (" + username + ")"); */

				Toast.makeText(MagicWord.this,
						"Connected to Facebook as " + username,
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MagicWord.this, "Connected to Facebook",
						Toast.LENGTH_SHORT).show();

			}
		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

			if (msg.what == 1) {
				Toast.makeText(MagicWord.this, "Facebook logout failed",
						Toast.LENGTH_SHORT).show();
			} else {
				/*
				 * mFacebookBtn.setChecked(false);
				 * mFacebookBtn.setText("  Facebook (Not connected)");
				 * mFacebookBtn.setTextColor(Color.GRAY);
				 */

				/*
				 * Toast.makeText(TestConnect.this,
				 * "Disconnected from Facebook", Toast.LENGTH_SHORT).show();
				 */
			}
		}
	};

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			speak.setEnabled(true);
		} else {
			// failed to init
			finish();
		}

	}

	private void sendRecognizeIntent() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say the magic word");
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100);
		startActivityForResult(intent, SPEECH_REQUEST_CODE);
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
						tts.speak("You said the magic word!",
								TextToSpeech.QUEUE_FLUSH, null);
					} else {
						tts.speak("The magic word is  " + mostLikelyThingHeard
								+ " try again", TextToSpeech.QUEUE_FLUSH, null);
					}
				}
				// result.setText("heard: " + matches);
				result.setText("heard: " + mostLikelyThingHeard);
			} else {
				Log.d(TAG, "result NOT ok");
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public void displayquote() {
		switch (cnt) {
		case 1:
			quotes.setText(" \"Great people are those who make others feel that they, too, can become great.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 2:
			quotes.setText(" \"Kindness is the language which the deaf can hear and the blind can see.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 3:
			quotes.setText(" \"I'm not a deaf musician. I'm a musician who happens to be deaf..\" ");

			cnt++;

			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 4:
			quotes.setText(" \"Who is blind, dumb and deaf will live a peaceful life of a hundred years.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 5:
			quotes.setText(" \"Disability is not a brave struggle or courage in the face of adversity. Disability is an art. It’s an ingenious way to live.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 6:
			quotes.setText(" \"We must embrace pain and burn it as fuel for our journey.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 7:
			quotes.setText(" \"Kindness is the language which the deaf can hear and the blind can seeDisability is a matter of  perception . if you can do just one thing well , you are needed by someone.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 8:
			quotes.setText(" \"In the middle of difficulty lies opportunity.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 9:
			quotes.setText(" \"Difficulties are meant to rouse, not discourage. The human spirit is to grow strong by conflict.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 10:
			quotes.setText(" \"Challenges are what make life interesting; overcoming them is what makes life meaningful.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 11:
			quotes.setText(" \"All misfortune is but a stepping stone to fortune.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 12:
			quotes.setText(" \"The gem cannot be polished without friction, nor man be perfected without trials.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 13:
			quotes.setText(" \"It is the surmounting of difficulties that make heroes.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;

		case 14:
			quotes.setText(" \"Obstacles can't stop you. Problems can't stop you. Most of all other people can't stop you. Only you can stop you.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;
		case 15:
			quotes.setText(" \"Troubles are often the tools by which God fashions us for better things.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;
		case 16:
			quotes.setText(" \"Strength does not come from physical capacity. It comes from an indomitable will.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;
		case 17:
			quotes.setText(" \"Determination gives you the resolve to keep going in spite of the roadblocks that lay before you.\" ");

			cnt++;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;
		case 18:
			quotes.setText(" \"The difference between the impossible and the possible lies in a person's determination but not in his disabilities.\" ");

			cnt = 1;
			getSharedPreferences("QUOTECOUNT", MODE_PRIVATE).edit()
					.putInt("cnt", cnt).commit();
			// Toast.makeText(this, " " + cnt, Toast.LENGTH_SHORT).show();
			break;
		}
	}

	@Override
	public void onBackPressed() {

		new AlertDialog.Builder(this)
				.setTitle("Exit")
				.setMessage("Are you sure you want to exit this application?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent();
								i.setAction(Intent.ACTION_MAIN);
								i.addCategory(Intent.CATEGORY_HOME);
								MagicWord.this.startActivity(i);
								finish();
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// do nothing
					}
				}).show();

	}

	/*
	 * @Override public void onBackPressed() { if (doubleBackToExitPressedOnce)
	 * { super.onBackPressed(); return; } this.doubleBackToExitPressedOnce =
	 * true; Toast.makeText(this, "Please click BACK again to exit",
	 * Toast.LENGTH_SHORT).show(); new Handler().postDelayed(new Runnable() {
	 * 
	 * @Override public void run() { doubleBackToExitPressedOnce = false;
	 * 
	 * } }, 2000); }
	 */

	@Override
	protected void onDestroy() {
		if (tts != null) {
			tts.shutdown();
		}
		super.onDestroy();
	}

	@Override
	public void onWhistleDetected() {
		runOnUiThread(new Runnable() {
			public void run() {
/*				vibe.vibrate(25);
				Toast.makeText(context, "detected", Toast.LENGTH_SHORT).show();*/
				prefs = PreferenceManager.getDefaultSharedPreferences(context);
				boolean val = prefs.getBoolean("whistle", true);
				/*Toast.makeText(context, " "+val, Toast.LENGTH_SHORT)
				.show();*/
				if (val == true) {
			/*		Toast.makeText(context, "done!", Toast.LENGTH_SHORT)
							.show();*/
					vibe.vibrate(100);
				}
				/*
				 * else{ recorderThread.stopRecording();
				 * detectorThread.stopDetection(); }
				 */

			}
		});
		// TODO Auto-generated method stub

	}
}