package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class MakeBelieveQuestion extends ExtendedQuestions{
    private String type;
  
    public MakeBelieveQuestion(){}

    public MakeBelieveQuestion(String type, List<Options> options, int answerId, List<Responses> responses) {
    	super(options,answerId,responses);
        this.type = type;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
