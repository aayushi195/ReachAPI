package edu.asu.heal.reachv3.api.modelFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceStatus;
import edu.asu.heal.core.api.models.NullObjects;
import edu.asu.heal.core.api.models.Patient;
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
}

