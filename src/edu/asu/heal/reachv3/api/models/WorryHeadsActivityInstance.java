package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;

public class WorryHeadsActivityInstance extends ActivityInstance{
	private ExtendedActivityInstance extended;

    public WorryHeadsActivityInstance(){}

    public WorryHeadsActivityInstance(String activityInstanceId, String activityId, Date createdAt,
                                       Date updatedAt, String description,
                                       Date startTime, Date endTime, Date userSubmissionTime,
                                       Date actualSubmissionTime,
                                       String state, int patientPin, ExtendedActivityInstance extendedActivityInstance) {
        super(activityInstanceId, activityId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime, actualSubmissionTime, state, patientPin);
        this.extended=extendedActivityInstance;
    }

    public ExtendedActivityInstance getExtended() {
        return extended;
    }

    public void setExtended(ExtendedActivityInstance extendedActivityInstance) {
        this.extended = extendedActivityInstance;
    }
}
