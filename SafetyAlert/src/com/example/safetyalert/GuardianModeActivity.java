package com.example.safetyalert;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuardianModeActivity extends Activity {
	
	public static final int GUARDIAN_MODE_NOTIFICATION_ID = 1;

	private NotificationManager nManager;
	private GuardianRequest g;
	private AlertAlarm alertAlarm;
	private ProgressUpdateAlarm progressUpdateAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guardian_mode);
		
		Intent i = getIntent();
		Bundle data = i.getExtras();
		g = (GuardianRequest) data.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);

		Utils.appendToLog("[GUARDIAN REQUEST ACCEPTED] Duration: " + g.guardianshipDuration + "mins.");

		// Cancel pending guardian mode notification
		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.cancel(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID);
		
		alertAlarm = new AlertAlarm();
		alertAlarm.setAlert(this, g);
		
		// Render progress bar in notification on separate thread
//		Notification startProgressNotification = 
//				NotificationFactory.progressUpdateNotification(this, 0, g.guardianshipDuration * 60);
//		nManager.notify(SafetyAppService.SAFETY_APP_NOTIFICATION_ID, startProgressNotification);
//		
//		progressUpdateAlarm = new ProgressUpdateAlarm();
//		progressUpdateAlarm.startProgressUpdate(this, 0, g.guardianshipDuration * 60);
	}

	public void quit(View view) {
		this.finish();
	}

//	public static boolean isOn = false;
//		NotificationCompat.Builder ncb = new NotificationCompat.Builder(this)
//	.setSmallIcon(R.drawable.ic_launcher)
//	.setContentTitle("Guardian Mode")
//	.setContentText("Your friend might send you an alert!");
//
//UpdateNotificationRunnable r = new UpdateNotificationRunnable(this, ncb, nManager, SafetyAppService.SAFETY_APP_NOTIFICATION_ID, g.interval);
//new Thread(r).start();
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