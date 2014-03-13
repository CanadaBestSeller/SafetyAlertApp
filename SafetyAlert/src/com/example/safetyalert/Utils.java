package com.example.safetyalert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.format.DateFormat;

public class Utils {

	public static void toast(Activity activity, String message, int length) {
		UiToast u = new UiToast(activity, message, length);
		activity.runOnUiThread(u);
	}

	public static void toast(Activity activity, int message_id, int length) {
		String message = activity.getResources().getString(message_id);
		UiToast u = new UiToast(activity, message, length);
		activity.runOnUiThread(u);
	}

	public static String long2timestamp(long time) {
		String timestamp = DateFormat.format("EEEE, MMM dd, HH:mm:ss",
				new Date(time)).toString();
		return timestamp;
	}

	public static void appendToLog(String message) {

		if (isExternalStorageWritable()) {
			String root = Environment.getExternalStorageDirectory().toString();
			File myDir = new File(root + "/SafetyAlert");
			myDir.mkdirs();

			String logName = "activity_log.txt";

			try {
				FileWriter f = new FileWriter(myDir + "/" + logName, true);
				f.write(Utils.long2timestamp(System.currentTimeMillis())
						+ " --- ");
				f.write(message);
				f.write("\n\n");
				f.flush();
				f.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/* Checks if external storage is available for read and write */
	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	// Returns the next alert
	public static GuardianRequest nextAlert(Context context) {
		try {

			AssetManager am = context.getAssets();
			InputStream i = am.open("alert_schedule.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(i));

			// Example line
			// "Mar 03 2014 22:00, 30, 15, Dangerous area, Suddenly running, Night time"
			String line;
			long nextAlertTime;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				if ((nextAlertTime = isFutureDate(parts[0])) != 0) {
					String[] reasons = Arrays.copyOfRange(parts, 3,
							parts.length);
					return new GuardianRequest(nextAlertTime,
							Integer.parseInt(parts[1].trim()),
							Integer.parseInt(parts[2].trim()),
							reasons);
				}
			}

		} catch (IOException e) {
			System.out.println("CAN'T OPEN ALERT SCHEDULE TEXT FILE!");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Check alert_schedule.txt SYNTAX!");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Check alert_schedule.txt SYNTAX!");
			e.printStackTrace();
		}

		return null;
	}

	private static long isFutureDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("MMM dd yyyy HH:mm");
		try {
			Date nextAlertDate = format.parse(dateString);
			long nextAlertDateLong = nextAlertDate.getTime();
			if (System.currentTimeMillis() < nextAlertDateLong)
				return nextAlertDateLong;
		} catch (java.text.ParseException e) {
			System.out.println("CAN'T PARSE. Check alert_schedule.txt SYNTAX!");
			e.printStackTrace();
		}
		return 0;
	}
}