package root.magicword;


import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.app.ProgressDialog;

import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.Facebook;
import com.facebook.android.SessionStore;

/**
 * This example shows how to post status to Facebook wall.
 * 
 * @author Lorensius W. L. T <lorenz@londatiga.net>
 * 
 * http://www.londatiga.net
 */
public class TestPost extends Activity
{
	private Facebook mFacebook;
	private CheckBox mFacebookCb;
	private ProgressDialog mProgress;
	
	private Handler mRunOnUi = new Handler();
	
	private static final String APP_ID = "638727409478071";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		//final EditText reviewEdit = (EditText) findViewById(R.id.revieew);
		final String review ="Hi, I'm uisng AndroEar for Android. Its an awesome app. I think you must give it a try!!!";
		mFacebookCb	= (CheckBox) findViewById(R.id.cb_facebook);
		
		mProgress	= new ProgressDialog(this);
		
		mFacebook 	= new Facebook(APP_ID);
		
		SessionStore.restore(mFacebook, this);
		postToFacebook(review);

/*		if (mFacebook.isSessionValid()) {
			mFacebookCb.setChecked(true);
				
			String name = SessionStore.getName(this);
			name		= (name.equals("")) ? "Unknown" : name;
				
			mFacebookCb.setText("  Facebook  (" + name + ")");
		}*/
		
		((Button) findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//String review = reviewEdit.getText().toString();
				
				//if (review.equals("")) return;
			
				if (mFacebookCb.isChecked()) postToFacebook(review);
			}
		});
	}
	
	private void postToFacebook(String review) {	
		mProgress.setMessage("Posting ...");
		mProgress.show();
		
		AsyncFacebookRunner mAsyncFbRunner = new AsyncFacebookRunner(mFacebook);
		
		Bundle params = new Bundle();
    		
		params.putString("message", review);
		params.putString("name", "AndroEar");
		params.putString("caption", "AndroEar : An Android app for the hearing impaired");
	//	params.putString("link", "http://www.londatiga.net");
		params.putString("description", "Jaseem Abbas likes AndroEar");
		//params.putString("picture", "http://twitpic.com/show/thumb/6hqd44");
		
		mAsyncFbRunner.request("me/feed", params, "POST", new WallPostListener());
	}

	private final class WallPostListener extends BaseRequestListener {
        public void onComplete(final String response) {
        	mRunOnUi.post(new Runnable() {
        		@Override
        		public void run() {
        			mProgress.cancel();
        			
        			Toast.makeText(TestPost.this, "Posted to Facebook", Toast.LENGTH_SHORT).show();
        		}
        	});
        }
    }
}