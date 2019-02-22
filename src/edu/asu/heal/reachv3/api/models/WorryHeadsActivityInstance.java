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

    public WorryHeadsActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
                                      Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                      String state, int patientPin,
                                      WorryHeadsSituation situation) {
        super(activityInstanceId, activityId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
                actualSubmissionTime, state, patientPin);
        this.situation = situation;
    }


    public WorryHeadsSituation getSituation() {
        return situation;
    }

    public void setSituation(WorryHeadsSituation situation) {
        this.situation = situation;
    }
}
