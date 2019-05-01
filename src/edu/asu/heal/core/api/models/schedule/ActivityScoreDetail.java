package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ActivityScoreDetail {
	
	private String activityName;
	private String activityId;
	private float actualCount;
	private float totalCount;
	private float score;
	private HashSet<String> activityInstances;
	
	public ActivityScoreDetail() {
		this.activityName=null;
		this.activityId=null;
		this.actualCount=0;
		this.totalCount=0;
		this.score=0;
		this.activityInstances = new HashSet<>();
	}
	
	public ActivityScoreDetail(String activityName,String activityId,float actualCount,
			float totalCount, float score,HashSet<String> activityInstances ) {
		this.activityName=activityName;
		this.activityId=activityId;
		this.actualCount = actualCount;
		this.totalCount = totalCount;
		this.score=score;
		this.activityInstances = new HashSet<>(activityInstances);
	}

	public HashSet<String> getActivityInstances() {
		return activityInstances;
	}

	public void setActivityInstances(HashSet<String> activityInstances) {
		this.activityInstances = activityInstances;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public float getActualCount() {
		return actualCount;
	}

	public void setActualCount(float actualCount) {
		this.actualCount = actualCount;
	}

	public float getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(float totalCount) {
		this.totalCount = totalCount;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	
	@Override
	public String toString() {
		return "ActivityScoreDetails : {" +
				", activityName='" + this.activityName + '\'' +
				", activityId='" + this.activityId + '\'' +
				", actualCount=" + this.actualCount +
				", totalCount=" + this.totalCount +
				", score=" + this.score +
				'}';
	}

}
