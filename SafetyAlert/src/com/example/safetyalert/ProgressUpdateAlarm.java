package com.example.safetyalert;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ProgressUpdateAlarm extends BroadcastReceiver {

	// we're going to update progress 1/20th at a time.
	private static final int GRANULARITY = 20;
	private static final String PROGRESS = "PROGRESS";
	private static final String OUT_OF = "OUT_OF";
	
	private Context context;
	private NotificationManager nManager;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		this.nManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		int progress = intent.getIntExtra(PROGRESS, 100);
		int outOf = intent.getIntExtra(OUT_OF, 100);

		Notification notification = NotificationFactory.progressUpdateNotification(context, progress, outOf);
		nManager.notify(SafetyAppService.SAFETY_APP_NOTIFICATION_ID, notification);
		
		if (progress < outOf) startProgressUpdate(context, progress, outOf);
	}

	public void startProgressUpdate(Context context, int progress, int outOf) {
		// eg) 90/180
		int timeslice = outOf * 60 / GRANULARITY; // eg) outOf=>2min, timeslice=>6sec
		System.out.println(Integer.toString(timeslice));
		AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		
		Intent i = new Intent(context, ProgressUpdateAlarm.class);
		i.putExtra(PROGRESS, progress + timeslice);
		i.putExtra(OUT_OF, outOf);
		PendingIntent operation = PendingIntent.getBroadcast(context, 0, i, 0);
		
		am.set(AlarmManager.RTC, System.currentTimeMillis() + (timeslice*1000), operation);
	}

//	@Override
//	public void onCreate() {
//		super.onCreate();
//		Toast.makeText(this, "TEST!!!!", 0).show();
//	}
//
//	@Override
//	public int onStartCommand(Intent intent, int flags, int starttId) {
//		Bundle data = intent.getExtras();
//		g = (GuardianRequest) data.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);
//		
//		seconds = g.guardianshipDuration * 60;
//		timeslice = seconds/GRANULARITY;
//
//		ncb = new NotificationCompat.Builder(this)
//				.setSmallIcon(R.drawable.ic_launcher)
//				.setContentTitle("Guardian Mode is ON.")
//				.setContentText("Running in the background.");
//
//		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//		for (int i = 0; i <= seconds; i += timeslice) {
//
//			ncb.setProgress(seconds, i, false);
//			Notification notification = ncb.build();
//			notification.flags |= Notification.FLAG_ONGOING_EVENT;
//			nManager.notify(SafetyAppService.SAFETY_APP_NOTIFICATION_ID, notification);
//
//			try {
//				Thread.sleep(timeslice * 1000);
//			} catch (InterruptedException e) {
//				// SafetyApp.log("sleep failure on progress notification");
//			}
//
//		}
//		
//		stopSelf();
//
//		return START_NOT_STICKY;
//	}
}
