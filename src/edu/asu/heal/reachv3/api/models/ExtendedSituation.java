package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class ExtendedSituation {
	
	private int situationId;
	private String situationTitle;
	private List<ExtendedQuestions> questions;
	
	public ExtendedSituation() {}
	
	public ExtendedSituation(int situationId, String situationTitle, List<ExtendedQuestions> questions) {
		this.situationId = situationId;
		this.situationTitle = situationTitle;
		this.questions = questions;
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
	public List<ExtendedQuestions> getQuestions() {
		return questions;
	}
	public void setQuestions(List<ExtendedQuestions> questions) {
		this.questions = questions;
	}
	
	

}
