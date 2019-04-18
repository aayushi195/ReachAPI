package edu.asu.heal.core.api.models.schedule;

public class ActivityScheduleDetail {

	private String activity;
	private boolean dailyActivity;
	private int totalCount;
	
	public ActivityScheduleDetail() {}
	
	public ActivityScheduleDetail(String activity, boolean dailyActivity, int totalCount) {
		this.activity=activity;
		this.dailyActivity = dailyActivity;
		this.totalCount=totalCount;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public boolean getDailyActivity() {
		return dailyActivity;
	}

	public void setDailyActivity(boolean dailyActivity) {
		this.dailyActivity = dailyActivity;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	  @Override
	    public String toString() {
	        return "ActivitySchedule : {" +
	                ", activity='" + this.activity + '\'' +
	                ", dailyActivity='" + this.dailyActivity + '\'' +
	                ", totalCount=" + this.totalCount +
	                '}';
	    }
}
