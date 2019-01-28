package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class WorryHeadsQuestion {

    private List<WorryHeadsOption> options;
    private List<Integer> answerId;
    private List<WorryHeadsResponse> responses;

    public WorryHeadsQuestion(){}

    public WorryHeadsQuestion(List<WorryHeadsOption> options, List<Integer> answerId, List<WorryHeadsResponse> responses) {

        this.options = options;
        this.answerId = answerId;
        this.responses = responses;
    }

    public List<WorryHeadsOption> getOptions() {
        return options;
    }

    public void setOptions(List<WorryHeadsOption> options) {
        this.options = options;
    }

    public List<Integer> getAnswerId() {
        return answerId;
    }

    public void setAnswerId(List<Integer> answerId) {
        this.answerId = answerId;
    }

    public List<WorryHeadsResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<WorryHeadsResponse> responses) {
        this.responses = responses;
    }
}
