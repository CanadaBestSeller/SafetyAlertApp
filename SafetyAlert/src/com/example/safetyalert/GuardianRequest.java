package com.example.safetyalert;

public class GuardianRequest {

	public long triggerTime;
	public int guardianshipDuration;
	public int interval;
	public String[] reasons;
	
	public GuardianRequest(long triggerTime, int guardianshipDuration, int interval, String[] reasons) {
		this.triggerTime = triggerTime;
		this.guardianshipDuration = guardianshipDuration;
		this.interval = interval;
		this.reasons = reasons;
	}
}