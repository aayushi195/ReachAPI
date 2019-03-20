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

			if(activityName.equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
				MakeBelieveSituation situation = dao.getMakeBelieveSituation();
				extendedActivityInstance.setSituation(situation);
				activityInstance = new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(), 
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(),
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("WorryHeads")){
				WorryHeadsSituation situation = dao.getWorryHeadsSituation();
				extendedActivityInstance.setSituation(situation);
				activityInstance = new WorryHeadsActivityInstance(activityInstance.getActivityInstanceId(), 
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(),
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("StandUp")){
				extendedActivityInstance.setSituation(dao.getStandUpSituation());
				activityInstance = new StandUpActivityInstance(activityInstance.getActivityInstanceId(), 
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(),
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("DailyDiary")){
				extendedActivityInstance.setSituation(dao.getDailyDiarySituation());
				activityInstance = new DailyDiaryActivityInstance(activityInstance.getActivityInstanceId(), 
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(),
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("SWAP")){
				extendedActivityInstance.setSituation(dao.getSwapSituation());
				activityInstance = new SwapActivityInstance(activityInstance.getActivityInstanceId(), 
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(),
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityName.equals("FaceIt")){
				activityInstance = new FaceItActivityInstance(activityInstance.getActivityInstanceId(),
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(), 
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),dao.getFaceItChallenges());
			} else if(activityName.equals("Emotion")){
				activityInstance = new EmotionActivityInstance(activityInstance.getActivityInstanceId(),
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(), 
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin());
			} else if(activityName.equals("Relaxation")){
				activityInstance = new RelaxationActivityInstance(activityInstance.getActivityInstanceId(),
						activityInstance.getActivityId(),activityInstance.getCreatedAt(), 
						activityInstance.getUpdatedAt(),activityInstance.getDescription(), 
						activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),activityInstance.getPatientPin(),extendedActivityInstance);
			}
			ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);
			return newActivityInstance;
		}catch (Exception e) {
			throw new ModelException("SOME ERROR CREATING NE ACTIVITY INSTANCE IN MODEL FACTORY - CREATEACTIVITYINSTANCE", e);
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

			if(activityInstance!=null && activityName.equals("MakeBelieve")) {
				String instance = dao.getActivityMakeBelieveInstanceDAO(activityInstanceId);

				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));
				MakeBelieveSituation situation = mapper.readValue(extended.getString("situation"), MakeBelieveSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance =
						new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);

			} else if(activityInstance!=null && activityName.equals("WorryHeads")) {
				String instance = dao.getActivityWorryHeadsInstanceDAO(activityInstanceId);

				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));

				WorryHeadsSituation situation = mapper.readValue(extended.getString("situation"), WorryHeadsSituation.class);
				extendedActivityInstance.setSituation(situation);

				activityInstance =
						new WorryHeadsActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);

			} else if(activityInstance!=null && activityName.equals("StandUp")) {
				String instance = dao.getActivityStandUpInstanceDAO(activityInstanceId);

				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));

				StandUpSituation situation = mapper.readValue(extended.getString("situation"), StandUpSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance =
						new StandUpActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityInstance!=null && activityName.equals("DailyDiary")) {
				String instance = dao.getActivityDailyDiaryInstanceDAO(activityInstanceId);

				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));

				DailyDiarySituation situation = mapper.readValue(extended.getString("situation"), DailyDiarySituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance =
						new DailyDiaryActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityInstance!=null && activityName.equals("Relaxation")) {
				activityInstance =
						new DailyDiaryActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);
			} else if(activityInstance!=null && activityName.equals("SWAP")) {
				String instance = dao.getActivitySwapInstanceDAO(activityInstanceId);

				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));

				SwapSituation situation = mapper.readValue(extended.getString("situation"), SwapSituation.class);
				extendedActivityInstance.setSituation(situation);
				activityInstance =
						new DailyDiaryActivityInstance(activityInstance.getActivityInstanceId(), activityInstance.getActivityId(),
								activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
								activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
								activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
								activityInstance.getState(),
								activityInstance.getPatientPin(),extendedActivityInstance);
			}
			//			else if(rval!=null && activityName.equals("FaceIt"))
			//				rval = dao.getActivityFaceInstanceDAO(activityInstanceId);

			return activityInstance;
		}catch(Exception e) {
			throw new ModelException("SOME ERROR IN HEAL SERVICE MODEL FACTORY",e);
		}
	}
}

