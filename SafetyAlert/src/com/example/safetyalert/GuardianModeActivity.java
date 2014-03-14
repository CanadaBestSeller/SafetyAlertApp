package com.example.safetyalert;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GuardianModeActivity extends Activity {

	public static final int GUARDIAN_MODE_NOTIFICATION_ID = 1;

	private NotificationManager nManager;
	private GuardianRequest g;
	private boolean sorry, past;
	private AlertAlarm alertAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = getIntent();
		Bundle data = i.getExtras();
		g = (GuardianRequest) data
				.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);
		sorry = i.getBooleanExtra(TimeoutQuestionnaire.SORRY, false);
		
		// normal case
		if (g.interval != 0) {
			past = (System.currentTimeMillis() > (g.triggerTime + 15*60*1000))? true : false;
		} else {
			// test case
			past = (System.currentTimeMillis() > (g.triggerTime + 10000))? true : false;
		}

		// If late acceptance, should only happen once
		if (past && !sorry) {
			Intent questionnaire = new Intent(this, TimeoutQuestionnaire.class);
			questionnaire.putExtra(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST, g);
			startActivity(questionnaire);
			finish();
		} else {

			setContentView(R.layout.activity_guardian_mode);

			Utils.appendToLog("[GUARDIAN REQUEST ACCEPTED] Duration: "
					+ g.guardianshipDuration + "mins.");

			// Cancel pending guardian mode notification
			nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			nManager.cancel(GuardianModeActivity.GUARDIAN_MODE_NOTIFICATION_ID);

			alertAlarm = new AlertAlarm();
			alertAlarm.setAlert(this, g);
		}
	}

	public void quit(View view) {
		this.finish();
	}
}