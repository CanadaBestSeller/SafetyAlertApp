package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlertAlarm extends BroadcastReceiver {

	public static final int ALERT_NOTIFICATION_ID = 2;

	private Context context;
	private NotificationManager nManager;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		this.nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		Bundle data = intent.getExtras();
		GuardianRequest g = (GuardianRequest) data.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);
		initiateAlert(g);
	}

	private void initiateAlert(GuardianRequest g) {
		Utils.appendToLog("[ALERT TRIGGERED]");
		
		Notification pendingAlertNotification = 
				NotificationFactory.pendingAlertNotification(context, g);
		nManager.notify(ALERT_NOTIFICATION_ID, pendingAlertNotification);
		
		DialogManager dm = new DialogManager(context);
		dm.spawnAlert(g);
	}

	public void setAlert(Context context, GuardianRequest g) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		Intent i = new Intent(context, AlertAlarm.class);
		i.putExtra(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST, g);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);

		// internal=>0 implies test value
		long alertTriggerTime = (g.interval == 0) ?
				System.currentTimeMillis() + 10000 : System.currentTimeMillis() + (g.interval*60*1000);
		Utils.appendToLog("[PREPARED ALERT] Will trigger on " + Utils.long2timestamp(alertTriggerTime));
		am.set(AlarmManager.RTC_WAKEUP, alertTriggerTime, operation);
	}
	
	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, AlertAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}
}