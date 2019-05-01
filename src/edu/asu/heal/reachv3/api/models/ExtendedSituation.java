package edu.asu.heal.reachv3.api.models;

import java.util.ArrayList;
import java.util.List;

public class ExtendedSituation {

	protected int situationId;
	protected String situationTitle;
//	protected List<ExtendedQuestions> questions;

	public ExtendedSituation() {
	//	questions = new ArrayList<MakeBelieveQuestion>();
	}

	public ExtendedSituation(int situationId, String situationTitle, List<ExtendedQuestions> questions) {
		this.situationId = situationId;
		this.situationTitle = situationTitle;
//		this.questions = questions;
	}

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

	@Override
	public String toString() {
		return "Situation : {" +
				", situationId : '" + situationId + '\'' +
				", situationTitle : '" + situationTitle + '\'' +
				//", questions : " + questions.toString() +
				'}';
	}


}
