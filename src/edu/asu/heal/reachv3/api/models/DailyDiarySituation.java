package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class DailyDiarySituation extends ExtendedSituation{

    private List<DailyDiaryQuestion> questions;

    public DailyDiarySituation(){
    }

    public DailyDiarySituation(int situationId, String situationTitle, List<DailyDiaryQuestion> questions) {
        super(situationId, situationTitle, null);
    }

    public List<DailyDiaryQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DailyDiaryQuestion> questions) {
        this.questions = questions;
    }
}
