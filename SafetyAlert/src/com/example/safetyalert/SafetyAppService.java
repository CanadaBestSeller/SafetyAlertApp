package com.example.safetyalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SafetyAppService extends Service {

	public static final int SAFETY_APP_NOTIFICATION_ID = 0;

	private NotificationManager notificationManager;
	private GuardianModeAlarm guardianModeAlarm;

	@Override
	public void onCreate() {
		super.onCreate();
		this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		this.guardianModeAlarm = new GuardianModeAlarm();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int starttId) {
		
		if (!Utils.isExternalStorageWritable()) {
			Toast.makeText(this, "THIS APP WON'T WORK WITHOUT EXTERNAL STORAGE!", Toast.LENGTH_LONG).show();
		}

		Utils.appendToLog("Started SafetyApp");

		Notification safetyAppOnNotification = NotificationFactory.safetyAppOnNotification(this);
		notificationManager.notify(SAFETY_APP_NOTIFICATION_ID, safetyAppOnNotification);
		toast(R.string.safety_app_on, Toast.LENGTH_SHORT);

		guardianModeAlarm.setAlarm(SafetyAppService.this);

		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Utils.appendToLog("Destroyed SafetyApp");

		notificationManager.cancel(SAFETY_APP_NOTIFICATION_ID);
		toast(R.string.safety_app_off, Toast.LENGTH_SHORT);
		guardianModeAlarm.cancelAlarm(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private void toast(int id, int duration) {
		Toast.makeText(this, this.getResources().getString(id), duration).show();
	}
}