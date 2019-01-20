package edu.asu.heal.reachv3.api.models;

public class Emotions {
	
	public static String EMOTION_NAME="emotionName";
	public static String INTENSITY="intensity";
	public static String SUGGESTED_ACTIVITIES="suggestedActivities";
	public static String SESSION ="session";
	//public static String ID=""
	private String emotionName;
	private String intensity;
	private String suggestedActivities;
	private String session;
	
	public String getEmotionName() {
		return emotionName;
	}
	public void setEmotionName(String emotionName) {
		this.emotionName = emotionName;
	}
	public String getIntensity() {
		return intensity;
	}
	public void setIntensity(String intensity) {
		this.intensity = intensity;
	}
	public String getSuggestedActivity() {
		return suggestedActivities;
	}
	public void setSuggestedActivity(String suggestedActivities) {
		this.suggestedActivities = suggestedActivities;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}

}
