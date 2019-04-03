package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class MakeBelieveQuestion {
    private String type;
    private List<MakeBelieveOption> options;
    private int answerId;
    private List<MakeBelieveResponse> responses = new ArrayList<>();

    public MakeBelieveQuestion(){}

    public MakeBelieveQuestion(String type, List<MakeBelieveOption> options, int answerId, List<MakeBelieveResponse> responses) {
        this.type = type;
        this.options = options;
        this.answerId = answerId;
        this.responses = responses;
    }

    public List<MakeBelieveOption> getOptions() {
        return options;
    }

    public void setOptions(List<MakeBelieveOption> options) {
        this.options = options;
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

    public List<MakeBelieveResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<MakeBelieveResponse> responses) {
        this.responses = responses;
    }
}
