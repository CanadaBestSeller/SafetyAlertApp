package com.example.safetyalert;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;
import android.widget.Toast;

public class GuardianModeActivity extends Activity {
	
	public static final int GUARDIAN_MODE_NOTIFICATION_ID = 1;

	private NotificationManager nManager;
	private int guardianModeDuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guardian_mode);
		
		// TODO Set guardianModeDuration using putextra from intent/pendingIntent
		//this.guardianModeDuration = intent
		
		Toast.makeText(this, getResources().getString(R.string.guardian_mode_on), Toast.LENGTH_LONG).show();
		Utils.appendToLog("Guardianship request of duration " + guardianModeDuration + "mins ACCEPTED.");

		// Cancel pending guardian mode notification
		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.cancel(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID);
	}

//	public static boolean isOn = false;
//
//	public void guardianModeOn(int minutes) {
//		if (this.activation && !this.guardianMode) {
//			// User goes back to the screen when they click the notification
//			Intent toMainActivity = new Intent(this, MainActivity.class);
//			PendingIntent p = PendingIntent.getActivity(this, 0,
//					toMainActivity, 0);
//
//			// TODO Get a different icon for guardian mode
//			NotificationCompat.Builder ncb = new NotificationCompat.Builder(
//					this).setSmallIcon(R.drawable.ic_launcher)
//					.setContentTitle("Guardian Mode")
//					.setContentText("Your friend might send you an alert!")
//					.setContentIntent(p);
//
//			UpdateNotificationRunnable r = new UpdateNotificationRunnable(this,
//					ncb, nm, minutes);
//			toast("Guardian Mode is ON. For the next " + minutes
//					+ " minutes, your friend might send you distress signals!",
//					Toast.LENGTH_LONG);
//			this.guardianMode = true;
//			new Thread(r).start();
//		}
//	}
//
//	public void guardianModeOff() {
//		if (this.activation) {
//			nm.notify(MainActivity.APP_NOTIFICATION_ID, safetyAppOnNotification());
//			toast("Guardian Mode OFF. Thanks for helping out your friend!",
//					Toast.LENGTH_SHORT);
//			this.guardianMode = false;
//		}
//	}
//
//	public void startGuardianFor1Minute(View view) {
//		guardianModeOn(1);
//	}

}