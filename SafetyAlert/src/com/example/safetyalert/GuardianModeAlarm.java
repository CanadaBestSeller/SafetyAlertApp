package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GuardianModeAlarm extends BroadcastReceiver {

	private Context context;

	private static int guardianModeDuration;
	private static int requests_left = 3;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		initiateGuardianRequest();
	}

	public void setAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);

		am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, operation);
		// set guardian mode details here
		guardianModeDuration = 3;
		requests_left -= 1;
	}
	
	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}

	private void initiateGuardianRequest() {

		Toast.makeText(context, "Request " + Integer.toString(requests_left), Toast.LENGTH_SHORT).show();

		// If there is another guardianship request
		if(requests_left > 0) {
			this.setAlarm(context);
		}
	}
}