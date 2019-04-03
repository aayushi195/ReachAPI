package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class StandUpSituation extends ExtendedSituation{

	private List<StandUpQuestion> questions;
	
	public StandUpSituation() {}
	
	public StandUpSituation(int situationId, String situationTitle, List<StandUpQuestion> questions) {
		super(situationId, situationTitle, null);
		this.questions=questions;
	}

	public List<StandUpQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<StandUpQuestion> questions) {
		this.questions = questions;
	}

	//	private int situationId;
	//	private String situationTitle;
	//	private List<StandUpQuestion> questions;
	//
	//	public int getSituationId() { return situationId; }
	//
	//	public void setSituationId(int situationId) { this.situationId = situationId; }
	//
	//	public String getSituationTitle() { return situationTitle; }
	//
	//	public void setSituationTitle(String situationTitle) { this.situationTitle = situationTitle; }
	//
	//	public List<StandUpQuestion> getQuestions() { return questions; }
	//
	//	public void setQuestions(List<StandUpQuestion> questions) { this.questions = questions; }
}
