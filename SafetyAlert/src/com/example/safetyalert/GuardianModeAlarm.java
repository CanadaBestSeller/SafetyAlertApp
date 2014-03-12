package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.widget.Toast;

public class GuardianModeAlarm extends BroadcastReceiver {

	private Context context;

	private static int guardianModeDuration = 60;
	private static int requests_left = 3;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
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

	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	private void initiateGuardianRequest() {

		String state = isExternalStorageWritable() ? "CAN WRITE TO EXT" : "CANNOT WRITE TO EXT!";
		Toast.makeText(context, state, Toast.LENGTH_SHORT).show();
		
//		DialogManager dm = new DialogManager(context);
//		dm.spawnRequest(guardianModeDuration);

		// Check if there are any other guardian requests.
		this.setAlarm(context);
	}
}