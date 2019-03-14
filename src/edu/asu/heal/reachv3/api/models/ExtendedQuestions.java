package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class ExtendedQuestions {

	protected List<Options> options;
	protected List<Responses> responses;
	
	public ExtendedQuestions() {}
	
	public ExtendedQuestions(List<Options> options, List<Responses> responses) {
		this.options = options;
		this.responses = responses;
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

	@Override
	public String toString() {
		return "Questions : {" +
				", Options : '" + options.toString() + '\'' +
				", Response : '" + responses.toString() + '\'' +
				'}';
	}
}
