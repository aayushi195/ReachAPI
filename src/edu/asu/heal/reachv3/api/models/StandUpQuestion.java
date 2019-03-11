package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class StandUpQuestion {

	private List<StandUpOption> options;
	private int answerId;
	private List<StandUpResponse> responses = new ArrayList<>();

	public StandUpQuestion(){}

	public StandUpQuestion(List<StandUpOption> options, int answerId, List<StandUpResponse> responses) {
		this.options = options;
		this.answerId = answerId;
		this.responses = responses;
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

	public List<StandUpResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<StandUpResponse> responses) {
		this.responses = responses;
	}
		
}
