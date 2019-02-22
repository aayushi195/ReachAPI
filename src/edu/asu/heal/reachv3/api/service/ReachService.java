package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.models.*;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReachService implements HealService {

    private static final String DATE_FORMAT = "MM/dd/yyyy";

    /****************************************  Service methods for Activity  ******************************************/
    @Override
    public List<Activity> getActivities(String domain) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Activity> result = dao.getActivities(domain);

            return result;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity createActivity(String title, String description) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Activity newActivity = new Activity();
            newActivity.setTitle(title);
            newActivity.setDescription(description);
            newActivity.setUpdatedAt(new Date());
            newActivity.setCreatedAt(new Date());
            Activity createdActivity = dao.createActivity(newActivity);

            return createdActivity;
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE - CREATEACTIVITY");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity getActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getActivity(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity updateActivity(Activity activity) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Activity activityInDatabase = dao.getActivity(activity.getActivityId());
            if (activityInDatabase == null || activityInDatabase.equals(NullObjects.getNullActivity()))
                return activityInDatabase;

            activityInDatabase.setTitle(
                    activity.getTitle() != null ? activity.getTitle() : activityInDatabase.getTitle());
            activityInDatabase.setDescription(
                    activity.getDescription() != null ? activity.getDescription() : activityInDatabase.getDescription());
            activityInDatabase.setUpdatedAt(new Date());

            return dao.updateActivity(activityInDatabase);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN UPDATE ACTIVITY IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity deleteActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.deleteActivity(activityId);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
            e.printStackTrace();
            return null;
        }
    }

    /****************************************  Service methods for ActivityInstance  **********************************/
    @Override
    public List<ActivityInstance> getActivityInstances(int patientPin) {
        List<ActivityInstance> response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<ActivityInstance> instances = dao.getScheduledActivities(patientPin);

            return instances;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    public List<Activity> getEmotionsActivityInstance(int patientPin, String emotion, int intensity){
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
  
    @Override
    public ActivityInstance getActivityInstance(String activityInstanceId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            ActivityInstance rval;
            rval =  dao.getActivityInstance(activityInstanceId);

//            if(rval!=null && rval.getInstanceOf().getName().equals("MakeBelieve"))
//                rval = dao.getActivityMakeBelieveInstanceDAO(activityInstanceId);
//
//            else if(rval!=null && rval.getInstanceOf().getName().equals("WorryHeads"))
//                rval = dao.getActivityWorryHeadsInstanceDAO(activityInstanceId);
//
//            else if(rval!=null && rval.getInstanceOf().getName().equals("StandUp"))
//                rval = dao.getActivityStandUpInstanceDAO(activityInstanceId);
//
//            else if(rval!=null && rval.getInstanceOf().getName().equals("FaceIt"))
//                rval = dao.getActivityFaceInstanceDAO(activityInstanceId);

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
            if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
            if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
            if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());
//            if (activityInstance.getInstanceOf().getActivityId() == null) {
//                activityInstance.getInstanceOf()
//                        .setActivityId(dao.getActivityId(activityInstance.getInstanceOf().getName()));
//            }
            String activityName = dao.getActivityNameById(activityInstance.getActivityId());

            // Create one config file to store activity name as per service.
            
            if(activityName.equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
                activityInstance =
                        new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                                activityInstance.getState(),
                        activityInstance.getPatientPin(), dao.getMakeBelieveSituation());
            } else if(activityName.equals("WorryHeads")){
                activityInstance = new WorryHeadsActivityInstance(
                        activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin(), dao.getWorryHeadsSituation());
            } else if(activityName.equals("StandUp")){
                activityInstance = new StandUpActivityInstance(
                        activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin(), dao.getStandUpSituation());
            } else if(activityName.equals("DailyDiary")){
                activityInstance = new DailyDiaryActivityInstance(
                        activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin());
            } else if(activityName.equals("SWAP")){
                activityInstance = new SwapActivityInstance(
                        activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin());
            } else if(activityName.equals("FaceIt")){
                activityInstance = new FaceItActivityInstance(
                        activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin(),dao.getFaceItChallenges());
            }else if(activityName.equals("Emotion")){
                activityInstance = new EmotionActivityInstance(
                        activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getState(),
                        activityInstance.getPatientPin());
            }

            ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);
            return newActivityInstance;
        } catch (Exception e) {
            System.out.println("SOME ERROR CREATING NE ACTIVITY INSTANCE IN REACH SERVICE - CREATEACTIVITYINSTANCE");
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public ActivityInstance updateActivityInstance(String requestBody) {
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
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.deleteActivityInstance(activityInstanceId);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
            e.printStackTrace();
            return null;
        }
    }


    /****************************************  Service methods for Domain  ********************************************/
    @Override
    public List<Domain> getDomains() {
        try {
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomains();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain getDomain(String id) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomain(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain addDomain(String title, String description, String state) {

        try {
            DAO dao = DAOFactory.getTheDAO();
            Domain instance = new Domain(title, description, state);
            instance.setCreatedAt(new Date());
            if (instance.getState() == null) instance.setState(DomainState.CREATED.state());

            return dao.createDomain(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addTestDomain(String title, String description, String state) {
        return null;
    }

    /****************************************  Service methods for Patient  *******************************************/
    @Override
    public List<Patient> getPatients(String trialId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Patient> result;

            if (trialId == null) {
                // return list of all patients present
                result = dao.getPatients();
            } else {
                // return list of patients for given trialId
                result = dao.getPatients(trialId);
            }

            return result;
        } catch (Exception e) {
            System.out.println("SOME PROBLEM WITH REACH SERVICE - GET PATIENTS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient getPatient(int patientPin) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getPatient(patientPin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient createPatient(String trialId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.createPatient(trialId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient updatePatient(Patient patient) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            Patient patientInDatabase = dao.getPatient(patient.getPin());
            if (patientInDatabase == null || patientInDatabase.equals(NullObjects.getNullPatient()))
                return patientInDatabase;

            patientInDatabase.setStartDate(
                    patient.getStartDate() != null ? patient.getStartDate() : patientInDatabase.getStartDate());
            patientInDatabase.setEndDate(
                    patient.getEndDate() != null ? patient.getEndDate() : patientInDatabase.getEndDate());
            patientInDatabase.setState(
                    patient.getState() != null ? patient.getState() : patientInDatabase.getState());
            patientInDatabase.setCreatedAt(
                    patient.getCreatedAt() != null ? patient.getCreatedAt() : patientInDatabase.getCreatedAt());
            patientInDatabase.setUpdatedAt(new Date());

            return dao.updatePatient(patientInDatabase);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN UPDATE PATIENT IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String deletePatient(String patientPin) {
        return "DELETE PATIENT";
    }

    /****************************************  Service methods for Trial  *********************************************/

    @Override
    public List<Trial> getTrials(String domain) {
        HEALResponse response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Trial> trials = null;

            if (domain == null)
                trials = dao.getTrials();
            else
                trials = dao.getTrials(domain);

            return trials;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETTRIALS() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Trial addTrial(Trial trialInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            // check if the domain exist, if yes get the id of domain
            Domain domain = dao.getDomain(trialInstance.getDomainId());
            if (domain != null) {

                Date startDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getStartDate().toString());
                Date endDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getEndDate().toString());

                trialInstance.setUpdatedAt(new Date());
                trialInstance.setCreatedAt(new Date());
                trialInstance.setStartDate(startDateFormat);
                trialInstance.setEndDate(endDateFormat);
                trialInstance.setDomainId(domain.getDomainId());

                return dao.createTrial(trialInstance);
            } else {
                return NullObjects.getNullTrial();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /****************************************  Service methods for Logger *********************************************/
    @Override
    public Logger[] logMessage (Logger[] loggerInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            Logger[] logger = dao.logMessage(loggerInstance);
            return logger;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
