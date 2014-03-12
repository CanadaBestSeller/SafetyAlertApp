package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GuardianModeAlarm extends BroadcastReceiver {

	private Context context;
	private NotificationManager nManager;

	private static int guardianModeDuration = 60;
	private static int requests_left = 3;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		initiateGuardianRequest();
	}

	public void setAlarm(Context context) {
		if (requests_left > 0) {
			requests_left--;
			
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Intent i = new Intent(context, GuardianModeAlarm.class);
			PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);

			am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, operation);

			// set guardian mode details here
			guardianModeDuration = 3;
		}
	}

	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}

	private void initiateGuardianRequest() {

		Toast.makeText(context, Integer.toString(requests_left), Toast.LENGTH_SHORT).show();
		
		// Spawn a notification, which will be canceled later
		Notification safetyAppOnNotification = NotificationFactory.
				pendingGuardianRequestNotification(context, guardianModeDuration);
		nManager.notify(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID, safetyAppOnNotification);

		DialogManager dm = new DialogManager(context);
		dm.spawnRequest(guardianModeDuration);

		// Check if there are any other guardian requests.
		this.setAlarm(context);
	}
}