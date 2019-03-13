package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class SwapSituation extends ExtendedSituation{

    private List<SwapQuestion> questions;

    public SwapSituation(){
    }

    public SwapSituation(int situationId, String situationTitle, List<SwapQuestion> questions) {
        super(situationId, situationTitle, null);
    }

    public List<SwapQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<SwapQuestion> questions) {
        this.questions = questions;
    }
}
