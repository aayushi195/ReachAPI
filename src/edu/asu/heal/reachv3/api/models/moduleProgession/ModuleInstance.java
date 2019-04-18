package edu.asu.heal.reachv3.api.models.moduleProgession;

import edu.asu.heal.core.api.models.IHealModelType;

import java.util.ArrayList;
import java.util.List;

public class ModuleInstance implements IHealModelType {

    public static String PATIENT_PIN = "patientPin";
    public static String MODULE_PROGRESSION = "moduleProgression";

    private int patientPin;
    private List<ModuleBasedInstance> moduleProgression;

    public ModuleInstance() {
        this.patientPin = -1;
        this.moduleProgression = new ArrayList();
    }

    public ModuleInstance(int patientPin, List<ModuleBasedInstance> moduleInstance) {
        this.patientPin = patientPin;
        this.moduleProgression = new ArrayList(moduleInstance);
    }

    public int getPatientPin() {
        return patientPin;
    }

    public void setPatientPin(int patientPin) {
        this.patientPin = patientPin;
    }

    public List<ModuleBasedInstance> getModuleProgression() {
        return moduleProgression;
    }

    public void setModuleProgression(List<ModuleBasedInstance> moduleProgression) {
        this.moduleProgression = moduleProgression;
    }

    @Override
    public String toString() {
        return "ModuleDetails : {" +
                ", patientPin='" + this.patientPin + '\'' +
                ", moduleProgression='" + this.moduleProgression.toString() + '\'' +
                '}';
    }
}
