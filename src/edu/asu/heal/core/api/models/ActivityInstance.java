package edu.asu.heal.core.api.models;

import java.util.Date;

public class ActivityInstance implements IHealModelType {
    public static String CREATEDAT_ATTRIBUTE = "createdAt";
    public static String UPDATEDAT_ATTRIBUTE = "updatedAt";
    public static String STARTTIME_ATTRIBUTE = "startTime";
    public static String ENDTIME_ATTRIBUTE = "endTime";
    public static String USERSUBMISSIONTIME_ATTRIBUTE = "userSubmissionTime";
    public static String ACTUALSUBMISSIONTIME_ATTRIBUTE = "actualSubmissionTime";
    public static String INSTANCEOF_ATTRIBUTE = "instanceOf";
    public static String STATE_ATTRIBUTE = "state";
    public static String DESCRIPTION_ATTRIBUTE = "description";
    public static String ACTIVITYINSTANCEID_ATTRIBUTE = "activityInstanceId";
    public static String ACTIVITYID_ATTRIBUTE = "activityId";
    public static String PATIENT_PIN = "patientPin";


    private String activityInstanceId;
    private String activityId;
    private Date createdAt;
    private Date updatedAt;
    private String description;
    private Date startTime;
    private Date endTime;
    private Date userSubmissionTime;
    private Date actualSubmissionTime;
 //   private ActivityInstanceType instanceOf;
    private String state;
    private int patientPin;

    public ActivityInstance() {
    }

    public ActivityInstance(String activityInstanceId, String activityId, Date createdAt, Date updatedAt, String description,
                            Date startTime, Date endTime, Date userSubmissionTime, Date actualSubmissionTime,
                            String state, int patientPin) {
        this.activityInstanceId = activityInstanceId;
        this.activityId =activityId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userSubmissionTime = userSubmissionTime;
        this.actualSubmissionTime = actualSubmissionTime;
        this.state = state;
        this.patientPin = patientPin;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getUserSubmissionTime() {
        return userSubmissionTime;
    }

    public void setUserSubmissionTime(Date userSubmissionTime) {
        this.userSubmissionTime = userSubmissionTime;
    }

    public Date getActualSubmissionTime() {
        return actualSubmissionTime;
    }

    public void setActualSubmissionTime(Date actualSubmissionTime) {
        this.actualSubmissionTime = actualSubmissionTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivityInstanceId() {
        return activityInstanceId;
    }

    public void setActivityInstanceId(String activityInstanceId) {
        this.activityInstanceId = activityInstanceId;
    }

    public int getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(int patientPin) {
        this.patientPin = patientPin;
    }

    @Override
    public String toString() {
        return "ActivityInstance{" +
                ", activityInstanceId='" + activityInstanceId + '\'' +
                ", activityId='" + activityId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userSubmissionTime=" + userSubmissionTime +
                ", actualSubmissionTime=" + actualSubmissionTime +
                ", state='" + state + '\'' +
                ", description='" + description + '\'' +
                ", patientPin='" + patientPin + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.activityInstanceId == null ? 0 :this.activityInstanceId.hashCode());
        result = PRIME * result + (this.activityId == null ? 0 :this.activityId.hashCode());
        result = PRIME * result + (this.createdAt == null ? 0: this.createdAt.hashCode());
        result = PRIME * result + (this.updatedAt == null ? 0 : this.updatedAt.hashCode());
        result = PRIME * result + (this.description == null ? 0 : this.description.hashCode());
        result = PRIME * result + (this.startTime == null ? 0 :this.startTime.hashCode());
        result = PRIME * result + (this.endTime == null ? 0 :this.endTime.hashCode());
        result = PRIME * result + (this.userSubmissionTime == null ? 0 :this.userSubmissionTime.hashCode());
        result = PRIME * result + (this.actualSubmissionTime == null ? 0 :this.actualSubmissionTime.hashCode());
        result = PRIME * result + (this.state == null ? 0 :this.state.hashCode());
        result = PRIME * result + this.patientPin;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof ActivityInstance)) return false;

        ActivityInstance temp = (ActivityInstance) obj;
        return this.activityInstanceId.equals(temp.activityInstanceId)
                && this.activityId.equals(temp.activityId)
                && this.createdAt.equals(temp.createdAt)
                && this.updatedAt.equals(temp.updatedAt)
                && this.description.equals(temp.description)
                && this.startTime.equals(temp.startTime)
                && this.endTime.equals(temp.endTime)
                && this.userSubmissionTime.equals(temp.userSubmissionTime)
                && this.actualSubmissionTime.equals(temp.actualSubmissionTime)
                && this.state.equals(temp.state)
                && this.patientPin == temp.patientPin;
    }

}
