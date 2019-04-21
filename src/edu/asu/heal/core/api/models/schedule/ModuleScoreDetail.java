package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

public class ModuleScoreDetail {
	
	private String module;
	private int daySoFar;
	private List<ActivityScoreDetail> activityScores;
	
	public ModuleScoreDetail() {
		module = null;
		daySoFar=0;
		activityScores = new ArrayList<>();
	}
	
	public ModuleScoreDetail(String module, int daySoFar,List<ActivityScoreDetail> activityScores ) {
		this.module =module;
		this.daySoFar=daySoFar;
		this.activityScores = new ArrayList<>(activityScores);
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public int getDaySoFar() {
		return daySoFar;
	}

	public void setDaySoFar(int daySoFar) {
		this.daySoFar = daySoFar;
	}

	public List<ActivityScoreDetail> getActivityScores() {
		return activityScores;
	}

	public void setActivityScores(List<ActivityScoreDetail> activityScores) {
		this.activityScores = activityScores;
	}
	

}
