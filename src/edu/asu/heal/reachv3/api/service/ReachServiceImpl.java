package edu.asu.heal.reachv3.api.service;

import java.util.List;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.service.AHealService;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.models.*;


public class ReachServiceImpl extends AHealService implements IReachService {

    private static HealService service = AHealService.getTheService();
    
 
    /****************************************  Service methods for Activity  ******************************************/
    
    @Override
    public List<Activity> getActivities(String domain) {
    	return service.getActivities(domain);
    }

    @Override
    public Activity createActivity(String title, String description) {
    	return service.createActivity(title, description);
    }

    @Override
    public Activity getActivity(String activityId) {
    	return service.getActivity(activityId);
    }

    @Override
    public Activity updateActivity(Activity activity) {
    	return service.updateActivity(activity);
    }

    @Override
    public Activity deleteActivity(String activityId) {
    	return service.deleteActivity(activityId);
    }

    /****************************************  Service methods for ActivityInstance  **********************************/
    @Override
    public List<ActivityInstance> getActivityInstances(int patientPin) {
        return service.getActivityInstances(patientPin);
    }

     
    @Override
    public ActivityInstance getActivityInstance(String activityInstanceId) {
    	
        try {
        	ActivityInstance rval = service.getActivityInstance(activityInstanceId);
            DAO dao = DAOFactory.getTheDAO();

            if(rval!=null && rval.getInstanceOf().getName().equals("MakeBelieve"))
                rval = dao.getActivityMakeBelieveInstanceDAO(activityInstanceId);

            else if(rval!=null && rval.getInstanceOf().getName().equals("WorryHeads"))
                rval = dao.getActivityWorryHeadsInstanceDAO(activityInstanceId);

            else if(rval!=null && rval.getInstanceOf().getName().equals("StandUp"))
                rval = dao.getActivityStandUpInstanceDAO(activityInstanceId);

            else if(rval!=null && rval.getInstanceOf().getName().equals("FaceIt"))
                rval = dao.getActivityFaceInstanceDAO(activityInstanceId);

            return rval;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN HEAL SERVICE getActivityInstance");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance createActivityInstance(ActivityInstance activityInstance) {
       try {
            DAO dao = DAOFactory.getTheDAO();
            // Create one config file to store activity name as per service.            
            if(activityInstance.getInstanceOf().getName().equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
                activityInstance =
                        new MakeBelieveActivityInstance(activityInstance,dao.getMakeBelieveSituation());
            }
            else if(activityInstance.getInstanceOf().getName().equals("WorryHeads")){
                activityInstance = 
                		new WorryHeadsActivityInstance(activityInstance,dao.getWorryHeadsSituation());
            } else if(activityInstance.getInstanceOf().getName().equals("StandUp")){
                activityInstance = 
                		new StandUpActivityInstance(activityInstance,dao.getStandUpSituation());
            } else if(activityInstance.getInstanceOf().getName().equals("DailyDiary")){
                activityInstance = 
                		new DailyDiaryActivityInstance(activityInstance);
            } else if(activityInstance.getInstanceOf().getName().equals("SWAP")){
                activityInstance = 
                		new SwapActivityInstance(activityInstance);
            } else if(activityInstance.getInstanceOf().getName().equals("FaceIt")){
                activityInstance = 
                		new FaceItActivityInstance(activityInstance,dao.getFaceItChallenges());
            }else if(activityInstance.getInstanceOf().getName().equals("Emotion")){
                activityInstance = 
                		new EmotionActivityInstance(activityInstance);
            }

            return service.createActivityInstance(activityInstance);
        } catch (Exception e) {
            System.out.println("SOME ERROR CREATING NE ACTIVITY INSTANCE IN REACH SERVICE - CREATEACTIVITYINSTANCE");
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public ActivityInstance updateActivityInstance(ActivityInstance instance) {
    	return service.updateActivityInstance(instance);
    }

    @Override
    public ActivityInstance deleteActivityInstance(String activityInstanceId) {
    	return service.deleteActivityInstance(activityInstanceId);
    }


    /****************************************  Service methods for Domain  ********************************************/
    @Override
    public List<Domain> getDomains() {
    	return service.getDomains();
    }

    @Override
    public Domain getDomain(String id) {
    	return service.getDomain(id);
    }

    @Override
    public Domain addDomain(String title, String description, String state) {
    	return service.addDomain(title, description, state);
    }

    @Override
    public String addTestDomain(String title, String description, String state) {
    	return service.addTestDomain(title, description, state);
    }

    /****************************************  Service methods for Patient  *******************************************/
    @Override
    public List<Patient> getPatients(String trialId) {
    	return service.getPatients(trialId);
    }

    @Override
    public Patient getPatient(int patientPin) {
    	return service.getPatient(patientPin);
    }

    @Override
    public Patient createPatient(String trialId) {
    	return service.createPatient(trialId);
    }

    @Override
    public Patient updatePatient(Patient patient) {
    	return service.updatePatient(patient);
    }

    @Override
    public String deletePatient(String patientPin) {
    	return service.deletePatient(patientPin);
    }

    /****************************************  Service methods for Trial  *********************************************/

    @Override
    public List<Trial> getTrials(String domain) {
    	return service.getTrials(domain);
    }

    @Override
    public Trial addTrial(Trial trialInstance) {
    	return service.addTrial(trialInstance);
    }

    /****************************************  Service methods for Logger *********************************************/
    @Override
    public Logger[] logMessage (Logger[] loggerInstance) {
    	return service.logMessage(loggerInstance);
    }
    
    // Reach service  methods.
    @Override
    public List<Activity> getSuggestedEmotionsActivities(int patientPin, String emotion, int intensity){
		  try{
			  DAO dao = DAOFactory.getTheDAO();
			  // Task #386
			  MappingInterface mapper = MappingFactory.getTheMapper();
			  String intensityVal = (String)mapper.intensityMappingToDifficultyLevel(intensity);

			  List<Activity> results = dao.getEmotionsActivityInstance(emotion.toLowerCase(), intensityVal);
			  if(results == null)
				  return null;
			  return results;

		  } catch (Exception e){
			  e.printStackTrace();
			  return null;
		  }
	  }
}
