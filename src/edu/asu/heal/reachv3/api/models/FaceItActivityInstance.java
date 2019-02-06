package edu.asu.heal.reachv3.api.models;

import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceType;

import java.util.Date;
import java.util.List;

public class FaceItActivityInstance extends ActivityInstance {

	private List<FaceItChallenges> faceItChallenges;

	public FaceItActivityInstance() { }

	public FaceItActivityInstance(List<FaceItChallenges> faceItChallenges){ this.faceItChallenges = faceItChallenges;}

	public FaceItActivityInstance(String activityInstanceId, Date createdAt, Date updatedAt, String description,
								  Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
								  ActivityInstanceType instanceOf, String state, int patientPin,
								  List<FaceItChallenges> faceItChallenges) {
	     super(activityInstanceId, createdAt, updatedAt, description, startTime, endTime, 
	    		 userSubmissionTime, actualSubmissionTime, instanceOf, state, patientPin);
	    this.faceItChallenges=faceItChallenges;
	}

	public FaceItActivityInstance(ActivityInstance activityInstance, List<FaceItChallenges> faceItChallenges) {
    	super(activityInstance.getActivityInstanceId(),
                activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                activityInstance.getInstanceOf(), activityInstance.getState(),
                activityInstance.getPatientPin());
    	this.faceItChallenges=faceItChallenges;
	}
	
	public List<FaceItChallenges> getFaceItChallenges() {
		return faceItChallenges;
	}

	public void setFaceItChallenges(List<FaceItChallenges> faceItChallenges) {
		this.faceItChallenges = faceItChallenges;
	}
	

}