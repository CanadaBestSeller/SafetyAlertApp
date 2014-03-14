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

			Utils.appendToLog("[PREPARED GUARDIAN REQUEST] Will trigger on "
					+ Utils.long2timestamp(g.triggerTime));
			am.set(AlarmManager.RTC_WAKEUP, g.triggerTime, operation);
		}
	}
	
	public void setTestAlarm(Context context) {
		String[] reasons = {"Just testing.", "No one is in danger."};
		GuardianRequest g = new GuardianRequest(System.currentTimeMillis() + 5000, 2, 0, reasons);

		// set guardian mode details here
		AlarmManager am = (AlarmManager) context .getSystemService(Context.ALARM_SERVICE);

		Intent i = new Intent(context, GuardianModeAlarm.class);
		i.putExtra(EXTRA_GUARDIAN_REQUEST, g);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);

		Utils.appendToLog("[PREPARED TEST GUARDIAN REQUEST] Will trigger on "
				+ Utils.long2timestamp(g.triggerTime));
		am.set(AlarmManager.RTC_WAKEUP, g.triggerTime, operation);
	}

	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}

	private void initiateGuardianRequest(GuardianRequest g) {

		Utils.lineBreakLog();
		Utils.appendToLog("[GUARDIAN REQUEST] Duration: "
				+ g.guardianshipDuration + "mins.");

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