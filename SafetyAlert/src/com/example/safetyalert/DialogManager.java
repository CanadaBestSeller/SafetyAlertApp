package com.example.safetyalert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

public class DialogManager {

	private Context context;
	private AlertDialog.Builder builder;

	public DialogManager(Context context) {
		this.context = context;
	}

	// public void spawnRequest1(int guardianModeDuration) {
	//
	// builder = new AlertDialog.Builder(context);
	// builder.setTitle("Level 2 DANGER!");
	// builder.setIcon(R.drawable.ic_launcher);
	// builder.setMessage("Your friend is in level 2 danger!");
	//
	// builder.setPositiveButton("Respond", new
	// DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// //Do something
	// dialog.dismiss();
	// }
	// });
	// builder.setNegativeButton("Later", new DialogInterface.OnClickListener()
	// {
	// public void onClick(DialogInterface dialog, int whichButton) {
	// dialog.dismiss();
	// }
	// });
	//
	// AlertDialog alert = builder.create();
	// alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
	// alert.show();
	// }

	public void spawnRequest(int guardianModeDuration) {

		builder = new AlertDialog.Builder(context);
		builder.setTitle("Guardian Request!");
		builder.setIcon(R.drawable.ic_launcher);
		builder.setMessage("Your friend would like you to be their guardian for "
				+ Integer.toString(guardianModeDuration) + " minutes.");

		builder.setPositiveButton("Accept",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Intent i = new Intent(context, GuardianModeActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(i);
					}
				});
		builder.setNegativeButton("Later",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				});

		AlertDialog alert = builder.create();
		alert.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		alert.show();
	}
}