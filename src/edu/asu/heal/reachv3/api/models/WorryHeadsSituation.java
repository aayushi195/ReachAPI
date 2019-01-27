package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class WorryHeadsSituation {

    private int situationId;
    private String situationTitle;
    private List<WorryHeadsQuestion> questions;
    private int userAnswerId;
    private String worryTitle;

    public int getSituationId() {
        return situationId;
    }

    public void setSituationId(int situationId) {
        this.situationId = situationId;
    }

    public String getSituationTitle() {
        return situationTitle;
    }

    public void setSituationTitle(String situationTitle) {
        this.situationTitle = situationTitle;
    }

    public List<WorryHeadsQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<WorryHeadsQuestion> questions) {
        this.questions = questions;
    }

    public int getUserAnswerId() { return userAnswerId; }

    public void setUserAnswerId(int userAnswerId) { this.userAnswerId = userAnswerId; }

    public String getWorryTitle() { return worryTitle; }

    public void setWorryTitle(String worryTitle) { this.worryTitle = worryTitle; }
}
