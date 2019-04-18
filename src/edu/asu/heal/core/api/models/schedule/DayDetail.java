package edu.asu.heal.core.api.models.schedule;

import java.util.ArrayList;
import java.util.List;

public class DayDetail {

	private int day;
	private List<ActivityScheduleDetail> activitySchedule;
	
	public DayDetail() {
		activitySchedule = new ArrayList<>();
	}
	
	public DayDetail(int day, List<ActivityScheduleDetail> activitySchedule) {
		this.day=day;
		this.activitySchedule = new ArrayList<>(activitySchedule);
	}	
	
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<ActivityScheduleDetail> getActivitySchedule() {
		return activitySchedule;
	}

	public void setActivitySchedule(List<ActivityScheduleDetail> activitySchedule) {
		this.activitySchedule = activitySchedule;
	}

	@Override
	    public String toString() {
	        return "ActivityInstance{" +
	                ", day='" + this.day + '\'' +
	                ", activitySchedule='" + this.activitySchedule.toString() + '\'' +
	                '}';
	    }
}
