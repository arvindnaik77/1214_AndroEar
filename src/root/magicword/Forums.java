package root.magicword;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Forums extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setListAdapter(new ArrayAdapter<String>(this, R.layout.forums, names));
		ListView lv = getListView();
		lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setTextFilterEnabled(true);
		getListView().setBackgroundResource(R.drawable.bg);

		// getListView().setBackgroundColor("#5b5a5f");
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void call(String str) {
				Intent myIntent = new Intent(Forums.this, Web.class);
				myIntent.putExtra("web", str);
				startActivity(myIntent);
			}

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				if (arg2 == 0)
					call("http://www.alldeaf.com/");
				else if (arg2 == 1)
					call("http://www.actiononhearingloss.org.uk");
				else if (arg2 == 2)
					call("http://www.hearinglossweb.com/");
				else if (arg2 == 3)
					call("http://hearingaidforums.com/forumdisplay.php?f=6");
				else if (arg2 == 4)
					call("http://www.myhearingloss.org");
				else if (arg2 == 5)
					call("http://www.start-american-sign-language.com");
				else if (arg2 == 6)
					call("http://www.efsli.org/");
				else if (arg2 == 7)
					call("http://www.rocketlanguages.com/your-community/american-sign-language");

			}

		});

	}

/*	@Override
	public void onBackPressed() {

		Intent myIntent = new Intent(Forums.this, MagicWord.class);
		Forums.this.startActivity(myIntent);
		overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
	//	finish();

	}
*/
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	/*	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
		Toast.makeText(Forums.this, "destroyed!",
				Toast.LENGTH_SHORT).show();*/
	}

	static final String[] names = new String[] { "All Deaf",
			"Action on hearing loss", "Hearing loss", "Hearing aid forums",
			"My hearing loss", "Start american sign language", "Efsli",
			"Rocket Languages" };

}
