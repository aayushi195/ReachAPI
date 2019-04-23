package edu.asu.heal.core.api.hal;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import com.theoryinpractise.halbuilder.impl.api.Support;
import com.theoryinpractise.halbuilder.standard.StandardRepresentationFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleActivityList;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleInstance;

import java.util.List;

public class HALBuilderApiHALHelperImpl implements HALHelper{

    @Override
    public String getActivityInstancesJSON(ActivityInstance activityInstance, String activityInstanceResourcePath, String patientResourcePath, String activityResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("activity_instance", activityInstance)
                .withLink(Support.SELF, activityInstanceResourcePath + "/" + activityInstance.getActivityInstanceId())
                .withLink("patient_pin", patientResourcePath + "/" + String.valueOf(activityInstance.getPatientPin()))
                .withLink("activity_type", activityResourcePath + "/" + activityInstance.getActivityId());

        System.out.println("REPRESENTATION HERE");  
        System.out.println(representation);
        return representation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getActivityInstancesJSON(List<ActivityInstance> aList, String activityInstanceResourcePath, String patientResourcePath, String activityResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(activityInstanceResourcePath);
        for (ActivityInstance a : aList) {
            representation = factory.newRepresentation()
                    .withProperty("activity_instance", a)
                    .withLink(Support.SELF, activityInstanceResourcePath + "/" + a.getActivityInstanceId())
                    .withLink("patient_pin", patientResourcePath + "/" + String.valueOf(a.getPatientPin()))
                    .withLink("activity_type", activityResourcePath + "/" + a.getActivityId());

            finalRepresentation.withRepresentation("activity_instances", representation);
        }
        finalRepresentation.withLink("patient_pin", patientResourcePath);
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getActivitiesJSON(Activity a, String activityResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("activity", a)
                .withLink(Support.SELF, activityResourcePath + "/" + a.getActivityId());
        factory = null;
        return representation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getActivitiesJSON(List<Activity> activities, String activityResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(activityResourcePath);

        for (Activity a : activities) {
            representation = factory.newRepresentation()
                    .withProperty("activity", a)
                    .withLink(Support.SELF, activityResourcePath + "/" + a.getActivityId());

            finalRepresentation.withRepresentation("activities", representation);
        }
        factory = null;
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getDomainsJSON(Domain a, String domainResourcePath, String activityResourcePath, String trialResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("domain", a)
                .withLink(Support.SELF, domainResourcePath + "/" + a.getDomainId());
        for (String temp : a.getActivities())
            representation.withLink("activity", activityResourcePath + "/" + temp);
        for (String temp : a.getTrials())
            representation.withLink("trial", trialResourcePath + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getDomainsJSON(List<Domain> domains, String domainResourcePath, String activityResourcePath, String trialResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(domainResourcePath);
        for (Domain a : domains) {
            representation = factory.newRepresentation()
                    .withProperty("domain", a)
                    .withLink(Support.SELF, domainResourcePath + "/" + a.getDomainId());
            for (String temp : a.getActivities())
                representation.withLink("activity", activityResourcePath + "/" + temp);
            for (String temp : a.getTrials())
                representation.withLink("trial", trialResourcePath + "/" + temp);

            finalRepresentation.withRepresentation("domains", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getPatientsJSON(Patient a, String patientResourcePath, String activityInstanceResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;
        representation = factory.newRepresentation()
                .withProperty("patient", a)
                .withLink(Support.SELF, patientResourcePath + "/" + String.valueOf(a.getPin()));
        for (String temp : a.getActivityInstances())
            representation.withLink("activity_instance", activityInstanceResourcePath + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);

    }

    @Override
    public String getPatientsJSON(List<Patient> patients, String patientResourcePath, String activityInstanceResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(patientResourcePath);
        for (Patient a : patients) {
            representation = factory.newRepresentation()
                    .withProperty("patient", a)
                    .withLink(Support.SELF, patientResourcePath + "/" + String.valueOf(a.getPin()));
            for (String temp : a.getActivityInstances())
                representation.withLink("activity_instance", activityInstanceResourcePath + "/" + temp);

            finalRepresentation.withRepresentation("patients", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getTrialsJSON(Trial a, String trialResourcePath, String domainResourcePath, String patientResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;
        representation = factory.newRepresentation()
                .withProperty("trial", a)
                .withLink(Support.SELF, trialResourcePath + "/" + a.getTrialId())
                .withLink("domain", domainResourcePath + "/" + a.getDomainId());
        for (String temp : a.getPatients())
            representation.withLink("patients", patientResourcePath + "/" + temp);


        return representation.toString(RepresentationFactory.HAL_JSON);
    }

    @Override
    public String getTrialsJSON(List<Trial> trials, String trialResourcePath, String domainResourcePath, String patientResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation finalRepresentation, representation;

        finalRepresentation = factory.newRepresentation(trialResourcePath);
        for (Trial a : trials) {
            representation = factory.newRepresentation()
                    .withProperty("trial", a)
                    .withLink(Support.SELF, trialResourcePath + "/" + a.getTrialId())
                    .withLink("domain", domainResourcePath + "/" + a.getDomainId());
            for (String temp : a.getPatients())
                representation.withLink("patients", patientResourcePath + "/" + temp);

            finalRepresentation.withRepresentation("trials", representation);
        }
        return finalRepresentation.toString(RepresentationFactory.HAL_JSON);
    }

	@Override
	public String getPatientScheduleJSON(PatientSchedule patientSchedule, String patientSchedulePath,String patientResourcePath) {
		RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("patient_schedule", patientSchedule)
                .withLink(Support.SELF, patientSchedulePath + "?patientPin=" + patientSchedule.getPatientPin())
                .withLink("patient_pin", patientResourcePath + "/" + String.valueOf(patientSchedule.getPatientPin()));
              
        System.out.println("REPRESENTATION HERE");  
        System.out.println(representation);
        return representation.toString(RepresentationFactory.HAL_JSON);
	}

    @Override
    public String getmoduleProgressionJSON(ModuleInstance moduleInstance, String patientResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("module_progression", moduleInstance)
                .withLink("patient_pin", patientResourcePath + "/" + String.valueOf(moduleInstance.getPatientPin()));

        System.out.println("REPRESENTATION HERE");
        System.out.println(representation);
        return representation.toString(RepresentationFactory.HAL_JSON);
    }
    
    @Override
    public String getmoduleActivitiesJSON(ModuleActivityList moduleInstance, String patientResourcePath) {
        RepresentationFactory factory = new StandardRepresentationFactory();
        Representation representation;

        representation = factory.newRepresentation()
                .withProperty("module_progression", moduleInstance)
                .withLink("patient_pin", patientResourcePath + "/" + String.valueOf(moduleInstance.getPatientPin()));

        System.out.println("REPRESENTATION HERE");
        System.out.println(representation);
        return representation.toString(RepresentationFactory.HAL_JSON);
    }
}
