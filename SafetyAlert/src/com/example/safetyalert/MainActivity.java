package com.example.safetyalert;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private Intent safetyAppIntent;
	public static final String EXTRA_TEST_BOOLEAN = "com.example.safetyalert.TEST_BOOLEAN";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		safetyAppIntent = new Intent(this, SafetyAppService.class);

		setContentView(R.layout.activity_main);

		// Too much work to use a static layout XML
		// TODO Take out all the static buttons and render just the toggle programmatically
		ToggleButton t = (ToggleButton) findViewById(R.id.activation_toggle);

		// Need to change both the visual as well as the actual value
		// This doesn't work the right way. When notification spawns an activity, uncheck still does nothing.
		t.setChecked(safetyAppIsRunning());
		t.setSelected(safetyAppIsRunning());

		t.invalidate(); // force re-draw of button
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            addTestEntry();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void addTestEntry() {
		deactivateSafetyApp();
		safetyAppIntent.putExtra(EXTRA_TEST_BOOLEAN, true);
		activateSafetyApp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void activationClicked(View view) {
		// TODO This is not working properly, when the button is unchecked, nothing happens.
		if (((ToggleButton) view).isChecked()) activateSafetyApp();
		else deactivateSafetyApp();
	}

	public void questionnaire(View view) {
		Intent toQuestionnaire = new Intent(this, Questionnaire.class);
		startActivity(toQuestionnaire);
	}

	public void trigger(View view) {
		Intent toSecondary = new Intent(this, DisplayTriggerDetailsActivity.class);
		startActivity(toSecondary);
	}
	private void activateSafetyApp() {
		startService(safetyAppIntent);
	}

	private void deactivateSafetyApp() {
		stopService(safetyAppIntent);
	}

	private boolean safetyAppIsRunning() {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (SafetyAppService.class.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}