package edu.asu.heal.reachv3.api.models;

import java.util.Date;
import java.util.List;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class StandUpActivityInstance extends ActivityInstance{

	private StandUpSituation situation;

	public StandUpActivityInstance(){}
	public StandUpActivityInstance(StandUpSituation situation) {
		this.situation = situation;
	}

	public StandUpActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
								   Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
								   String state, int patientPin,
								   StandUpSituation situation) {
		super(activityInstanceId, activityId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
				actualSubmissionTime, state, patientPin);
		this.situation = situation;
	}


	public StandUpSituation getSituation() {
		return situation;
	}

	public void setSituation(StandUpSituation situation) {
		this.situation = situation;
	}
	
}
