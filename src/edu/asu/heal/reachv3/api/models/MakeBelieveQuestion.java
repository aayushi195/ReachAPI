package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class MakeBelieveQuestion extends ExtendedQuestions{
    private String type;
    private int answerId;
  
    public MakeBelieveQuestion(){}

    public MakeBelieveQuestion(String type, List<Options> options, int answerId, List<Responses> responses) {
    	super(options,responses);
        this.type = type;
        this.answerId=answerId;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
    
}
