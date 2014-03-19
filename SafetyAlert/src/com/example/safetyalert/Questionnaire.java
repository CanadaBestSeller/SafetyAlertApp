package com.example.safetyalert;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Questionnaire extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_questionnaire);
		getActionBar().setDisplayHomeAsUpEnabled(false);
	}

	public void saveQuestionnaire(View view) {
		RadioGroup rg = (RadioGroup) findViewById(R.id.danger_level_radio_group);
		
		int id = rg.getCheckedRadioButtonId();
		View radioButton = rg.findViewById(id);
		int radioId = rg.indexOfChild(radioButton);

		RadioButton btn = (RadioButton) rg.getChildAt(radioId);
		String radioSelection = (String) btn.getText();
		
		EditText et = (EditText) findViewById(R.id.questionnaire_body);
		String bodySelection = et.getText().toString();

		Utils.appendToLog("[QUESTIONNAIRE DANGER LEVEL] " + radioSelection);
		Utils.appendToLog("[QUESTIONNAIRE JUSTIFICATION] " + bodySelection);
		
		Toast.makeText(this, "Thank you!", Toast.LENGTH_SHORT).show();
		
		setResult(RESULT_OK, null);
		new GuardianModeAlarm().setAlarm(this);
		
		finish();
	}
}