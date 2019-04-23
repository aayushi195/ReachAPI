package edu.asu.heal.reachv3.api.models.patientRewards;

import java.util.ArrayList;
import java.util.List;

public class RewardsInstance {

    public static String PATIENT_PIN = "patientPin";
    public static String REWARDS = "rewards";

    private int patientPin;
    private List<RewardsBasedInstance> rewards;

    public RewardsInstance() {
        this.patientPin = -1;
        this.rewards = new ArrayList();
    }

    public RewardsInstance(int patientPin, List<RewardsBasedInstance> moduleInstance) {
        this.patientPin = patientPin;
        this.rewards = new ArrayList(moduleInstance);
    }

    public int getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(int patientPin) {
        this.patientPin = patientPin;
    }

    public List<RewardsBasedInstance> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardsBasedInstance> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "PatientRewards : {" +
                ", patientPin='" + this.patientPin + '\'' +
                ", rewards='" + this.rewards.toString() + '\'' +
                '}';
    }
}
