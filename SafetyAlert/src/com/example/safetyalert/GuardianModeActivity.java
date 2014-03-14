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
}