package edu.asu.heal.core.api.service;

import java.util.List;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.models.Logger;
import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.models.Trial;

public abstract class AHealService implements HealService{
	
	private static HealService __service;
	
	protected AHealService(HealService impl) {
		__service = impl;
		// if impl is null thn can we create new instance directly here via factory ?
		
	}


    /****************************************  Service methods for Activity  ******************************************/
   public List<Activity> getActivities(String domain){
	  return __service.getActivities(domain);
   }

    public Activity createActivity(String title, String description) {
    	return __service.createActivity(title, description);
    	
    }

    public Activity getActivity(String activityId) {
    	return __service.getActivity(activityId);
    }

    public Activity updateActivity(Activity activity) {
    	return __service.updateActivity(activity);
    }

    public Activity deleteActivity(String activityId) {
    	return __service.deleteActivity(activityId);
    }
    

    /****************************************  Service methods for ActivityInstance  **********************************/
    public List<ActivityInstance> getActivityInstances(int patientPin){
    	return __service.getActivityInstances(patientPin);
    }

    public ActivityInstance getActivityInstance(String activityInstanceId) {
    	return __service.getActivityInstance(activityInstanceId);
    }

    public ActivityInstance createActivityInstance(ActivityInstance activityInstanceJson) {
    	return __service.createActivityInstance(activityInstanceJson);
    }

    public ActivityInstance deleteActivityInstance(String activityInstanceId) {
    	return __service.deleteActivityInstance(activityInstanceId);
    }

    ActivityInstance updateActivityInstance(String requestBody);

    /****************************************  Service methods for Domain  ********************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain addDomain(String title, String description, String state);

    String addTestDomain(String title, String description, String state);

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


//    public static HealService getTheService() {
//        if (__service == null) {
//        	__service = DecoratorFactory.getTheService();
//        }
//
//        return __service;
//    }

}
