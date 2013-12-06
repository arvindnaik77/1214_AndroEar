package root.magicword;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.Toast;

public class Broadcastreceiver extends Service implements LocationListener {
	ShakeListener mShaker;
	Vibrator vibe;
	SmsManager sms;
	Settings set;
	double amp;
	String ph;
	String loc = "";
	// private String provider;
	// private LocationManager locationManager;
	Context context;
	// Location location;

	SharedPreferences prefs;
	static final private double EMA_FILTER = 0.6;

	private MediaRecorder mRecorder = null;
	private double mEMA = 0.0;

	private static final String TAG = "MyService";
	MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		context = this;
		// Toast.makeText(this, "My Service Created", Toast.LENGTH_LONG).show();
		vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

		mShaker = new ShakeListener(this);

		mShaker.setOnShakeListener(new ShakeListener.OnShakeListener() {
			public void onShake() {

				// getloc();
				String provider;
				LocationManager locationManager;
				Location location;
				locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				Criteria criteria = new Criteria();
				provider = locationManager.getBestProvider(criteria, false);
				location = locationManager.getLastKnownLocation(provider);

				// locationManager.removeUpdates(context);

				/*
				 * locationManager.requestLocationUpdates(provider, 400, 1,
				 * context);
				 */
				try {
					Geocoder geo = new Geocoder(context, Locale.getDefault());

					List<Address> addresses = geo.getFromLocation(
							location.getLatitude(), location.getLongitude(), 1);
					if (addresses.isEmpty()) {
						// placeName.setText("Waiting for Location");
					} else {
						if (addresses.size() > 0) {
							loc = "I'm in trouble.\n\nI'm at:\n"
									+ addresses.get(0).getFeatureName() + ", "
									+ addresses.get(0).getSubLocality() + ",  "
									+ addresses.get(0).getSubAdminArea() + ","
									+ addresses.get(0).getAdminArea() + ", "
									+ addresses.get(0).getCountryName();
						}
					}
				} catch (Exception e) {
					// Toast.makeText(context "No Location Name Found", loc,
					// Toast.LENGTH_LONG).show();
				}

				// Toast.makeText(context, loc, Toast.LENGTH_LONG).show();

				prefs = PreferenceManager.getDefaultSharedPreferences(context);
				ph = prefs.getString("phone", "");

				/*
				 * String phn = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
				 * .getString("phone", "");
				 */

				sms = SmsManager.getDefault();

				prefs = PreferenceManager.getDefaultSharedPreferences(context);
				boolean val = prefs.getBoolean("sos", true);
				if (val == true) {

					if (!(ph.equals(""))) {
						if (loc.equals(""))
							sms.sendTextMessage(ph, null,
									"I'm in trouble.\n Sent from AndroEar",
									null, null);
						else
							sms.sendTextMessage(ph, null, loc
									+ "\n\nSent from AndroEar", null, null);

					}
					vibe.vibrate(200);

				}
				/* sms.sendTextMessage(ph, null, loc, null, null); */
				// Toast.makeText(context, ph, Toast.LENGTH_LONG).show();
			}
		});

	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		try {
			Geocoder geo = new Geocoder(this.getApplicationContext(),
					Locale.getDefault());
			List<Address> addresses = geo.getFromLocation(
					location.getLatitude(), location.getLongitude(), 1);
			if (addresses.isEmpty()) {
			} else {

				if (addresses.size() > 0) {

					loc = "I'm in trouble.\n\nI'm at:\n"
							+ addresses.get(0).getFeatureName() + ", "
							+ addresses.get(0).getSubLocality() + ",  "
							+ addresses.get(0).getSubAdminArea() + ","
							+ addresses.get(0).getAdminArea() + ", "
							+ addresses.get(0).getCountryName();

				}
			}
		} catch (Exception e) {
			Toast.makeText(this, "No Location Name Found", 600).show();
		}
	}

	@Override
	public void onDestroy() {
		// Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onStart(Intent intent, int startid) {
		// Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}