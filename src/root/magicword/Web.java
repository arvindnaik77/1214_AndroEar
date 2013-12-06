package root.magicword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Web extends Activity {

	RelativeLayout rl;
	WebView mWebView;
	String URL;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		Bundle extras = getIntent().getExtras();
		URL = extras.getString("web");

		getWindow().setFeatureInt(Window.FEATURE_PROGRESS,
				Window.PROGRESS_VISIBILITY_ON);

		mWebView = (WebView) findViewById(R.id.webview);
		final ProgressBar loadingProgressBar;
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.loadUrl(URL);

		mWebView.setWebViewClient(new MyWebViewClient());
		loadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				super.onProgressChanged(view, newProgress);
				loadingProgressBar.setProgress(newProgress);
				if (newProgress == 100) {
					loadingProgressBar.setVisibility(View.GONE);

				} else {
					loadingProgressBar.setVisibility(View.VISIBLE);

				}

			}

		});
	}

/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		} else {
			Intent myIntent = new Intent(Web.this, Forums.class);
			Web.this.startActivity(myIntent);
			startActivity(myIntent);
		}
		return super.onKeyDown(keyCode, event);

	}*/

	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			URL = url;
			view.loadUrl(url);
			return true;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		/*
		 * overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		 * Toast.makeText(Forums.this, "destroyed!", Toast.LENGTH_SHORT).show();
		 */
	}
}