package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class StandUpQuestion extends ExtendedQuestions {

	protected int answerId;
	protected List<Options> options;
	protected List<Responses> responses;
	
	public StandUpQuestion(){
		options = new ArrayList<>();
		responses = new ArrayList<>();
	}

	public StandUpQuestion(List<Options> options, int answerId, List<Responses> responses) {
	//	super(options,responses);
		this.answerId=answerId;
		this.options=options;
		this.responses=responses;
	}

	public List<Options> getOptions() {
		return options;
	}

	public void setOptions(List<Options> options) {
		this.options = options;
	}

	public List<Responses> getResponses() {
		return responses;
	}

	public void setResponses(List<Responses> responses) {
		this.responses = responses;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
		
}
