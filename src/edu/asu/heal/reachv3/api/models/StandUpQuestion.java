package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class StandUpQuestion extends ExtendedQuestions {

	public StandUpQuestion(){}

	public StandUpQuestion(List<Options> options, int answerId, List<Responses> responses) {
		super(options,answerId,responses);
	}
		
}
