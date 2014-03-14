package com.example.safetyalert;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlertResponseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle data = getIntent().getExtras();
		GuardianRequest g = 
				(GuardianRequest) data.getParcelable(GuardianModeAlarm.EXTRA_GUARDIAN_REQUEST);

        RelativeLayout rr = new RelativeLayout(this);

        TextView body = new TextView(this);
        body.setText(g.toString());

        rr.addView(body);
        setContentView(rr);
        
        Utils.appendToLog("[ALERT RESPONDED]");
	}
}