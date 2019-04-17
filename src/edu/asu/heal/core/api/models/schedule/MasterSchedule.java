package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

public class MasterSchedule {

	public static String MODULE_ATTRIBUTE = "module";
	public static String ACTIVITYLIST_ATTRIBUTE = "activityList";

	private String module;
	private List<String> activityList;

	public MasterSchedule() {
		module = null;
		activityList = new ArrayList<>();
	}

	public MasterSchedule(String module, List<String> activityList) {
		this.module= module;
		this.activityList = new ArrayList<>(activityList);
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public List<String> getActivityList() {
		return activityList;
	}

	public void setActivityList(List<String> activityList) {
		this.activityList = activityList;
	}

	  @Override
	    public String toString() {
	        return "MasterSchedule : {" +
	                ", module='" + this.module + '\'' +
	                ", activityList='" + this.activityList.toString() + '\'' +
	                '}';
	    }

}
