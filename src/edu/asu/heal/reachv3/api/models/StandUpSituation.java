package edu.asu.heal.reachv3.api.models;

import java.util.List;

public class StandUpSituation {
	
	private int situationId;
	private String situationTitle;
	private List<StandUpQuestion> questions;
	private List<StandUpResponse> responses;

	public List<StandUpResponse> getResponses() {
		return responses;
	}

	public void setResponses(List<StandUpResponse> responses) {
		this.responses = responses;
	}

	public List<StandUpQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<StandUpQuestion> questions) {
		this.questions = questions;
	}
	
	public StandUpSituation() {
		
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
}
