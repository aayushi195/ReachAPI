package edu.asu.heal.reachv3.api.modelFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.ActivityScheduleDetail;
import edu.asu.heal.core.api.models.schedule.ActivityScoreDetail;
import edu.asu.heal.core.api.models.schedule.DayDetail;
import edu.asu.heal.core.api.models.schedule.ModuleDetail;
import edu.asu.heal.core.api.models.schedule.ModuleScoreDetail;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.core.api.models.schedule.PatientScoreDetail;
import edu.asu.heal.reachv3.api.models.*;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleAcivityDetail;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleActivityList;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleBasedInstance;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleInstance;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsBasedInstance;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsInstance;
import org.json.JSONArray;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;

public class ModelFactory {
	private DAO dao;
	private static final String DATE_FORMAT = "MM/dd/yyyy";
	private static final String DATE_FORMAT_AI="MMM dd, yyyy HH:mm:ss";
	private static Properties _properties;
	private static String MAKEBELIEVE_ACTIVITYNAME,EMOTIONS_ACTIVITYNAME,SWAP_ACTIVITYNAME,
	STANDUP_ACTIVITYNAME, FACEIT_ACTIVITYNAME, RELAXATION_ACTIVITYNAME, WORRYHEADS_ACTIVITYNAME,
	DAILYDIARY_ACTIVITYNAME,WRAPUP_ACTIVITYNAME, VERSION, DOMAIN_NAME, EXTENDED_PART;
	private static String MODULE_SCHEDULE_FILE= "module.schedule";
	private static String MODULE_DURATION_DAYS= "module.duration.days";
	private static String TOTAL_MODULE= "total.modules";
	private static String TOTAL_SKILLS = "total.skills";
	private static String MODULE="module";
	private static String DAY="day";
	private static String INFORMATION_MODULE_CONTENT= "introduction.module.";
	private static String SKILL_NAME = "skill.";

	static {
		_properties = new Properties();
		try {
			InputStream propFile = ModelFactory.class.getResourceAsStream("activityName.properties");
			_properties.load(propFile);
			propFile.close();

			MAKEBELIEVE_ACTIVITYNAME = _properties.getProperty("makeBelieve.name");
			EMOTIONS_ACTIVITYNAME = _properties.getProperty("emotions.name");
			SWAP_ACTIVITYNAME = _properties.getProperty("swap.name");
			STANDUP_ACTIVITYNAME = _properties.getProperty("standUp.name");
			FACEIT_ACTIVITYNAME = _properties.getProperty("faceIt.name");
			RELAXATION_ACTIVITYNAME = _properties.getProperty("relaxation.name");
			WORRYHEADS_ACTIVITYNAME = _properties.getProperty("worryHeads.name");
			DAILYDIARY_ACTIVITYNAME = _properties.getProperty("dailyDiary.name");
			WRAPUP_ACTIVITYNAME = _properties.getProperty("wrapup.name");
			
			VERSION = _properties.getProperty("version.name");
			DOMAIN_NAME = _properties.getProperty("domain.name");
			EXTENDED_PART = _properties.getProperty("extended.name");
			

		} catch (Throwable t) {
			t.printStackTrace();
			try {
				throw new Exception(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

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
			extendedActivityInstance.setDomainName(DOMAIN_NAME);
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion(VERSION);

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
			extendedActivityInstance.setDomainName(DOMAIN_NAME);
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion(VERSION);

			String situationJson = getSituationString(null,requestBody);

			if(activityInstance == null) {
				return NullObjects.getNullActivityInstance();
			}

			activityInstance = getActitvityInstanceOfType(activityName,activityInstance,
					extendedActivityInstance,situationJson);

			if(activityInstance.getState().equals(ActivityInstanceStatus.COMPLETED.status())) {
				if(updatePatientScoreDetail(activityInstance.getPatientPin(), activityInstance.getActivityInstanceId(),
						activityInstance.getActivityId(), activityName)) {
				}else {
					System.out.println(" FAILURE !!!!!!! : Score date is NOT updated for pin : " + activityInstance.getPatientPin());
				}
			}

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
			//	ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_AI);
			//	mapper.setDateFormat(format);

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
				if(isLong(obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE))) 
					createdAt = new Date(Long.parseLong(obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE)));
				else
					createdAt = format.parse(obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE));
				//	createdAt = new Date(Long.parseLong(obj.getString(ActivityInstance.CREATEDAT_ATTRIBUTE)));
			}
			if(obj.has(ActivityInstance.STARTTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE).equals("null")) {
				if(isLong(obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE)))
					startTime = new Date(Long.parseLong(obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE)));
				else
					startTime = format.parse(obj.getString(ActivityInstance.STARTTIME_ATTRIBUTE));
			}
			if(obj.has(ActivityInstance.ENDTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE).equals("null")) {
				if(isLong(obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE)))
					endTime = new Date(Long.parseLong(obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE)));
				else
					endTime = format.parse(obj.getString(ActivityInstance.ENDTIME_ATTRIBUTE));
			}
			if(obj.has(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE).equals("null")) {
				if(isLong(obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE)))
					userSubmissionTime = new Date(Long.parseLong(obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE)));
				else
					userSubmissionTime = format.parse(obj.getString(ActivityInstance.USERSUBMISSIONTIME_ATTRIBUTE));

			}
			if(obj.has(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE)
					&& !obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE).equals("null")) {
				if(isLong(obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE)))
					actualSubmissionTime = new Date(Long.parseLong(obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE)));
				else
					actualSubmissionTime = format.parse(obj.getString(ActivityInstance.ACTUALSUBMISSIONTIME_ATTRIBUTE));
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

	public ActivityInstance getActitvityInstanceOfType(String activityName, ActivityInstance activityInstance, ExtendedActivityInstance extendedActivityInstance, String situationJson) throws ModelException{

		try{
			ObjectMapper mapper = new ObjectMapper();
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_AI);
			mapper.setDateFormat(format);
			if(activityName.equals(MAKEBELIEVE_ACTIVITYNAME)){

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
			} else if(activityName.equals(WORRYHEADS_ACTIVITYNAME)){
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
			} else if(activityName.equals(STANDUP_ACTIVITYNAME)){
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
			} else if(activityName.equals(DAILYDIARY_ACTIVITYNAME)){
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
			} else if(activityName.equals(SWAP_ACTIVITYNAME)){
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
			} else if(activityName.equals(FACEIT_ACTIVITYNAME)){
				activityInstance = new FaceItActivityInstance(
						activityInstance.getActivityInstanceId(),activityInstance.getActivityId(),
						activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
						activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
						activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
						activityInstance.getState(),
						activityInstance.getPatientPin(),dao.getFaceItChallenges());
			} else if(activityName.equals(EMOTIONS_ACTIVITYNAME)){
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
			} else if(activityName.equals(RELAXATION_ACTIVITYNAME)){
				extendedActivityInstance.setSituation(null);
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
			throw new ModelException("EXCEPTION in getActitvityInstanceOfType.",e);
		}
	}

	public String getSituationString(String activityInstanceId,String requestBody) throws ModelException {
		try {
			String instance = null;
			if(activityInstanceId!=null){
				instance = dao.getActivityInstanceAsStringDAO(activityInstanceId);
			}else if(requestBody != null){
				instance = requestBody;
			}
			if(instance != null) {
				ObjectMapper mapper = new ObjectMapper();
				SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT_AI);
				mapper.setDateFormat(format);

				JSONObject obj = new JSONObject(instance);
				JSONObject extended = new JSONObject(obj.getString("extended"));

				if (extended.has(ExtendedActivityInstance.SITUATION_ATTRIBUTE))
					return extended.getString(ExtendedActivityInstance.SITUATION_ATTRIBUTE);
				else
					return null;
			}else
				return null;
		} catch(Exception e) {
			throw new ModelException("SOME ERROR IN GET SITUATION STRING  IN MODEL FACTORY",e);
		}
	}

	public ActivityInstance getActivityInstance(String activityInstanceId) throws ModelException {
		try {
			ActivityInstance activityInstance;
			activityInstance = dao.getActivityInstance(activityInstanceId);

			String activityName = dao.getActivityNameById(activityInstance.getActivityId());

			ExtendedActivityInstance extendedActivityInstance = new ExtendedActivityInstance();
			extendedActivityInstance.setDomainName(DOMAIN_NAME);
			extendedActivityInstance.setActivityTypeName(activityName);
			extendedActivityInstance.setVersion(VERSION);

			String situationStr = getSituationString(activityInstanceId,null);
			activityInstance = getActitvityInstanceOfType(activityName, activityInstance, extendedActivityInstance, situationStr);
			return activityInstance;

		} catch (Exception e) {
			throw new ModelException("SOME ERROR IN GET ACTIVITY INSTANCE IN MODEL FACTORY", e);
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

	public RewardsInstance getPatientRewards(int patientPin){

		RewardsInstance rewardsInstance = new RewardsInstance();
		List<RewardsBasedInstance> rewardsBasedInstances = new ArrayList<>();

		int totalSkills = Integer.parseInt(_properties.getProperty(TOTAL_SKILLS));
		try{
			rewardsInstance.setPatientPin(patientPin);
			PatientScoreDetail patientScoreDetail = dao.getPatientScoreDetail(patientPin);
			if(patientScoreDetail==null)
				return null;
			PatientSchedule patientSchedule = dao.getPatientSchedule(patientPin);
			if(patientSchedule==null)
				return null;
			int rewardsLevel;
			Integer currentModule = -1;
			HashMap<String,Integer> map = getModuleAndDay(patientSchedule,new Date());
			if(map != null && map.size() > 0) {
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					currentModule=map.get(this.MODULE);
			}
			HashMap<String,Boolean> skillSetMap = new HashMap<>();

			for(Integer counter = currentModule; counter>0;counter--){
				//Starts from current module and goes till the first one
				List<ModuleScoreDetail> moduleScoreDetails = patientScoreDetail.getScoreData();

				ModuleScoreDetail moduleScoreDetail = getModuleScoreDetail(moduleScoreDetails,counter);

				if(moduleScoreDetail == null)
					continue;

				for(int i=0;i<totalSkills;i++){
					//Loops to check the skills if present in that module
					RewardsBasedInstance rewardsBasedInstance = new RewardsBasedInstance();
					String propertySkillName = _properties.getProperty(SKILL_NAME+(i+1));

					int activitylength = moduleScoreDetail.getActivityScores().size();
					for(int j=0;j<activitylength;j++){
						String skillName =
								moduleScoreDetail.getActivityScores().get(j).getActivityName();
						float score = moduleScoreDetail.getActivityScores().get(j).getScore();

						if(skillName.equals(propertySkillName) && !skillSetMap.getOrDefault(skillName,false)){
							skillSetMap.put(skillName,true);
							rewardsBasedInstance.setSkill(propertySkillName);
							rewardsLevel = getLevelOfRewards(score);
							rewardsBasedInstance.setLevel(rewardsLevel);
							rewardsBasedInstances.add(rewardsBasedInstance);
						}

					}
				}
			}
			rewardsInstance.setRewards(rewardsBasedInstances);
			if(rewardsInstance!=null)
				dao.createPatientRewards(rewardsInstance,patientPin);
			return rewardsInstance;

		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public ModuleScoreDetail getModuleScoreDetail(List<ModuleScoreDetail> moduleScoreDetails, Integer count){

		return moduleScoreDetails.stream()
				.filter(x -> (count.toString()).equals(x.getModule()))
				.findAny()
				.orElse(null);
	}

	// ************************************* ACTIVITY ****************************************************
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
				trialInstance.setUpdatedAt(new Date());
				trialInstance.setCreatedAt(new Date());
				if(trialInstance.getStartDate() == null)
					trialInstance.setStartDate(new Date());
				if(trialInstance.getEndDate() == null)
					trialInstance.setEndDate(new Date());
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

	// ************************************* SCHEDULES ****************************************************

	public PatientSchedule createPatientSchedule(int patientPin) {
		PatientSchedule result = new PatientSchedule();
		List<ModuleDetail> moduleDetails = new ArrayList<>();
		HashMap<String,List<String>> dateMap;
		try {
			result.setPatientPin(patientPin);
			ObjectMapper mapper = new ObjectMapper();
			String moduleScheduleFileName = _properties.getProperty(MODULE_SCHEDULE_FILE);
			dateMap = this.calculateDefaultModuleDates();
			if(moduleScheduleFileName != null) {
				String fileData = this.readFile(moduleScheduleFileName);
				if(fileData == null || fileData.equals(""))
					return null;
				JSONObject scheduleJSON = new JSONObject(fileData);
				JSONArray moduleJSON = null;
				if(scheduleJSON.has("patientSchedule"))
					moduleJSON = scheduleJSON.getJSONArray("patientSchedule");
				if(moduleJSON == null)
					return null;

				//	moduleDetails = mapper.readValue(moduleJSON.toString(),
				//			new TypeReference<List<ModuleDetail>>(){});

				for(int i=0; i< moduleJSON.length(); i++) {
					JSONObject module = moduleJSON.getJSONObject(i);
					ModuleDetail obj = mapper.readValue(module.toString(), ModuleDetail.class);
					// add start and end date for each module starting from today.
					List<String> dateList = new ArrayList<>();
					dateList=dateMap.get(obj.getModule());
					obj.setStartDate(new Date(dateList.get(0)));
					obj.setEndDate(new Date(dateList.get(1)));
					moduleDetails.add(obj);
				}
				result.setPatientSchedule(moduleDetails);
			}
			return dao.createPatientSchedule(result);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PatientSchedule getPatientSchedule(int patientPin) {
		try {
			return dao.getPatientSchedule(patientPin);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public PatientSchedule updatePatientSchedule(int patientPin, String module) {
		PatientSchedule patientSchedule = null;
		try {
			int moduleDays = Integer.parseInt(_properties.getProperty(MODULE_DURATION_DAYS));
			patientSchedule = dao.getPatientSchedule(patientPin);
			if(patientSchedule == null)
				return null; // can be null object..
			List<ModuleDetail> moduleDetails =patientSchedule.getPatientSchedule();
			int moduleNumber = Integer.parseInt(module) -1;
			Date startDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DATE, -1);
			Date prevDay = cal.getTime();
			cal.setTime(startDate);
			for(int i=0;i<moduleDetails.size(); i++) {
				if(i<moduleNumber) {
					moduleDetails.get(i).setStartDate(prevDay);
					moduleDetails.get(i).setEndDate(prevDay);
				}else {
					moduleDetails.get(i).setStartDate(startDate);
					cal.add(Calendar.DATE, moduleDays-1);
					moduleDetails.get(i).setEndDate(cal.getTime());
					cal.add(Calendar.DATE, 1);
					startDate = cal.getTime();
				}
			}
			return dao.updatePatientSchedule(patientSchedule);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	//************************************ MODULES ***********************************************

	public ModuleInstance getScheduleOfModules(int patientPin){

		ModuleInstance result = new ModuleInstance();
		List<ModuleBasedInstance> moduleBasedInstanceList = new ArrayList<>();
		try{
			result.setPatientPin(patientPin);
			PatientSchedule patientSchedule = getPatientSchedule(patientPin);

			if(patientSchedule==null)
				return null;
			HashMap<String, Integer> map = getModuleAndDay(patientSchedule, new Date());
			Integer currentModule =-1, dayOfModule =-1,moduleIndex=-1;
			if(map != null && map.size() > 0) {
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule=map.get(this.DAY);
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					currentModule=map.get(this.MODULE);
			}
			if(currentModule == -1)
				return null;
			int totalModules = Integer.parseInt(_properties.getProperty(TOTAL_MODULE));
			for(int counter = 1;counter<=totalModules;counter++){
				ModuleBasedInstance moduleBasedInstance = new ModuleBasedInstance();
				moduleBasedInstance.setModule(String.valueOf(counter));
				moduleBasedInstance.setInformationContent(_properties.getProperty(INFORMATION_MODULE_CONTENT
						+ String.valueOf(counter)));
				if(counter<=currentModule)
					moduleBasedInstance.setActive(true);
				else
					moduleBasedInstance.setActive(false);

				moduleBasedInstanceList.add(moduleBasedInstance);
			}

			result.setModuleProgression(moduleBasedInstanceList);
			return result;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}

	public ModuleActivityList getActivityListWithCallToAction(String module, int patientPin) {
		ModuleActivityList result = new ModuleActivityList();

		try {
			result.setModule(module);
			result.setPatientPin(patientPin);
			List<ModuleAcivityDetail> moduleAcivityDetails = new ArrayList<>();
			PatientSchedule patientSchedule = dao.getPatientSchedule(patientPin);
			PatientScoreDetail patientScoreDetail = dao.getPatientScoreDetail(patientPin);
			if(patientSchedule == null)
				return null;
			HashMap<String, Integer> map = getModuleAndDay(patientSchedule, new Date());
			Integer mod =-1, dayOfModule =-1,moduleIndex=-1;
			if(map != null && map.size() > 0) {
				if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
					dayOfModule=map.get(this.DAY);
				if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
					mod=map.get(this.MODULE);
			}
			if(patientScoreDetail == null)
				patientScoreDetail=createModuleScoreDetail(patientPin, null, null, patientSchedule, 
						patientScoreDetail, Integer.valueOf(module), dayOfModule);

			Integer currModule = Integer.valueOf(module)-1;
			if(currModule == -1)
				return null;
			List<ActivityScoreDetail> leftOverActivities = null;
			List<ModuleScoreDetail> moduleScoreDetails = patientScoreDetail.getScoreData();
			ModuleScoreDetail moduleScoreDetail = moduleScoreDetails.stream() 
					.filter(x -> (module.toString()).equals(x.getModule()))   
					.findAny()                                     		 
					.orElse(null);

			if(moduleScoreDetail == null) {
				patientScoreDetail= createModuleScoreDetail(patientPin, null, null, patientSchedule, 
						patientScoreDetail, Integer.valueOf(module), dayOfModule);
			}
			if(currModule >= 1) {
				leftOverActivities = getLeftOverActivities(Integer.valueOf(module),patientScoreDetail);
			}
			moduleScoreDetail = moduleScoreDetails.stream() 
					.filter(x -> (module.toString()).equals(x.getModule()))   
					.findAny()                                     		 
					.orElse(null);

			if(leftOverActivities != null && !leftOverActivities.isEmpty() && moduleScoreDetail!=null) {
				HashSet<String> uniqueSet = new HashSet<>();
				for(ActivityScoreDetail obj : moduleScoreDetail.getActivityScores()) {
					uniqueSet.add(obj.getActivityName());
				}				
				List<ActivityScoreDetail> al = moduleScoreDetail.getActivityScores();
				for(ActivityScoreDetail obj : leftOverActivities ) {
					if(!uniqueSet.contains(obj.getActivityName())) {
						al.add(obj);
					}
				}
				moduleScoreDetail.setActivityScores(al);
			}
			if(dao.updatePatientScoreDetail(patientScoreDetail)) {
				System.out.println("Patient score detail updated  - from getActivityListWithCallToAction "
						+ "for pin : "+ patientPin);
			}else {
				System.out.println("Patient score detail did NOT update - from getActivityListWithCallToAction "
						+ "for pin : "+ patientPin);
			}
			
			// Call method which has Priority queue and return list of module activity in ascending order of score	
			moduleAcivityDetails = getOrderedModuleActivities(patientScoreDetail,Integer.parseInt(module));

			result.setActivityList(moduleAcivityDetails);

		}catch(Exception e ) {
			System.out.println("Error in getActivityListWithCallToAction");
			e.printStackTrace();
			return null;
		}


		return result;

		// if dayOfModule ==0 -- create modulescoredetail and thn leftover add it to module score
		// create inner class contains score and ModuleActivityDetails - create PQ and store sorted in list

		// if dayOfModule > 0 -- read score data and create list using PQ above.
	}

	/************************************ Helper service methods *************************************************/


	// To get current module and day based on current date for the patient.
	public HashMap<String, Integer> getModuleAndDay(PatientSchedule patientSchedule, Date today) {

		if (patientSchedule == null
				|| patientSchedule.getPatientSchedule() == null
				|| patientSchedule.getPatientSchedule().size() == 0) {
			return  new HashMap<>();
		}

		List<ModuleDetail> moduleJson = patientSchedule.getPatientSchedule();

		HashMap<String, Integer> rval = new HashMap<>();
		try {
			for(int i =0; i<moduleJson.size(); i++) {

				Date startDate= getDateWithoutTime(moduleJson.get(i).getStartDate());// new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());
				Date endDate = getDateWithoutTime(moduleJson.get(i).getEndDate()); //new SimpleDateFormat(ReachService.DATE_FORMAT).parse(.toString());

				today = getDateWithoutTime(today);
				if(today.compareTo(startDate) >= 0 && today.compareTo(endDate) <=0) {
					rval.put(this.MODULE, Integer.valueOf(moduleJson.get(i).getModule()));
					long diffTime = today.getTime() - startDate.getTime();
					Long d = TimeUnit.DAYS.convert(diffTime, TimeUnit.MILLISECONDS);
					rval.put(this.DAY,d.intValue());
					break;
				}

			}
			return rval;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public Date getDateWithoutTime(Date date) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"dd/MM/yyyy");
			return formatter.parse(formatter.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String readFile(String filename) {
		String result = "";
		try {
			InputStream inputStream = ModelFactory.class.getResourceAsStream(filename);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder stringBuilder = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuilder.append(line);
				line = bufferedReader.readLine();
			}
			result = stringBuilder.toString();
			bufferedReader.close();
			inputStream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// To calculate date according to module length and starting date of first module. 
	public HashMap<String,List<String>> calculateDefaultModuleDates(){
		HashMap<String,List<String>> result = new HashMap<>();
		try {
			int moduleDays = Integer.parseInt(_properties.getProperty(MODULE_DURATION_DAYS));
			int totalModule = Integer.parseInt(_properties.getProperty(TOTAL_MODULE));
			Date today = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			Date startDate = today;
			for(int i =1; i<=totalModule; i++) {
				List<String> dateList = new ArrayList<>();
				dateList.add(startDate.toString());
				cal.add(Calendar.DATE, moduleDays-1);
				Date endDate = cal.getTime();
				dateList.add(endDate.toString());
				cal.setTime(endDate);
				cal.add(Calendar.DATE, 1);
				startDate = cal.getTime();
				result.put(String.valueOf(i), dateList);
			}
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	// To update patient score once the activity instance is completed.
	public boolean updatePatientScoreDetail(int patientPin, String activityInstanceId, String activityId, 
			String activityName) {
		PatientSchedule patientSchedule = dao.getPatientSchedule(patientPin);
		PatientScoreDetail patientScoreDetail = dao.getPatientScoreDetail(patientPin);

		if(patientSchedule == null) {
			return false;  // return null object as no schedule for the patient.
		}

		HashMap<String, Integer> map = getModuleAndDay(patientSchedule, new Date());
		Integer module =-1, dayOfModule =-1,moduleIndex=-1;
		if(map != null && map.size() > 0) {
			if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
				module = map.get(this.MODULE);
			if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
				dayOfModule=map.get(this.DAY);
		}
		if(module == -1) {
			return false;
		}else {
			if(patientScoreDetail == null) {
				patientScoreDetail=createModuleScoreDetail(patientPin, activityInstanceId, 
						activityName, patientSchedule, patientScoreDetail, module, dayOfModule);
				// Create object for the patient.
			}else {
				patientScoreDetail= updateModuleScoreDetail(patientPin, activityInstanceId, activityId,
						activityName, patientSchedule, patientScoreDetail, module, dayOfModule);
			}
			if(dao.updatePatientScoreDetail(patientScoreDetail)) 
				return true;
			else
				return false;

		}

	}


	// Create module score data for the patient.
	public PatientScoreDetail createModuleScoreDetail(int patientPin, String activityInstanceId, 
			String activityName, PatientSchedule patientSchedule,
			PatientScoreDetail patientScoreDetail, Integer module, Integer dayOfModule) {
		PatientScoreDetail pDetail;
		List<ModuleScoreDetail> mScoreDetails;
		if(patientScoreDetail == null) {
			pDetail = new PatientScoreDetail();
			pDetail.setPatientPin(patientPin);
			mScoreDetails = new ArrayList<>();
		}else {
			pDetail=patientScoreDetail;
			mScoreDetails = pDetail.getScoreData();
		}
		DayDetail dayDetail =patientSchedule.getPatientSchedule().get(module-1).getSchedule().get(0);
		List<ActivityScheduleDetail> actDetail = dayDetail.getActivitySchedule();
		Integer moduleValue = module;

		ModuleScoreDetail moduleScoreDetail = new ModuleScoreDetail();
		moduleScoreDetail.setModule(moduleValue.toString());
		moduleScoreDetail.setDaySoFar(dayOfModule);
		List<ActivityScoreDetail> actScore = new ArrayList<>();
		for(ActivityScheduleDetail aSchedule : actDetail ) {
			String actName = aSchedule.getActivity();
			String actId = dao.getActivityIdByName(actName);
			float totalCount = aSchedule.getTotalCount();
			float actualCount = 0;
			float score =0;
			
			HashSet<String> aiList = new HashSet<>();
			if(activityName != null && activityName.equals(aSchedule.getActivity())) {
				actualCount += 1;
				score = (actualCount/totalCount)*100;
				aiList.add(activityInstanceId);
			}
			ActivityScoreDetail activityScoreDetail = new ActivityScoreDetail(actName, actId, actualCount,
					totalCount, score, aiList);
			if(activityScoreDetail.getActivityName().equalsIgnoreCase(WRAPUP_ACTIVITYNAME)) {
				activityScoreDetail.setScore(100);
			}
			actScore.add(activityScoreDetail);
		}
		moduleScoreDetail.setActivityScores(actScore);
		mScoreDetails.add(moduleScoreDetail);
		pDetail.setScoreData(mScoreDetails);
		if(dao.updatePatientScoreDetail(pDetail)) 
			return pDetail;
		else 
			return null;

	}

	// Update module score data for the patient.
	public PatientScoreDetail updateModuleScoreDetail(int patientPin, String activityInstanceId, String activityId, 
			String activityName, PatientSchedule patientSchedule,
			PatientScoreDetail patientScoreDetail, Integer module, Integer dayOfModule) {
		List<ModuleScoreDetail> moduleScoreList = patientScoreDetail.getScoreData();
		List<ActivityScheduleDetail> aScheduleList = patientSchedule.getPatientSchedule().get(module-1)
				.getSchedule().get(0).getActivitySchedule();
		ActivityScheduleDetail activityScheduleDetail = aScheduleList.stream() 
				.filter(x -> activityName.equals(x.getActivity()))   
				.findAny()                                     		 
				.orElse(null);
		int tc =0; // Change variable
		if(activityScheduleDetail != null)
			tc = activityScheduleDetail.getTotalCount();

		ModuleScoreDetail moduleScoreDetail = moduleScoreList.stream() 
				.filter(x -> (module.toString()).equals(x.getModule()))
				.findAny()                                     		 
				.orElse(null);

		if(moduleScoreDetail != null) {
			List<ActivityScoreDetail> activityScoreDetails = moduleScoreDetail.getActivityScores();
			for(ActivityScoreDetail actScore : activityScoreDetails) {
				float totalCount, actualCount;
				float score;
				totalCount = actScore.getTotalCount();
				if(dayOfModule > moduleScoreDetail.getDaySoFar()) {
					totalCount += tc; // change total count
				}
				actScore.setTotalCount(totalCount);
				if(actScore.getActivityName().equalsIgnoreCase(WRAPUP_ACTIVITYNAME)) {
					actScore.setScore(100);
				}
				if(actScore.getActivityName().equalsIgnoreCase(activityName)) {
					actualCount = actScore.getActualCount() + 1;
					score = (actualCount/totalCount)*100;
					if(score >100) {
						score =100;
					}
					actScore.setActualCount(actualCount);
					actScore.setScore(score);
					HashSet<String> aiList = actScore.getActivityInstances();
					aiList.add(activityInstanceId);
					actScore.setActivityInstances(aiList);
				}
			}
			moduleScoreDetail.setDaySoFar(dayOfModule);
			return patientScoreDetail;
		}else {
			return createModuleScoreDetail(patientPin, activityInstanceId, activityName,
					patientSchedule, patientScoreDetail, module, dayOfModule);
		}
	}


	public List<ActivityScoreDetail> getLeftOverActivities(Integer module, PatientScoreDetail patientScoreDetail){
		List<ActivityScoreDetail> result = new ArrayList<>();
		Integer prevModuleIndex = module -1;
		List<ModuleScoreDetail> moduleScoreDetails =patientScoreDetail.getScoreData();
		if(prevModuleIndex >=0) {
			ModuleScoreDetail moduleScoreDetail = moduleScoreDetails.stream() 
					.filter(x -> (prevModuleIndex.toString()).equals(x.getModule()))   
					.findAny()                                     		 
					.orElse(null);
			List<ActivityScoreDetail> activityScoreDetails =null;
			if(moduleScoreDetail != null)
				activityScoreDetails = moduleScoreDetail.getActivityScores();
			else
				return result;
			for(int i=0; i< activityScoreDetails.size();i++) {
				ActivityScoreDetail obj = activityScoreDetails.get(i);
				if(obj.getScore() < 100) {
					result.add(obj);
				}
			}
		}
		return result;
	}

	public List<ModuleAcivityDetail> getOrderedModuleActivities(PatientScoreDetail patientScoreDetail, 
			Integer currModule){
		List<ModuleAcivityDetail> result = new ArrayList<>();
		List<ModuleScoreDetail> moduleScoreDetails = patientScoreDetail.getScoreData();
		ModuleScoreDetail moduleScoreDetail = moduleScoreDetails.stream() 
				.filter(x -> (currModule.toString()).equals(x.getModule()))   
				.findAny()                                     		 
				.orElse(null);
		List<ActivityScoreDetail> activityScoreDetails = null;
		if(moduleScoreDetail != null) {
			activityScoreDetails = moduleScoreDetail.getActivityScores();
		}
		else
			return result;
		PriorityQueue<ScoreDetails> pq = new PriorityQueue<>(new Comparator<ScoreDetails>() {

			@Override
			public int compare(ScoreDetails arg0, ScoreDetails arg1) {
				if(arg0.score < arg1.score)
					return -1;
				else if(arg0.score > arg1.score)
					return 1;
				return 0;
			}
		});		
		
		for(ActivityScoreDetail obj : activityScoreDetails) {
			ModuleAcivityDetail moduleAcivityDetail = new ModuleAcivityDetail();
			moduleAcivityDetail.setActivityId(obj.getActivityId());
			moduleAcivityDetail.setActivityName(obj.getActivityName());
			if(obj.getScore()<100 && !moduleAcivityDetail.getActivityName().equalsIgnoreCase(WRAPUP_ACTIVITYNAME) ) {
				moduleAcivityDetail.setCallToAction(true);
			}
			if(isLastDay(patientScoreDetail.getPatientPin()) &&
					moduleAcivityDetail.getActivityName().equalsIgnoreCase(WRAPUP_ACTIVITYNAME)) {
				moduleAcivityDetail.setCallToAction(true);
			}
			ScoreDetails scoreDetails = new ScoreDetails(obj.getScore(), moduleAcivityDetail);
			pq.add(scoreDetails);
		}	
		while(!pq.isEmpty()) {
			result.add(pq.poll().getModuleAcivityDetail());
		}
		return result;
	}


	public boolean isLastDay(Integer patientPin) {
		PatientSchedule patientSchedule = dao.getPatientSchedule(patientPin);
		if(patientSchedule == null)
			return false;
		Integer moduleLen = Integer.parseInt(_properties.getProperty(MODULE_DURATION_DAYS));
		HashMap<String, Integer> map = getModuleAndDay(patientSchedule, new Date());
		Integer module =-1, dayOfModule =-1,moduleIndex=-1;
		if(map != null && map.size() > 0) {
			if (map.containsKey(this.MODULE) && map.get(this.MODULE) != null)
				module = map.get(this.MODULE);
			if (map.containsKey(this.DAY) && map.get(this.DAY) != null)
				dayOfModule=map.get(this.DAY);
		}
		if(module == -1) 
			return false;
		else {
			if(dayOfModule == (moduleLen-1)) {
				return true;
			}
		}
		return false;
	}

	public boolean isLong(String s) {
		try {
			long l = Long.parseLong(s);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public class ScoreDetails{
		float score;
		ModuleAcivityDetail moduleAcivityDetail;
		public ScoreDetails(float score, ModuleAcivityDetail moduleAcivityDetail) {
			this.score = score;
			this.moduleAcivityDetail=moduleAcivityDetail;
		}

		public ModuleAcivityDetail getModuleAcivityDetail() {
			return moduleAcivityDetail;
		}

		public float getScore() {
			return score;
		}
	}

	public int getLevelOfRewards(float score){
		if(score==0)
			return 0;
		else if(score>0 && score <=33)
			return 1;
		else if(score>33 && score <=66)
			return 2;
		else if(score>66 && score<=100)
			return 3;

		return -1;
	}
}

