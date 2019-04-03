package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class MakeBelieveQuestion extends ExtendedQuestions{
    public String type;
    public int answerId;
    protected List<Options> options;
	protected List<Responses> responses;
  
    public MakeBelieveQuestion(){
    	responses = new ArrayList<>();
    	options = new ArrayList<>();
    }

    public MakeBelieveQuestion(String type, List<Options> options, int answerId, List<Responses> responses) {
    //	super(options,responses);
    	this.options=options;
    	this.responses=responses;
        this.type = type;
        this.answerId=answerId;
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

	 @Override
	    public String toString() {
	        return "Questions : {" +
					", Options : '" + options.toString() + '\'' +
					", Response : '" + responses.toString() + '\'' +
	                ", answerId : " + answerId +
	                ", type : " + type +
	                '}';
	    }

}
