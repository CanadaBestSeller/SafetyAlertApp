package com.example.safetyalert;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AlertResponseActivity extends Activity {

	private NotificationManager nManager;
	//private final Intent questionnaire = new Intent(this, Questionnaire.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle data = getIntent().getExtras();
		GuardianRequest g = (GuardianRequest) data
				.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);

		setContentView(R.layout.activity_alert_response);

		TextView t = (TextView)findViewById(R.id.alert_response_body);
		t.append("Sent:\n" + Utils.long2timestamp(g.triggerTime) + "\n\n" + g.getReasonsAsString());

		ImageView map = (ImageView) findViewById(R.id.map);
		map.setImageBitmap(Utils.int2png(this, g.mapNumber));

		nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nManager.cancel(AlertAlarm.ALERT_NOTIFICATION_ID);

		Utils.appendToLog("[ALERT RESPONDED]");
	}

	public void toQuestionnaire(View view) {
		Intent questionnaire = new Intent(this, Questionnaire.class);
		startActivityForResult(questionnaire, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == 0) {
	         if (resultCode == RESULT_OK) {
	        	Toast.makeText(this, "FINISHED!", Toast.LENGTH_SHORT).show();
	            this.finish();
	         }
	     }
	}
}