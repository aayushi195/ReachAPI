package edu.asu.heal.reachv3.api.service;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.modelFactory.ModelFactory;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleActivityList;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleInstance;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsInstance;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReachService implements HealService {

	private ModelFactory __modelFactory =null;

	public ReachService() {
		try {
			__modelFactory = new ModelFactory();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  private static final String DATE_FORMAT = "MM/dd/yyyy";
	/****************************************  Service methods for Activity  ******************************************/
	@Override
	public List<Activity> getActivities(String domain) {
		try {
			return  __modelFactory.getActivities(domain);
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity createActivity(String title, String description) {
		try {
			return __modelFactory.createActivity(title,description);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE - CREATEACTIVITY");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity getActivity(String activityId) {
		try {
			return __modelFactory.getActivity(activityId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity updateActivity(Activity activity) {
		try {
			return __modelFactory.updateActivity(activity);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN UPDATE ACTIVITY IN REACHSERVICE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity deleteActivity(String activityId) {
		try {
			return __modelFactory.deleteActivity(activityId);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for ActivityInstance  **********************************/
	@Override
	public List<ActivityInstance> getActivityInstances(int patientPin) {
		try {
			return __modelFactory.getActivityInstances(patientPin);
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
			ActivityInstance activityInstance=__modelFactory.getActivityInstance(activityInstanceId);
			return activityInstance;
		} catch (Exception e) {
			System.out.println("SOME ERROR IN HEAL SERVICE getActivityInstance");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance createActivityInstance(ActivityInstance activityInstance) {
		try {
			ActivityInstance newActivityInstance = __modelFactory.createActivityInstance(activityInstance);
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
			ActivityInstance activityInstance = __modelFactory.updateActivityInstance(requestBody);
			return activityInstance;
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
			return __modelFactory.getDomains();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Domain getDomain(String id) {
		try {
			return __modelFactory.getDomain(id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Domain addDomain(String title, String description, String state) {

		try {
			return __modelFactory.createDomain(title,description,state);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for Patient  *******************************************/
	@Override
	public List<Patient> getPatients(String trialId) {
		try {
			return __modelFactory.getPatients(trialId);
		} catch (Exception e) {
			System.out.println("SOME PROBLEM WITH REACH SERVICE - GET PATIENTS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient getPatient(int patientPin) {
		try {
			return __modelFactory.getPatient(patientPin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient createPatient(String trialId) {
		try {
			return __modelFactory.createPatient(trialId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient updatePatient(Patient patient) {
		try {
			return __modelFactory.updatePatient(patient);
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

	public RewardsInstance getPatientRewards(int patientPin){
		try{
			return __modelFactory.getPatientRewards(patientPin);
		} catch (Exception e){
			System.out.println("SOME ERROR IN GETPATIENTREWARDS IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}

	}

	/****************************************  Service methods for Trial  *********************************************/

	@Override
	public List<Trial> getTrials(String domain) {
		try {
			return __modelFactory.getTrials(domain);
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETTRIALS() IN REACHSERVICE CLASS");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Trial addTrial(Trial trialInstance) {
		try {
			return __modelFactory.addTrial(trialInstance);
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
	
	/******************************** Schedule Methods **********************************************************/

	@Override
	public PatientSchedule createPatientSchedule(int patientPin) {
		try {
			return __modelFactory.createPatientSchedule(patientPin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PatientSchedule getPatientSchedule(int patientPin) {
		try {
			return __modelFactory.getPatientSchedule(patientPin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public PatientSchedule updatePatientSchedule(int patientPin, String module) {
		try {
			return __modelFactory.updatePatientSchedule(patientPin, module);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Service methods for Module *********************************************/

	public ModuleInstance getScheduleOfModules(int patientPin){

		try{
			return __modelFactory.getScheduleOfModules(patientPin);
		} catch (Exception e){
			System.out.println("SOME PROBLEM IN GETTING THE SCHEDULE OF MODULES");
      e.printStackTrace();
			return null;
		}
	}
	
	public ModuleActivityList getActivityListWithCallToAction(String module, int patientPin) {
		try{
			return __modelFactory.getActivityListWithCallToAction(module,patientPin);
		} catch (Exception e){
			System.out.println("SOME PROBLEM IN GETTING THE SCHEDULE OF MODULES");
      e.printStackTrace();
			return null;
		}
	}


	/****************************************  Other Service methods *********************************************/

	public HashMap<String, Integer> getModuleAndDay(PatientSchedule patientSchedule, Date today) {

		try{
			return __modelFactory.getModuleAndDay(patientSchedule,today);
		} catch (Exception e){
			System.out.println("SOME PROBLEM IN GETTING THE SCHEDULE OF MODULES");
			e.printStackTrace();
			return null;
		}
	}

}
