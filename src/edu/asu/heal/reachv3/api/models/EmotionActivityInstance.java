package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class EmotionActivityInstance extends ActivityInstance{
	
	public static String EMOTION_NAME="emotionName";
	public static String INTENSITY="intensity";
	public static String SUGGESTED_ACTIVITIES="suggestedActivities";
	public static String ACTIVITIES="activities";
	public static String SESSION ="session";
	//public static String ID=""
	private String emotionName;
	private String intensity;
	private String suggestedActivities; // this needs to be activity array with suggested activity
	private String session;
	
	public EmotionActivityInstance() {
		
	}
	
	public EmotionActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
								   Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
								   String state, int patientPin) {
		super(activityInstanceId, activityId,createdAt, updatedAt, description, startTime, endTime,
	    		 userSubmissionTime, actualSubmissionTime, state, patientPin);
		this.emotionName=null;
		this.session=null;
		this.intensity=null;
		this.suggestedActivities=null;
		
	}
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
