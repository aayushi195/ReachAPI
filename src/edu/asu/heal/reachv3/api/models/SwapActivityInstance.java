package edu.asu.heal.reachv3.api.models;

import java.util.Date;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class SwapActivityInstance extends ActivityInstance {

	private ExtendedActivityInstance extended;

	public SwapActivityInstance() {
	}

	public SwapActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
									  Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
									  String state, int patientPin, ExtendedActivityInstance extendedActivityInstance) {
		super(activityInstanceId, activityId,createdAt, updatedAt, description, startTime, endTime,
				userSubmissionTime, actualSubmissionTime, state, patientPin);
		this.extended=extendedActivityInstance;
	}

	public ExtendedActivityInstance getExtended() {
		return extended;
	}

	public void setExtended(ExtendedActivityInstance extendedActivityInstance) {
		this.extended = extendedActivityInstance;
	}

//	private String situation;
//	private String worry;
//	private String action;
//
//	public SwapActivityInstance() {}
//
//	public SwapActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
//								Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
//								String state, int patientPin) {
//		super(activityInstanceId,activityId, createdAt, updatedAt,
//				description, startTime, endTime, userSubmissionTime,
//				actualSubmissionTime, state, patientPin);
//		this.situation=null;
//		this.worry=null;
//		this.action=null;
//	}
//
//	public String getSituation() {
//		return situation;
//	}
//	public void setSituation(String situation) {
//		this.situation = situation;
//	}
//	public String getWorry() {
//		return worry;
//	}
//	public void setWorry(String worry) {
//		this.worry = worry;
//	}
//	public String getAction() {
//		return action;
//	}
//	public void setAction(String action) {
//		this.action = action;
//	}


}
