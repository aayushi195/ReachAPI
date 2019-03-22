package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class EmotionActivityInstance extends ActivityInstance {
	
	public static String EMOTION_NAME="emotionName";
	public static String INTENSITY="intensity";
	public static String SUGGESTED_ACTIVITIES="suggestedActivities";
	public static String ACTIVITIES="activities";
	public static String SESSION ="session";

	public ExtendedActivityInstance extended;
	
	public EmotionActivityInstance() { }
	
	public EmotionActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
								   Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
								   String state, int patientPin, ExtendedActivityInstance extendedActivityInstance) {
		super(activityInstanceId, activityId,createdAt, updatedAt, description, startTime, endTime,
	    		 userSubmissionTime, actualSubmissionTime, state, patientPin);
		this.extended=extendedActivityInstance;
	}

}
