package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class WorryHeadsSituation extends ExtendedSituation {

//    private int situationId;
//    private String situationTitle;
    private List<WorryHeadsQuestion> questions;
    private String worryTitle;
    
    public WorryHeadsSituation() {}
    
    public WorryHeadsSituation(int situationId, String situationTitle, List<WorryHeadsQuestion> questions, String worryTitle) {
		super(situationId, situationTitle, null);
		this.questions=questions;
		this.worryTitle=worryTitle;
	}

    public List<WorryHeadsQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<WorryHeadsQuestion> questions) {
		this.questions = questions;
	}

	public String getWorryTitle() { return worryTitle; }

    public void setWorryTitle(String worryTitle) { this.worryTitle = worryTitle; }
}
