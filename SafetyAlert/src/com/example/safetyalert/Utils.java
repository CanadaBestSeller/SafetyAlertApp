package com.example.safetyalert;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import android.app.Activity;
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
		 String timestamp = DateFormat.format("EEEE, MMM dd, HH:mm:ss", new Date(time)).toString();
		 return timestamp;
	}

	public static void appendToLog(String message) {

	    String root = Environment.getExternalStorageDirectory().toString();
	    File myDir = new File(root + "/SafetyAlert");    
	    myDir.mkdirs();

	    String logName = "activity_log.txt";

	    try {
	    	FileWriter f = new FileWriter(myDir+"/"+logName, true);
	    	f.write(Utils.long2timestamp(System.currentTimeMillis()) + " --- ");
	    	f.write(message);
	    	f.write("\n\n");
	    	f.flush();
	    	f.close();
	    } catch (Exception e) {
	           e.printStackTrace();
	    }
	}
}