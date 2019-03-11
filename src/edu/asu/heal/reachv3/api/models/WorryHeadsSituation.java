package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class WorryHeadsSituation extends ExtendedSituation {

//    private int situationId;
//    private String situationTitle;
//    private List<WorryHeadsQuestion> questions;
    private String worryTitle;
    
    public WorryHeadsSituation() {}
    
    public WorryHeadsSituation(int situationId, String situationTitle, List<ExtendedQuestions> questions, String worryTitle) {
		super(situationId, situationTitle, questions);
		this.worryTitle=worryTitle;
	}

    public String getWorryTitle() { return worryTitle; }

    public void setWorryTitle(String worryTitle) { this.worryTitle = worryTitle; }
}
