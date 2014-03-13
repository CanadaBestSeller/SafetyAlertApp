package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GuardianModeAlarm extends BroadcastReceiver {
	
	public final static String EXTRA_GUARDIAN_REQUEST = 
			"com.example.safetyalert.GUARDIAN_REQUEST";

	private Context context;
	private NotificationManager nManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Bundle data = intent.getExtras();
		GuardianRequest g = (GuardianRequest) data.getParcelable(EXTRA_GUARDIAN_REQUEST);
		initiateGuardianRequest(g);
	}

	public void setAlarm(Context context) {
		GuardianRequest g;
		if ((g = Utils.nextAlert(context)) != null) {

			// set guardian mode details here
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

			Intent i = new Intent(context, GuardianModeAlarm.class);
			i.putExtra(EXTRA_GUARDIAN_REQUEST, g);
			PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);

			Utils.appendToLog("Set guardianship request to trigger @ "
					+ Utils.long2timestamp(System.currentTimeMillis()));
			am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, operation);
		}
	}

	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}

	private void initiateGuardianRequest(GuardianRequest g) {

		//Toast.makeText(context, Integer.toString(requests_left), Toast.LENGTH_SHORT).show();
		Utils.appendToLog("Guardianship request of duration "
				+ g.guardianshipDuration + "mins triggered.");

		// Spawn a notification, which will be canceled later
		// If you use intent extras, remeber to call PendingIntent.getActivity()
		// with the flag PendingIntent.FLAG_UPDATE_CURRENT, otherwise the same
		// extras will be reused for every notification.
		Notification safetyAppOnNotification = NotificationFactory
				.pendingGuardianRequestNotification(context, g);
		nManager.notify(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID,
				safetyAppOnNotification);

		DialogManager dm = new DialogManager(context);
		dm.spawnRequest(g);

		// Check if there are any other guardian requests.
		this.setAlarm(context);
	}
}