package edu.asu.heal.reachv3.api.models;

public class EmotionSituation extends ExtendedSituation {

    private String emotionName;
    private String intensity;
    private String suggestedActivities; // this needs to be activity array with suggested activity
    private String session;

    public EmotionSituation() {}

    public EmotionSituation(String emotionName, String intensity, String suggestedActivities,String session ){
        this.emotionName=emotionName;
        this.intensity=intensity;
        this.suggestedActivities=suggestedActivities;
        this.session=session;
    }

    public String getEmotionName() {
        return emotionName;
    }
    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }
    public String getIntensity() {
        return intensity;
    }
    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }
    public String getSuggestedActivity() {
        return suggestedActivities;
    }
    public void setSuggestedActivity(String suggestedActivities) {
        this.suggestedActivities = suggestedActivities;
    }
    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }
}
