package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class FaceItSituation extends ExtendedSituation {

	private List<FaceItChallenges> faceItChallenges;
	
	public FaceItSituation() {}
    
    public FaceItSituation(List<FaceItChallenges> faceItChallenges) {
		this.faceItChallenges=faceItChallenges;
	}

	
	public List<FaceItChallenges> getFaceItChallenges() {
		return faceItChallenges;
	}

	public void setFaceItChallenges(List<FaceItChallenges> faceItChallenges) {
		this.faceItChallenges = faceItChallenges;
	}
}
