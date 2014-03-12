package com.example.safetyalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class SafetyAppService extends Service {

	public static final int SAFETY_APP_SERVICE_ID = 1;

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
		startSafetyApp();
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		toast(R.string.alert_off, Toast.LENGTH_SHORT);
		notificationManager.cancel(SAFETY_APP_SERVICE_ID);
		guardianModeAlarm.CancelAlarm(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	private void toast(int id, int duration) {
		Toast.makeText(this, this.getResources().getString(id), duration).show();
	}

	private void startSafetyApp() {
		Notification safetyAppOnNotification = NotificationFactory.safetyAppOnNotification(this);
		notificationManager.notify(SAFETY_APP_SERVICE_ID, safetyAppOnNotification);

		toast(R.string.alert_on, Toast.LENGTH_SHORT);

		guardianModeAlarm.SetAlarm(SafetyAppService.this);

//		DialogManager dm = new DialogManager(this)
//		dm.spawnRequest();
	}
}