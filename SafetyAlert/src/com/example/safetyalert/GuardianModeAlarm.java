package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GuardianModeAlarm extends BroadcastReceiver {

	private Context context;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		initiateGuardianRequest();
	}

	public void setAlarm(Context context, long time) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);
		am.set(AlarmManager.RTC_WAKEUP, time, operation);
	}
	
	public void cancelAlarm(Context context) {
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, GuardianModeAlarm.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
		am.cancel(pi);
	}

	private void initiateGuardianRequest() {
		Toast.makeText(context, "HELLO!", Toast.LENGTH_SHORT).show();
		
		this.setAlarm(context, System.currentTimeMillis() + 5000);
		Toast.makeText(context, "HELLO AGAIN!", Toast.LENGTH_SHORT).show();
	}
}