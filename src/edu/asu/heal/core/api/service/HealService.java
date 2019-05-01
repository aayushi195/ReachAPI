package edu.asu.heal.core.api.service;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;

import java.util.List;

public interface HealService {


    /****************************************  Service methods for Activity  ******************************************/
    List<Activity> getActivities(String domain);

    Activity createActivity(String title, String description);

    Activity getActivity(String activityId);

    Activity updateActivity(Activity activity);

    Activity deleteActivity(String activityId);
    

    /****************************************  Service methods for ActivityInstance  **********************************/
    List<ActivityInstance> getActivityInstances(int patientPin);

    ActivityInstance getActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance activityInstanceJson);

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance updateActivityInstance(String requestBody);

    /****************************************  Service methods for Domain  ********************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain addDomain(String title, String description, String state);

    /****************************************  Service methods for Patient  *******************************************/
    List<Patient> getPatients(String trialId);

    Patient getPatient(int patientPin);

    Patient createPatient(String trialId);

    Patient updatePatient(Patient patient);

    String deletePatient(String patientPin);

    /****************************************  Service methods for Trial  *********************************************/
    List<Trial> getTrials(String domain);

    Trial addTrial(Trial trialInstance);

    /****************************************  Service methods for Logger  ********************************************/
    Logger[] logMessage (Logger[] loggerInstance);

    /******************************************* Schedule methods ***********************************************************/
    
    PatientSchedule createPatientSchedule(int patientPin);
    
    PatientSchedule getPatientSchedule(int patientPin);
    
    PatientSchedule updatePatientSchedule(int patientPin,String module);
    
}