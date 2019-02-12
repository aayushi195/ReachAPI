package edu.asu.heal.reachv3.api.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.service.AHealService;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.models.*;


public class ReachServiceImpl extends AHealService implements IReachService {

   // private static HealService service = AHealService.getTheService();
    
    public ReachServiceImpl(HealService impl) {
		super(impl);
	}
    
 
    /****************************************  Service methods for Activity  ******************************************/
    
    @Override
    public List<Activity> getActivities(String domain) {
    	return super.getActivities(domain);
    }

    @Override
    public Activity createActivity(String title, String description) {
    	return super.createActivity(title, description);
    }

    @Override
    public Activity getActivity(String activityId) {
    	return super.getActivity(activityId);
    }

    @Override
    public Activity updateActivity(Activity activity) {
    	return super.updateActivity(activity);
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
    public ActivityInstance updateActivityInstance(String requestBody) {
//    	return service.updateActivityInstance(instance);
        try {

            DAO dao = DAOFactory.getTheDAO();

            ObjectMapper mapper = new ObjectMapper();
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            mapper.setDateFormat(format);

            JsonNode activityInstanceAsTree = mapper.readTree(requestBody);
            String activityInstanceType = activityInstanceAsTree.get("instanceOf").get("name").asText();

            ActivityInstance instance;
            if (activityInstanceType.equals("MakeBelieve")) { // todo Need to find a more elegant way to do this
                instance = mapper.readValue(requestBody, MakeBelieveActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else if (activityInstanceType.equals("WorryHeads")) {
                instance = mapper.readValue(requestBody, WorryHeadsActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else if (activityInstanceType.equals("DailyDiary")) {
                instance = mapper.readValue(requestBody, DailyDiaryActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else if (activityInstanceType.equals("SWAP")) {
                instance = mapper.readValue(requestBody, SwapActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else if (activityInstanceType.equals("StandUp")) {
                instance = mapper.readValue(requestBody, StandUpActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else if (activityInstanceType.equals("FaceIt")) {
                instance = mapper.readValue(requestBody, FaceItActivityInstance.class);
                instance.setUpdatedAt(new Date());
            }else if (activityInstanceType.equals("Emotion")) {
                instance = mapper.readValue(requestBody, EmotionActivityInstance.class);
                instance.setUpdatedAt(new Date());
            } else{
                instance  = mapper.readValue(requestBody, ActivityInstance.class);
                instance.setUpdatedAt(new Date());
            }
            if(dao.updateActivityInstance(instance)){
                return instance;
            }
            return NullObjects.getNullActivityInstance();
        } catch (NullPointerException ne){
            return NullObjects.getNullActivityInstance();
        }catch (Exception e) {
            System.out.println("Error from updateActivityInstance() in ReachService");
            e.printStackTrace();
            return null;
        }
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
