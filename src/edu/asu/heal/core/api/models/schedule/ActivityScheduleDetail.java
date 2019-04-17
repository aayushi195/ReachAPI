package edu.asu.heal.core.api.models.schedule;

public class ActivityScheduleDetail {

	private String activity;
	private boolean isDailyActivity;
	private int totalCount;
	
	public ActivityScheduleDetail() {}
	
	public ActivityScheduleDetail(String activity, boolean isDailyActivity, int totalCount) {
		this.activity=activity;
		this.isDailyActivity = isDailyActivity;
		this.totalCount=totalCount;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public boolean isDailyActivity() {
		return isDailyActivity;
	}

	public void setDailyActivity(boolean isDailyActivity) {
		this.isDailyActivity = isDailyActivity;
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
	                ", isDailyActivity='" + this.isDailyActivity + '\'' +
	                ", totalCount=" + this.totalCount +
	                '}';
	    }
}
