package edu.asu.heal.reachv3.api.models;

public class ExtendedActivityInstance {

	public static String DOMAIN_NAME = "domainName";
    public static String ACTIVITY_TYPE_NAME = "activityTypeName";
    public static String VERSION = "version";
    public static String SITUATION_ATTRIBUTE = "situation";
    
    private String domainName;
    private String activityTypeName;
    private String version;
    private ExtendedSituation situation;

    public ExtendedActivityInstance(){}

    public ExtendedActivityInstance(MakeBelieveSituation situation){
        this.situation=situation;
    }

    public ExtendedActivityInstance(StandUpSituation situation){
        this.situation=situation;
    }
    
    public ExtendedActivityInstance(WorryHeadsSituation situation){
        this.situation=situation;
    }
    public ExtendedActivityInstance(String domainName, String activityTypeName, String version, ExtendedSituation situation) {
        this.domainName=domainName;
        this.activityTypeName=activityTypeName;
        this.version=version;
        this.situation=situation;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public ExtendedSituation getSituation() {
        return situation;
    }

    public void setSituation(ExtendedSituation situation) {
        this.situation = situation;
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.domainName == null ? 0 :this.domainName.hashCode());
        result = PRIME * result + (this.activityTypeName == null ? 0 :this.activityTypeName.hashCode());
        result = PRIME * result + (this.version == null ? 0 :this.version.hashCode());
        result = PRIME * result + (this.situation==null ? 0 : this.situation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if (obj == null) return false;
        if(!(obj instanceof ExtendedActivityInstance)) return false;

        ExtendedActivityInstance temp = (ExtendedActivityInstance) obj;
        return this.domainName.equals(temp.domainName)
                && this.activityTypeName.equals(temp.activityTypeName)
                && this.version.equals(temp.version)
                && this.situation.equals(temp.situation);
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s + "ExtendedActivityInstance{" +
                "domainName='" + domainName + '\'' +
                ", activityTypeName='" + activityTypeName + '\'' +
                ", version='" + version + '\'' +
                ", situation='" + situation.toString() + '\'' +
                '}';
    }
}
