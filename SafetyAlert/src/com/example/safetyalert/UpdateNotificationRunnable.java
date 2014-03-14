package com.example.safetyalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class UpdateNotificationRunnable implements Runnable {

	// we're going to update progress 1/20th at a time.
	public static final int GRANULARITY = 20;
	public int notificationId;

	public int minutes;
	public int seconds;
	public int timeslice;

	public NotificationCompat.Builder ncb;
	public NotificationManager nm;
	public GuardianModeActivity m;

	public UpdateNotificationRunnable(GuardianModeActivity m, NotificationCompat.Builder ncb, NotificationManager nm, int notificationid, int minutes) {
		this.ncb = ncb;
		this.nm = nm;
		this.m = m;
		this.notificationId = notificationid;

		this.minutes = minutes;
		// this.seconds = minutes*60;
		this.seconds = 20;
		this.timeslice = seconds / UpdateNotificationRunnable.GRANULARITY;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		for (int i = 0; i <= seconds; i += timeslice) {

			ncb.setProgress(seconds, i, false);
			Notification notification = ncb.build();
			notification.flags |= Notification.FLAG_ONGOING_EVENT;
			nm.notify(notificationId, notification);

			try {
				Thread.sleep(timeslice * 1000);
			} catch (InterruptedException e) {
				//SafetyApp.log("sleep failure on progress notification");
			}
		}

		Toast.makeText(m, m.getResources().getString(R.string.guardian_mode_off), Toast.LENGTH_LONG).show();
	}
}
