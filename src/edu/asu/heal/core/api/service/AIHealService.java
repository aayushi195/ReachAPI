package edu.asu.heal.core.api.service;

import java.util.List;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.Domain;
import edu.asu.heal.core.api.models.Logger;
import edu.asu.heal.core.api.models.Patient;
import edu.asu.heal.core.api.models.Trial;

public abstract class AIHealService implements IHealService {
	
	private IHealService __service;
	protected AIHealService(){}
	protected AIHealService(IHealService impl) {
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

    public ActivityInstance updateActivityInstance(String requestBody){
        return __service.updateActivityInstance(requestBody);
    }

    /****************************************  Service methods for Domain  ********************************************/
    public List<Domain> getDomains(){
        return __service.getDomains();
    }

    public Domain getDomain(String id){
        return __service.getDomain(id);
    }

    public Domain addDomain(String title, String description, String state){
        return __service.addDomain(title,description,state);
    }

    public String addTestDomain(String title, String description, String state){
        return __service.addTestDomain(title, description, state);
    }

    /****************************************  Service methods for Patient  *******************************************/
    public List<Patient> getPatients(String trialId){
        return __service.getPatients(trialId);
    }

    public Patient getPatient(int patientPin){
        return __service.getPatient(patientPin);
    }

    public Patient createPatient(String trialId){
        return __service.createPatient(trialId);
    }

    public Patient updatePatient(Patient patient){
        return __service.updatePatient(patient);
    }

    public String deletePatient(String patientPin){
        return __service.deletePatient(patientPin);
    }

    /****************************************  Service methods for Trial  *********************************************/
    public List<Trial> getTrials(String domain){
        return __service.getTrials(domain);
    }

    public Trial addTrial(Trial trialInstance){
        return __service.addTrial(trialInstance);
    }

    /****************************************  Service methods for Logger  ********************************************/
    public Logger[] logMessage (Logger[] loggerInstance){
        return __service.logMessage(loggerInstance);
    }

}
