package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

import edu.asu.heal.core.api.models.IHealModelType;

public class PatientSchedule implements IHealModelType{
	
	public static String PATIENT_PIN = "patientPin";
	public static String PATIENT_SCHEDULE = "patientSchedule";
	
	
	private int patientPin;
	private List<ModuleDetail> patientSchedule;
	
	public PatientSchedule() {
		this.patientPin = -1;
		this.patientSchedule = new ArrayList<>();
	}
	
	public PatientSchedule(int patientPin, List<ModuleDetail> patientSchedule) {
		this.patientPin = patientPin;
		this.patientSchedule = new ArrayList<>(patientSchedule);
	}

	public int getPatientPin() {
		return patientPin;
	}

	public void setPatientPin(int patientPin) {
		this.patientPin = patientPin;
	}

	public List<ModuleDetail> getPatientSchedule() {
		return patientSchedule;
	}

	public void setPatientSchedule(List<ModuleDetail> patientSchedule) {
		this.patientSchedule = patientSchedule;
	}
	
	@Override
	public String toString() {
		return "ModuleDetails : {" +
				", patientPin='" + this.patientPin + '\'' +
				", patientSchedule='" + this.patientSchedule.toString() + '\'' +
				'}';
	}

}
