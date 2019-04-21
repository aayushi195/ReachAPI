package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

public class PatientScoreDetail {

	public static final String PATIENT_PIN = "patientPin";
	public static final String SCORE_DATA = "scoreData";
	
	
	private int patientPin;
	private List<ModuleScoreDetail> scoreData;
	
	public PatientScoreDetail() {
		this.patientPin =-1;
		this.scoreData = new ArrayList<>();
	}
	
	public PatientScoreDetail(int patientPin, List<ModuleScoreDetail> scoreData) {
		this.patientPin =patientPin;
		this.scoreData = new ArrayList<>(scoreData);
	}

	public int getPatientPin() {
		return patientPin;
	}

	public void setPatientPin(int patientPin) {
		this.patientPin = patientPin;
	}

	public List<ModuleScoreDetail> getScoreData() {
		return scoreData;
	}

	public void setScoreData(List<ModuleScoreDetail> scoreData) {
		this.scoreData = scoreData;
	}
	
}
