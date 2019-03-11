package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class ExtendedQuestions {

	private List<Options> options;
	private int answerId;
	private List<Responses> responses = new ArrayList<>();
	
	public ExtendedQuestions() {}
	
	public ExtendedQuestions(List<Options> options, int answerId, List<Responses> responses) {
		this.options = options;
		this.answerId = answerId;
		this.responses = responses;
	}
	
	public List<Options> getOptions() {
		return options;
	}
	public void setOptions(List<Options> options) {
		this.options = options;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	public List<Responses> getResponses() {
		return responses;
	}
	public void setResponses(List<Responses> responses) {
		this.responses = responses;
	}

}
