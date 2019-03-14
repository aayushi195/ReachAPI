package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class WorryHeadsQuestion extends ExtendedQuestions {

    protected List<Integer> answerId;
    protected List<Options> options;
	protected List<Responses> responses;

    public WorryHeadsQuestion(){
    	answerId = new ArrayList<>();
    	options=new ArrayList<>();
    	responses = new ArrayList<>();
    }

    public WorryHeadsQuestion(List<Options> options, List<Integer> answerId, List<Responses> responses) {
    //	super(options,responses);
        this.answerId = answerId;
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

	public List<Integer> getAnswerId() {
        return answerId;
    }

    public void setAnswerId(List<Integer> answerId) {
        this.answerId = answerId;
    }

}
