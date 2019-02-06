package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;

public class WorryHeadsActivityInstance extends ActivityInstance{
    private WorryHeadsSituation situation;

    public WorryHeadsActivityInstance(){}
    public WorryHeadsActivityInstance(WorryHeadsSituation situation) {
        this.situation = situation;
    }

    public WorryHeadsActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description,
                                      Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                      ActivityInstanceType instanceOf, String state, int patientPin,
                                      WorryHeadsSituation situation) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
                actualSubmissionTime, instanceOf, state, patientPin);
        this.situation = situation;
    }

    public WorryHeadsActivityInstance(ActivityInstance activityInstance, WorryHeadsSituation
    		situation) {
    	super(activityInstance.getActivityInstanceId(),
                activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                activityInstance.getInstanceOf(), activityInstance.getState(),
                activityInstance.getPatientPin());
    	this.situation=situation;
    }

    public WorryHeadsSituation getSituation() {
        return situation;
    }

    public void setSituation(WorryHeadsSituation situation) {
        this.situation = situation;
    }
}
