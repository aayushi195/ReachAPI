package edu.asu.heal.reachv3.api.models.patientRewards;

public class RewardsBasedInstance {

    private int level;
    private String skill;

    public RewardsBasedInstance() {
        level =0;
        skill=null;
    }

    public RewardsBasedInstance(int level, String skill) {
        this.level = level;
        this.skill = skill;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
