package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;

public class WorryHeadsActivityInstance extends ActivityInstance{
    private List<WorryHeadsSituation> situation;

    public WorryHeadsActivityInstance(){}
    public WorryHeadsActivityInstance(List<WorryHeadsSituation> situation) {
        this.situation = situation;
    }

    public WorryHeadsActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description,
                                      Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                                      ActivityInstanceType instanceOf, String state, int patientPin,
                                      List<WorryHeadsSituation> situation) {
        super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, userSubmissionTime,
                actualSubmissionTime, instanceOf, state, patientPin);
        this.situation = situation;
    }


    public List<WorryHeadsSituation> getSituation() {
        return situation;
    }

    public void setSituation(List<WorryHeadsSituation> situation) {
        this.situation = situation;
    }
}
