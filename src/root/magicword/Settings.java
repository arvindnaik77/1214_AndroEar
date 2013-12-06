package root.magicword;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.facebook.android.Facebook;
import com.facebook.android.SessionStore;

public class Settings extends PreferenceActivity {

	String ph;
	static String result;
	String result1;
	Button apply;
	Context context;
	SharedPreferences mPreferences;
	SharedPreferences getPrefs;
	private Facebook mFacebook;
	private ProgressDialog mProgress;
	private static final String APP_ID = "638727409478071";
	private static final String[] PERMISSIONS = new String[] {
		"publish_stream", "read_stream", "offline_access" };
	
	String pullnumber() {
	getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
/*		
		String ph = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				.getString("phone", "");*/

		ph = getPrefs.getString("phone", "");
		return ph;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		setContentView(R.layout.settings);
		


		mProgress = new ProgressDialog(this);
		mFacebook = new Facebook(APP_ID);
		
		
		apply = (Button) findViewById(R.id.button1);
		mFacebook = new Facebook(APP_ID);
		apply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			

				fbLogout();
			}

		});
		ph = pullnumber();

	}
	
	
	private void fbLogout() {
		mProgress.setMessage("Disconnecting from Facebook");
		mProgress.show();

		new Thread() {
			@Override
			public void run() {
				SessionStore.clear(Settings.this);

				int what = 1;

				try {
					mFacebook.logout(Settings.this);

					what = 0;
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				mHandler.sendMessage(mHandler.obtainMessage(what));
			}
		}.start();
	}
	
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mProgress.dismiss();

		}
	};
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	/*	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		Toast.makeText(Forums.this, "destroyed!",
				Toast.LENGTH_SHORT).show();*/
	}
	
	
	
}
