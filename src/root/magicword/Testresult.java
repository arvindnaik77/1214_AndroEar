package root.magicword;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Testresult extends Activity {
	
	TextView instructions;
	TextView header;
	Button cont;
	int hearcount;
	int i;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_your_ear);
		cont=(Button) findViewById(R.id.button1);
		Context context=this;
		
		Bundle extras = getIntent().getExtras();
		hearcount = extras.getInt("hearcount");
	/*	Toast.makeText(context,""+ hearcount,Toast.LENGTH_SHORT).show();*/
		
		instructions = (TextView) findViewById(R.id.textView2);
		header = (TextView) findViewById(R.id.textView1);
		cont = (Button) findViewById(R.id.button1);
		
		Typeface tf1 = Typeface.createFromAsset(getAssets(),
				"fonts/RobotoRegular.ttf");
		header.setTypeface(tf1);
		instructions.setTypeface(tf1);
		
		header.setText("RESULTS");
		
		if(hearcount==0)
			instructions.setText("We understand that you weren't able to hear any of the sounds. Thats why this app is for you!!! :) ");
		else if(hearcount==20)
			instructions.setText("Congratulations! You heard " + hearcount + "/20 sounds");
		else if(hearcount<15)
			instructions.setText("You heard " + hearcount + "/20 sounds. We think you should consult a doctor at the earliest.");
		else
			instructions.setText("We see that your hearing capacity is moderately good. Still we recommend you should consult a doctor at the earliest. ");
				
		cont.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(Testresult.this, MagicWord.class);
				Testresult.this.startActivity(myIntent);
			}
		});
		

		

	}
}