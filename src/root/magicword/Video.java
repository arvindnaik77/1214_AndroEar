package root.magicword;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Video extends Activity {

	String path;
	ImageButton plus;
	TextView header;
	VideoView mVideoView;
	Vibrator vibe;
	MediaController mediaController;
	final String url="http://m.youtube.com/#/results?gs_l=youtube.12..35i39.5355.5355.0.6749.1.1.0.0.0.0.265.265.2-1.1.0...0.0...1ac.1.vwhvvjpkMWw&oq=hand+gesture+language+&search_query=hand+gesture+language+&desktop_uri=%2Fresults%3Fsearch_query%3Dhand%2Bgesture%2Blanguage%2B%26oq%3Dhand%2Bgesture%2Blanguage%2B%26gs_l%3Dyoutube.12..35i39.5355.5355.0.6749.1.1.0.0.0.0.265.265.2-1.1.0...0.0...1ac.1.vwhvvjpkMWw";
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.video);
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		header = (TextView) findViewById(R.id.textView1);
		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		header.setTypeface(tf1);
		header.setText("Learn Gestures");
		plus=(ImageButton) findViewById(R.id.imageButton1);
		plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vibe.vibrate(100);
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);


			}

		});

		mVideoView = (VideoView) findViewById(R.id.videoView1);
		path = "android.resource://root.magicword/" + R.raw._hand;
		mVideoView.setVideoURI(Uri.parse(path));
		// mVideoView.setMediaController(new MediaController(this));
		mediaController = new MediaController(this);
		mediaController.findFocus();
		mediaController.show(0);
		mediaController.setEnabled(true);
		mVideoView.setMediaController(new MediaController(this) {
		    @Override
		    public void hide()
		    {
		    	//mediaController.show(0);
		    }

		    }); 
		mVideoView.setMediaController(mediaController);
/*		mVideoView.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				mediaController.show(0);
			}
		});*/
		mVideoView.requestFocus();
		mVideoView.start();
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

