package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;

public class MakeBelieveActivityInstance extends ActivityInstance{
    private MakeBelieveSituation situation;

    public MakeBelieveActivityInstance(){}
    public MakeBelieveActivityInstance(MakeBelieveSituation situation) {
        this.situation = situation;
    }

    public MakeBelieveActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
                                       Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                       String state, int patientPin, MakeBelieveSituation situation) {
        super(activityInstanceId, activityId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime, actualSubmissionTime, state, patientPin);
        this.situation = situation;
    }


    public MakeBelieveSituation getSituation() {
        return situation;
    }

    public void setSituation(MakeBelieveSituation situation) {
        this.situation = situation;
    }
}
