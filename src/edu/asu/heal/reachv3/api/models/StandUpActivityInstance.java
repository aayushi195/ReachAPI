package edu.asu.heal.reachv3.api.models;

import java.util.Date;
import java.util.List;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

public class StandUpActivityInstance extends ActivityInstance{
	
	private List<StandUpSituation> situations;
	
	public StandUpActivityInstance() {}
	
	public StandUpActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt,
								   String description, Date startTime, Date endTime, Date userSubmissionTime,
								   Date actualSubmissionTime, ActivityInstanceType instanceOf, String state,
								   int patientPin, List<StandUpSituation> situation) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
				actualSubmissionTime, instanceOf, state, patientPin);
        this.situations = situation;
        }
	
	public StandUpActivityInstance(List<StandUpSituation> situation) {
		this.situations = situation;
	}

	public List<StandUpSituation> getSituations() {
		return situations;
	}

	public void setSituations(List<StandUpSituation> situation) {
		this.situations = situation;
	}
	
}
