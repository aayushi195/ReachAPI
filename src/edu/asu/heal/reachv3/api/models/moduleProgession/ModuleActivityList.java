package edu.asu.heal.reachv3.api.models.moduleProgession;

import java.util.ArrayList;
import java.util.List;

public class ModuleActivityList {
	
	private int patientPin;
	private String module;
	private List<ModuleAcivityDetail> activityList;
	
	public ModuleActivityList() {
		patientPin=-1;
		module=null;
		activityList = new ArrayList<>();
	}
	
	public ModuleActivityList(int patientPin,String module,List<ModuleAcivityDetail> activityList ) {
		this.patientPin=patientPin;
		this.module = module;
		this.activityList = new ArrayList<>(activityList);
	}

	public int getPatientPin() {
		return patientPin;
	}

	public void setPatientPin(int patientPin) {
		this.patientPin = patientPin;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<ModuleAcivityDetail> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<ModuleAcivityDetail> activityList) {
		this.activityList = activityList;
	}
		

}
