package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class StandUpQuestion {

	private List<StandUpOption> options;
	private int answerId;
	
	public StandUpQuestion() {
		
	}
	
	public List<StandUpOption> getOptions() {
		return options;
	}
	public void setOptions(List<StandUpOption> options) {
		this.options = options;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
		
}
