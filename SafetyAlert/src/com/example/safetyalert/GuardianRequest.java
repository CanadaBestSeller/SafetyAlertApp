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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trigger time: " + Long.toString(triggerTime) + "\n");
		sb.append("Trigger date: " + Utils.long2timestamp(triggerTime) + "\n");
		sb.append("Guardianship duration: " + Integer.toString(guardianshipDuration) + "\n");
		sb.append("Interval: " + Integer.toString(interval) + "\n");
		sb.append("REASONS:\n");
		
		for (String reason : reasons) {
			sb.append(reason + "\n");
		}
		
		return sb.toString();
	}
}