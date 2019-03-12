package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class MakeBelieveSituation extends ExtendedSituation {
    private String name;
//    private int situationId;
//    private String situationTitle;
    private List<MakeBelieveQuestion> questions;

   
	public MakeBelieveSituation() {}
    
    public MakeBelieveSituation(int situationId, String situationTitle, List<MakeBelieveQuestion> questions, String name) {
		super(situationId, situationTitle, null);
		this.name=name;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//	public List<MakeBelieveQuestion> getMakeBelieveQuestions() {
//		return questions;
//	}
//
//	public void setMakeBelieveQuestions(List<MakeBelieveQuestion> makeBelieveQuestions) {
//		this.questions = makeBelieveQuestions;
//	}
    

}
