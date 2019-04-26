package edu.asu.heal.reachv3.api.models.moduleProgession;

public class ModuleAcivityDetail {
	
	private String activityName;
	private String activityId;
	private boolean callToAction;
	
	public ModuleAcivityDetail() {
		activityName = null;
		activityId = null;
		callToAction = false;
	}
	
	public ModuleAcivityDetail(String activityName, String activityId, boolean callToAction) {
		this.activityId=activityId;
		this.activityName = activityName;
		this.callToAction = callToAction;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public boolean isCallToAction() {
		return callToAction;
	}

	public void setCallToAction(boolean callToAction) {
		this.callToAction = callToAction;
	}
	

    @Override
    public String toString() {
        return "ModuleActivityDetails : {" +
                ", activityName='" + activityName + '\'' +
                ", activityId='" + activityId + '\'' +
                ", callToAction=" + callToAction +
                '}';
    }
	
	

}
