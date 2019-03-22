package edu.asu.heal.reachv3.api.modelFactory;

import java.util.Date;
import java.util.List;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.ActivityInstanceStatus;
import edu.asu.heal.core.api.models.NullObjects;
import edu.asu.heal.reachv3.api.models.DailyDiaryActivityInstance;
import edu.asu.heal.reachv3.api.models.EmotionActivityInstance;
import edu.asu.heal.reachv3.api.models.ExtendedActivityInstance;
import edu.asu.heal.reachv3.api.models.FaceItActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.RelaxationActivityInstance;
import edu.asu.heal.reachv3.api.models.StandUpActivityInstance;
import edu.asu.heal.reachv3.api.models.SwapActivityInstance;
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

}

