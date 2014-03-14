package com.example.safetyalert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TimeoutQuestionnaire extends Activity {

	private GuardianRequest g;
	public static final String SORRY = "SORRY";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = getIntent();
		Bundle data = i.getExtras();
		g = (GuardianRequest) data.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);
		
		setContentView(R.layout.activity_timeout_questionnaire);
	}
	
	public void saveQuestionnaire(View view) {
		EditText reason, env;
		reason = (EditText) findViewById(R.id.questionnaire_reason);
		env = (EditText) findViewById(R.id.questionnaire_environment);

		String reasonText, envText;
		reasonText = reason.getText().toString();
		envText = env.getText().toString();

		Utils.appendToLog("[LATE GUARDIANSHIP ACCEPTANCE] Reason: " + reasonText);
		Utils.appendToLog("[LATE GUARDIANSHIP ACCEPTANCE] Reason: " + envText);
		
		Intent guardianMode = new Intent(this, GuardianModeActivity.class);
		guardianMode.putExtra(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST, g);
		guardianMode.putExtra(SORRY, true);
		startActivity(guardianMode);

		finish();
	}
}
