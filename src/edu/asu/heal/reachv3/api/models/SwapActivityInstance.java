package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class SwapActivityInstance extends ActivityInstance {
	private String situation;
	private String worry;
	private String action;

	public SwapActivityInstance() {}

	public SwapActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, 
			String description, Date startTime, Date endTime, Date userSubmissionTime, 
			Date actualSubmissionTime, ActivityInstanceType instanceOf, String state, int patientPin) {
		super(activityInstanceId, createdAt, updatedAt, 
				description, startTime, endTime, userSubmissionTime, 
				actualSubmissionTime, instanceOf, state, patientPin);
		this.situation=null;
		this.worry=null;
		this.action=null;
	}
	
	public SwapActivityInstance(ActivityInstance activityInstance) {
		super(activityInstance.getActivityInstanceId(),
                activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                activityInstance.getInstanceOf(), activityInstance.getState(),
                activityInstance.getPatientPin());
		this.situation=null;
		this.worry=null;
		this.action=null;
	}

	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getWorry() {
		return worry;
	}
	public void setWorry(String worry) {
		this.worry = worry;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}


}
