package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class StandUpQuestion extends ExtendedQuestions {

	private int answerId;
	public StandUpQuestion(){}

	public StandUpQuestion(List<Options> options, int answerId, List<Responses> responses) {
		super(options,responses);
		this.answerId=answerId;
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
		
}
