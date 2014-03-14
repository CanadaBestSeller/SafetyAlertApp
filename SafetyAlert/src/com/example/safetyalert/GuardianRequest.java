package com.example.safetyalert;

import android.os.Parcel;
import android.os.Parcelable;

public class GuardianRequest implements Parcelable {

	public long triggerTime;
	public int guardianshipDuration;
	public int interval;
	public String[] reasons;

	public static final Parcelable.Creator<GuardianRequest> CREATOR = new Parcelable.Creator<GuardianRequest>() {
		public GuardianRequest createFromParcel(Parcel in) {
			return new GuardianRequest(in);
		}

		public GuardianRequest[] newArray(int size) {
			return new GuardianRequest[size];
		}
	};

	public GuardianRequest(long triggerTime, int guardianshipDuration,
			int interval, String[] reasons) {
		this.triggerTime = triggerTime;
		this.guardianshipDuration = guardianshipDuration;
		this.interval = interval;
		this.reasons = reasons;
	}

	public GuardianRequest(Parcel source) {
		triggerTime = source.readLong();
		guardianshipDuration = source.readInt();
		interval = source.readInt();
		reasons = source.createStringArray();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(triggerTime);
		dest.writeInt(guardianshipDuration);
		dest.writeInt(interval);
		dest.writeStringArray(reasons);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Trigger time: " + Long.toString(triggerTime) + "\n");
		sb.append("Trigger date: " + Utils.long2timestamp(triggerTime) + "\n");
		sb.append("Guardianship duration: "
				+ Integer.toString(guardianshipDuration) + "\n");
		sb.append("Interval: " + Integer.toString(interval) + "\n");
		sb.append("REASONS:\n");

		for (String reason : reasons) {
			sb.append(reason + "\n");
		}

		return sb.toString();
	}
	
	public String getTriggerTimeAsString() {
		return Utils.long2timestamp(triggerTime);
	}

	public String getReasonsAsString() {
		StringBuilder sb = new StringBuilder();
		for (String reason : reasons) {
			sb.append(reason.trim());
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public int describeContents() {
		return 0;
	}
}