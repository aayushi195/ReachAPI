package edu.asu.heal.reachv3.api.modelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.reachv3.api.models.*;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;

public class ModelFactory {
	private DAO dao;
	private static final String DATE_FORMAT = "MM/dd/yyyy";

	public ModelFactory() throws ModelException {
		try {
			dao = DAOFactory.getTheDAO();
		} catch (Exception de) {
			throw new ModelException("Unable to initialize the DAO", de);
		}
	}

	// ******************************** ACTIVITY INSTANCE *************************************************
	public List<ActivityInstance> getActivityInstances(int patientPin) {
		try {
			return dao.getScheduledActivities(patientPin);
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	public ActivityInstance createActivityInstance(ActivityInstance activityInstance) throws ModelException{
		try {
			if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
			if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
			if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());

			String activityName = dao.getActivityNameById(activityInstance.getActivityId());
			System.out.println("ActivityName : " + activityName);
			ExtendedActivityInstance extendedActivityInstance = new ExtendedActivityInstance();
			extendedActivityInstance.setDomainName("Preventive Anxiety");
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion("v1");

			// Create one config file to store activity name as per service.

			activityInstance = getActitvityInstanceOfType(activityName,activityInstance,extendedActivityInstance,null);

			ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);
			return newActivityInstance;
		}catch (Exception e) {
			throw new ModelException("SOME ERROR CREATING NE ACTIVITY INSTANCE IN MODEL FACTORY - CREATEACTIVITYINSTANCE", e);
		}
	}

	public ActivityInstance updateActivityInstance(String requestBody) throws ModelException{
		try {
			ActivityInstance activityInstance = getActivityInstanceFromJSON(requestBody);
			String activityName = dao.getActivityNameById(activityInstance.getActivityId());
			ExtendedActivityInstance extendedActivityInstance = new ExtendedActivityInstance();
			extendedActivityInstance.setDomainName("Preventive Anxiety");
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion("v1");

			String situationJson = getSituationString(activityInstance.getActivityInstanceId());
			
			if(activityInstance == null) {
				return NullObjects.getNullActivityInstance();
			}

			activityInstance = getActitvityInstanceOfType(activityName,activityInstance,
					extendedActivityInstance,situationJson);
			if(dao.updateActivityInstance(activityInstance)){
				return activityInstance;
			}
			return NullObjects.getNullActivityInstance();
		}catch(Exception e) {
			throw new ModelException("Error from updateActivityInstance() in Model Factory", e);
		}
	}

	public ActivityInstance getActivityInstance(String activityInstanceId) throws ModelException{
		try {
			ActivityInstance activityInstance;
			activityInstance =  dao.getActivityInstance(activityInstanceId);
			String activityName = dao.getActivityNameById(activityInstance.getActivityId());
			ExtendedActivityInstance extendedActivityInstance = new ExtendedActivityInstance();
			extendedActivityInstance.setDomainName("Preventive Anxiety");
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion("v1");
			String situationStr = getSituationString(activityInstanceId);	
			activityInstance = getActitvityInstanceOfType(activityName,activityInstance,extendedActivityInstance,situationStr);
			return activityInstance;
		}catch(Exception e) {
			throw new ModelException("SOME ERROR IN GET ACTIVITY INSTANCE IN MODEL FACTORY",e);
    }
  }
  
	
	public ActivityInstance getActivityInstanceFromJSON(String requestBody) {	

		try {
			JSONObject obj = new JSONObject(requestBody);
			String activityInstanceId = null, activityId = null, description = null, state =null;
			int patientPin = -1;
			Date createdAt = null, startTime = null, endTime = null, userSubmissionTime = null,
					actualSubmissionTime = null;
			Date updatedAt = new Date();

			if(obj.has(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE) 
					&& !obj.getString(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE).equals("null") ) {
				activityInstanceId = obj.getString(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE);
			}
			if(obj.has(ActivityInstance.ACTIVITYID_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.ACTIVITYID_ATTRIBUTE).equals("null")) {
				activityId = obj.getString(ActivityInstance.ACTIVITYID_ATTRIBUTE);
			}
			if(obj.has(ActivityInstance.DESCRIPTION_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.DESCRIPTION_ATTRIBUTE).equals("null")) {
				description = obj.getString(ActivityInstance.DESCRIPTION_ATTRIBUTE);
			}
			if(obj.has(ActivityInstance.STATE_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.STATE_ATTRIBUTE).equals("null")) {
				state = obj.getString(ActivityInstance.STATE_ATTRIBUTE);
			}
			if(obj.has(ActivityInstance.CREATEDAT_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE).equals("null")) {
				createdAt = new Date(Long.parseLong(obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.STARTTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE).equals("null")) {
				startTime = new Date(Long.parseLong(obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.ENDTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE).equals("null")) {
				endTime = new Date(Long.parseLong(obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE).equals("null")) {
				userSubmissionTime = new Date(Long.parseLong(obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE).equals("null")) {
				actualSubmissionTime = new Date(Long.parseLong(obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.PATIENT_PIN)
					&& obj.getInt(ActivityInstance.PATIENT_PIN) != -1) {
				patientPin = obj.getInt(ActivityInstance.PATIENT_PIN);
			}

			return new ActivityInstance(activityInstanceId, activityId, createdAt, updatedAt,
					description, startTime, endTime, userSubmissionTime, actualSubmissionTime, state, patientPin);
		}catch(Exception e) {
			System.out.println("EXCEPTION IN getActivityInstanceFromJSON");
			e.printStackTrace();
			return null;
		}

	}

	public ActivityInstance getActitvityInstanceOfType(String activityName, ActivityInstance activityInstance,
			ExtendedActivityInstance extendedActivityInstance, String situationJson) throws ModelException{

		try{
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			mapper.setDateFormat(format);
			if(activityName.equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
				MakeBelieveSituation situation;
				if(situationJson == null)
					situation = dao.getMakeBelieveSituation();
				else
					situation = mapper.readValue(situationJson, MakeBelieveSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance =new MakeBelieveActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("WorryHeads")){
				WorryHeadsSituation situation;
				if(situationJson == null)
					situation = dao.getWorryHeadsSituation();
				else
					situation = mapper.readValue(situationJson, WorryHeadsSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance = new WorryHeadsActivityInstance(
						activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(), extendedActivityInstance);
			} else if(activityName.equals("StandUp")){
				StandUpSituation situation;
				if(situationJson == null)
					situation = dao.getStandUpSituation();
				else
					situation = mapper.readValue(situationJson, StandUpSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance = new StandUpActivityInstance(
						activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(), extendedActivityInstance);
			} else if(activityName.equals("DailyDiary")){
				DailyDiarySituation situation;
				if(situationJson == null)
					situation = dao.getDailyDiarySituation();
				else
					situation = mapper.readValue(situationJson, DailyDiarySituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance = new DailyDiaryActivityInstance(
						activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(), extendedActivityInstance);
			} else if(activityName.equals("SWAP")){
				SwapSituation situation;
				if(situationJson == null)
					situation = dao.getSwapSituation();
				else
					situation = mapper.readValue(situationJson, SwapSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance = new SwapActivityInstance(
						activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("FaceIt")){
				activityInstance = new FaceItActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),dao.getFaceItChallenges());
			} else if(activityName.equals("Emotions")){
				System.out.println("Inside Emotions");
				EmotionSituation situation;
				if(situationJson == null)
					situation = new EmotionSituation();
				else
					situation = mapper.readValue(situationJson, EmotionSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance = new EmotionActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("Relaxation")){
				activityInstance = new RelaxationActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),extendedActivityInstance);
			}
			return activityInstance;
		}catch(Exception e) {
			throw new ModelException("EXCEPTION in getActitvityInstanceOfType .",e);
		}
	}
  
  	
  	public String getSituationString(String activityInstanceId) throws ModelException {
  		try {
  		String instance = dao.getActivityInstanceAsStringDAO(activityInstanceId);

		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
		mapper.setDateFormat(format);

		JSONObject obj = new JSONObject(instance);
		JSONObject extended = new JSONObject(obj.getString("extended"));

		if(extended.has(ExtendedActivityInstance.SITUATION_ATTRIBUTE))
			return extended.getString("situation");
		else
			return null;
  		}catch(Exception e) {
  			throw new ModelException("SOME ERROR IN GET SITUATION STRING  IN MODEL FACTORY",e);
  		}
  		
  	}
	//************************************ PATIENTS ***********************************************
	public List<Patient> getPatients(String trialId) {
		try {
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
  
  public Patient getPatient(int patientPin) {
		try {
			return dao.getPatient(patientPin);
		} catch (Exception e) {
      e.printStackTrace();
			return null;
		}
	}
  
  public Patient createPatient(String trialId) {
		try {
			return dao.createPatient(trialId);
    } catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
  
  public Patient updatePatient(Patient patient) {
		try {
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

	// ************************************* ACTVITY ****************************************************
	public List<Activity> getActivities(String domain) {
		try {
			List<Activity> result = dao.getActivities(domain);
			return result;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}
	}
	
	public Activity createActivity(String title, String description) {
		try {
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
	
	public Activity getActivity(String activityId) {
		try {
			return dao.getActivity(activityId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Activity updateActivity(Activity activity) {
		try {
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
  
	public Activity deleteActivity(String activityId) {
		try {
			return dao.deleteActivity(activityId);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
			e.printStackTrace();
			return null;
		}
	}

	// ************************************* DOMAIN ****************************************************
	public List<Domain> getDomains() {
		try {
			return dao.getDomains();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Domain getDomain(String id) {
		try {
			return dao.getDomain(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Domain createDomain(String title, String description, String state) {
		try {
			Domain instance = new Domain(title, description, state);
			instance.setCreatedAt(new Date());
			if (instance.getState() == null) instance.setState(DomainState.CREATED.state());

			return dao.createDomain(instance);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ************************************* TRIALS ****************************************************

	public List<Trial> getTrials(String domain) {
		try {
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

	public Trial addTrial(Trial trialInstance) {
		try {
			DAO dao = DAOFactory.getTheDAO();

			Domain domain = dao.getDomain(trialInstance.getDomainId());
			if (domain != null) {

				Date startDateFormat = new SimpleDateFormat(DATE_FORMAT).parse(trialInstance.getStartDate().toString());
				Date endDateFormat = new SimpleDateFormat(DATE_FORMAT).parse(trialInstance.getEndDate().toString());

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
}

