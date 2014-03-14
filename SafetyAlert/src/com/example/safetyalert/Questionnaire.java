package com.example.safetyalert;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
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
		
		finish();
	}
}