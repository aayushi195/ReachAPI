package edu.asu.heal.reachv3.api.modelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceStatus;
import edu.asu.heal.core.api.models.NullObjects;
import edu.asu.heal.reachv3.api.models.DailyDiaryActivityInstance;
import edu.asu.heal.reachv3.api.models.DailyDiarySituation;
import edu.asu.heal.reachv3.api.models.EmotionActivityInstance;
import edu.asu.heal.reachv3.api.models.ExtendedActivityInstance;
import edu.asu.heal.reachv3.api.models.FaceItActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.RelaxationActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpSituation;
import edu.asu.heal.reachv3.api.models.SwapActivityInstance;
import edu.asu.heal.reachv3.api.models.SwapSituation;
import edu.asu.heal.reachv3.api.models.WorryHeadsActivityInstance;
import edu.asu.heal.reachv3.api.models.WorryHeadsSituation;

public class ModelFactory {
	private DAO dao = null;

	public ModelFactory() throws ModelException {
		try {
			dao = DAOFactory.getTheDAO();
		} catch (Exception de) {
			throw new ModelException("Unable to initialize the DAO", de);
		}
	}

	public ActivityInstance createActivityInstance(ActivityInstance activityInstance) throws ModelException{
		try {
			if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
			if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
			if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());

			String activityName = dao.getActivityNameById(activityInstance.getActivityId());
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
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
			mapper.setDateFormat(format);

			JsonNode activityInstanceAsTree = mapper.readTree(requestBody);
			String activityInstanceType = activityInstanceAsTree.get("activityId").asText();

			String activityName = dao.getActivityNameById(activityInstanceType);

			JSONObject obj = new JSONObject(requestBody);
			JSONObject extended = new JSONObject(obj.getString("extended"));
			ExtendedActivityInstance extendedActivityInstance = new ExtendedActivityInstance();
			extendedActivityInstance.setDomainName(extended.getString(ExtendedActivityInstance.DOMAIN_NAME));
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion(extended.getString(ExtendedActivityInstance.VERSION));

			String situationJson = extended.getString(ExtendedActivityInstance.SITUATION_ATTRIBUTE);

			ActivityInstance activityInstance = getActivityInstanceFromJSON(requestBody);
			
			if(activityInstance == null) {
				return NullObjects.getNullActivityInstance();
			}

			activityInstance = getActitvityInstanceOfType(activityName,activityInstance,extendedActivityInstance,situationJson);
			if(dao.updateActivityInstance(activityInstance)){
				return activityInstance;
			}
			return NullObjects.getNullActivityInstance();
		}catch(Exception e) {
			throw new ModelException("Error from updateActivityInstance() in Model Factory", e);
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
			} else if(activityName.equals("Emotion")){
				activityInstance = new EmotionActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin());
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

}

