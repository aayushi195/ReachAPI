package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

public class ActivityScoreDetail {
	
	private String activityName;
	private String activityId;
	private int actualCount;
	private int totalCount;
	private float score;
	private List<String> activityInstances;
	
	public ActivityScoreDetail() {
		this.activityName=null;
		this.activityId=null;
		this.actualCount=0;
		this.totalCount=0;
		this.score=0;
		this.activityInstances = new ArrayList<>();
	}
	
	public ActivityScoreDetail(String activityName,String activityId,int actualCount,
			int totalCount, float score,List<String> activityInstances ) {
		this.activityName=activityName;
		this.activityId=activityId;
		this.actualCount = actualCount;
		this.totalCount = totalCount;
		this.activityInstances = new ArrayList<>(activityInstances);
	}

	public List<String> getActivityInstances() {
		return activityInstances;
	}

	public void setActivityInstances(List<String> activityInstances) {
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

	public int getActualCount() {
		return actualCount;
	}

	public void setActualCount(int actualCount) {
		this.actualCount = actualCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
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
