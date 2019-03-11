package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class WorryHeadsQuestion extends ExtendedQuestions {

    private List<Integer> answerIds;

    public WorryHeadsQuestion(){}

    public WorryHeadsQuestion(List<Options> options, List<Integer> answerIds, List<Responses> responses) {
    	super(options,0,responses);
        this.answerIds = answerIds;
    }

    public List<Integer> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(List<Integer> answerIds) {
        this.answerIds = answerIds;
    }

}
