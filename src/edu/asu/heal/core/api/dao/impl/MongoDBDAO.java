package edu.asu.heal.core.api.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.core.api.models.schedule.PatientScoreDetail;
import edu.asu.heal.reachv3.api.models.*;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsInstance;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.util.*;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBDAO implements DAO {

	// collection captions
	private static final String DOMAINS_COLLECTION = "domains";
	private static final String TRIALS_COLLECTION = "trials";
	private static final String ACTIVITIES_COLLECTION = "activities";
	private static final String PATIENTS_COLLECTION = "patients";
	private static final String ACTIVITYINSTANCES_COLLECTION = "activityInstances";
	private static final String MAKEBELIEVESITUATIONS_COLLECTION = "makeBelieveSituations";
	private static final String MAKEBELIEVESITUATIONNAMES_COLLECTION = "makeBelieveSituationNames";
	private static final String WORRYHEADSSITUATIONS_COLLECTION = "worryHeadsSituations";
	private static final String STANDUPSITUATIONS_COLLECTION = "standUpSituations";
	private static final String FACEITCHALLENGES_COLLECTION = "faceItChallenges";
	private static final String DAILYDIARYSITUATIONS_COLLECTION = "dailyDiarySituations";
	private static final String SWAPSITUATIONS_COLLECTION = "swapSituations";
	private static final String LOGGER_COLLECTION = "logger";
	private static final String EMOTIONS_COLLECTION = "emotions";
	private static final String PATIENT_SCHEDULE_COLLECTION = "patientSchedules";
	private static final String PATIENT_SCORE_COLLECTION = "patientScores";
	private static final String PATIENT_REWARDS_COLLECTION = "rewards";

	private static String __mongoDBName;
	private static String __mongoURI;


	public MongoDBDAO(Properties properties) {
		__mongoURI = properties.getProperty("mongo.uri");
		__mongoDBName = properties.getProperty("mongo.dbname");
	}

	private static MongoClient mongoClient  = null;

	private static MongoDatabase getConnectedDatabase() {
		try {

			if (mongoClient == null) {
				mongoClient = new MongoClient(new MongoClientURI(__mongoURI));
			}
			// create codec registry for POJOs
			CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
					fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			//MongoClient mongoClient = new MongoClient(new MongoClientURI(__mongoURI));

			// get handle to "_mongoDBName" database
			return mongoClient.getDatabase(__mongoDBName).withCodecRegistry(pojoCodecRegistry);
		} catch (Exception e) {
			System.out.println("SOME ERROR GETTING MONGO CONNECTION");
			e.printStackTrace();
			return null;
		}

	}

	/****************************************  Domain DAO methods *****************************************************/
	@Override
	public List<Domain> getDomains() {
		MongoDatabase database = MongoDBDAO.getConnectedDatabase();
		MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

		return domainCollection.find().projection(Projections.excludeId()).into(new ArrayList<>());
	}

	@Override
	public Domain getDomain(String id) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			Domain domain = domainCollection
					.find(Filters.eq(Domain.DOMAINID_ATTRIBUTE, id))
					.projection(Projections.excludeId())
					.first();
			if (domain == null)
				return NullObjects.getNullDomain();

			return domain;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public Domain createDomain(Domain instance) {
		try {

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			ObjectId newId = ObjectId.get();
			instance.setDomainId(newId.toHexString());

			domainCollection.insertOne(instance);

			return instance;
		} catch (Exception e) {
			System.out.println("SOME ERROR CREATING DOMAIN");
			e.printStackTrace();
			return null;
		}

	}

	/****************************************  Activity DAO methods ***************************************************/
	@Override
	public List<Activity> getActivities(String domain)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(DOMAINS_COLLECTION, Domain.class);

			Domain domain1 = domainCollection.find(Filters.eq(Domain.TITLE_ATTRIBUTE, domain)).first();
			if (domain1 == null) {
				List<Activity> result = new ArrayList<>();
				result.add(NullObjects.getNullActivity());
				return result;
			}

			MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);
			return activityCollection
					.find(Filters.in(Activity.ACTIVITYID_ATTRIBUTE, domain1.getActivities().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());

		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return new ArrayList<>();
		} catch (Exception e) {
			System.out.println("SOME ERROR HERE **");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity createActivity(Activity activity)  {
		try {
			ObjectId newId = ObjectId.get();
			activity.setActivityId(newId.toHexString());

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activitiesCollection = database.getCollection(MongoDBDAO.ACTIVITIES_COLLECTION,
					Activity.class);

			activitiesCollection.insertOne(activity);

			return activity;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity getActivity(String activityId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);
			return activityMongoCollection
					.find(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activityId))
					.projection(Projections.excludeId())
					.first();
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY WITH ID " + activityId);
			ne.printStackTrace();
			return NullObjects.getNullActivity();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITY");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity updateActivity(Activity activity) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			return activityMongoCollection
					.findOneAndReplace(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activity.getActivityId()), activity);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Activity deleteActivity(String activityId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityMongoCollection =
					database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			Activity deletedActivity = activityMongoCollection
					.findOneAndDelete(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE, activityId));

			if (deletedActivity == null)
				return NullObjects.getNullActivity();

			// What are the possible ramification of deleting an Activity?
			// Delete all activity instances related to it
			// Delete the activity from the domain
			// Need to consider this before deleting

			return deletedActivity;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM DELETING ACTIVITY " + activityId);
			e.printStackTrace();
			return null;
		}
	}


	/****************************************  ActivityInstance DAO methods *******************************************/
	@Override
	public List<ActivityInstance> getScheduledActivities(int patientPin)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

			Patient patient = patientCollection.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin)).first();
			if (patient == null) {
				List<ActivityInstance> result = new ArrayList<>();
				result.add(NullObjects.getNullActivityInstance());
				return result;
			}

			MongoCollection<ActivityInstance> activityInstanceCollection =
					database.getCollection(MongoDBDAO.ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);
			return activityInstanceCollection
					.find(Filters.and(Filters.in(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE,
							patient.getActivityInstances().toArray(new String[]{})),
							Filters.eq(ActivityInstance.STATE_ATTRIBUTE, ActivityInstanceStatus.CREATED.status()))) // todo Need to confirm this. It could be other states from the enum as well
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME ERROR IN GETSCHEDULEDACTIVITIES IN MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance deleteActivityInstance(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			ActivityInstance deletedInstance = activityInstanceMongoCollection
					.findOneAndDelete(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId));

			if (deletedInstance == null)
				return NullObjects.getNullActivityInstance();

			Patient patient = getPatient(deletedInstance.getPatientPin());
			List<String> patientActivityInstances = patient.getActivityInstances();
			patientActivityInstances.remove(patientActivityInstances.indexOf(deletedInstance.getActivityInstanceId()));
			updatePatient(patient);

			return deletedInstance;
		} catch (Exception e) {
			System.out.println("SOME PROBLEM DELETING ACTIVITY INSTANCE " + activityInstanceId);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance createActivityInstance(ActivityInstance instance) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			Patient patient = getPatient(instance.getPatientPin());
			if (patient == null || patient.equals(NullObjects.getNullPatient()))
				return NullObjects.getNullActivityInstance();

			ObjectId newId = ObjectId.get();
			instance.setActivityInstanceId(newId.toHexString());

			activityInstanceMongoCollection.insertOne(instance);
			patient.getActivityInstances().add(instance.getActivityInstanceId());
			patient.setUpdatedAt(new Date());

			updatePatient(patient);
			return instance;
		} catch (Exception e) {
			System.out.println("SOME ERROR INSERTING NEW ACTIVITY INSTANCE INTO DATABASE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActivityInstance getActivityInstance(String activityInstanceId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			ActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();

			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			System.out.println(instance);
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public WorryHeadsSituation getWorryHeadsSituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<WorryHeadsSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.WORRYHEADSSITUATIONS_COLLECTION, WorryHeadsSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<WorryHeadsSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			WorryHeadsSituation situation = null;
			for(WorryHeadsSituation temp : situations){
				situation = temp;
			}

			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random worry heads situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting worry heads situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public StandUpSituation getStandUpSituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<StandUpSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.STANDUPSITUATIONS_COLLECTION, StandUpSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<StandUpSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			StandUpSituation situation = null;
			for(StandUpSituation temp : situations){
				situation = temp;
			}

			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<FaceItChallenges> getFaceItChallenges() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<FaceItChallenges> situationMongoCollection =
					database.getCollection(MongoDBDAO.FACEITCHALLENGES_COLLECTION, FaceItChallenges.class);

			FindIterable<FaceItChallenges> challenges = situationMongoCollection.find();

			List<FaceItChallenges> faceItChallenges = new ArrayList<>();
			for (FaceItChallenges challenge : challenges) {
				faceItChallenges.add(challenge);
			}

			return faceItChallenges;
		}catch (NullPointerException ne){
			System.out.println("Could not get face it challenges");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting face it challenge");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FaceItActivityInstance getActivityFaceInstanceDAO(String activityInstanceId){
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<FaceItActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, FaceItActivityInstance.class);

			//FaceItActivityInstance faceItIns =  new FaceItActivityInstance();
			FaceItActivityInstance instance = activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId))
					.projection(Projections.excludeId())
					.first();
			System.out.println("ACTIVITY INSTANCE GOT FROM DB");
			System.out.println(instance);
			return instance ;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return (FaceItActivityInstance) NullObjects.getNullActivityInstance();
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getActivityNameById(String activityId) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			Activity activity = activityCollection
					.find(Filters.eq(Activity.ACTIVITYID_ATTRIBUTE,activityId))
					.projection(Projections.excludeId())
					.first();

			if(activity == null)
				return null;
			return activity.getTitle();

		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME ERROR HERE **");
			e.printStackTrace();
			return null;
		}
		//return null;
	}
	
	@Override
	public String getActivityIdByName(String activityName) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			Activity activity = activityCollection
					.find(Filters.eq(Activity.TITLE_ATTRIBUTE,activityName))
					.projection(Projections.excludeId())
					.first();

			if(activity == null)
				return null;
			return activity.getActivityId();

		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME ERROR HERE **");
			e.printStackTrace();
			return null;
		}
		//return null;
	}

	@Override
	public DailyDiarySituation getDailyDiarySituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<DailyDiarySituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.DAILYDIARYSITUATIONS_COLLECTION, DailyDiarySituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<DailyDiarySituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			DailyDiarySituation situation = null;
			for(DailyDiarySituation temp : situations){
				situation = temp;
			}

			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random daily diary situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting daily diary situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public SwapSituation getSwapSituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<SwapSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.SWAPSITUATIONS_COLLECTION, SwapSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<SwapSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			SwapSituation situation = null;
			for(SwapSituation temp : situations){
				situation = temp;
			}

			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random SWAP situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting SWAP situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean updateActivityInstance(ActivityInstance instance) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<ActivityInstance> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION, ActivityInstance.class);

			ActivityInstance updatedInstance = activityInstanceMongoCollection
					.findOneAndReplace(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, instance.getActivityInstanceId()), instance);

			if(updatedInstance != null){
				return true;
			}
			return false;
		}catch (Exception e){
			System.out.println("Some problem in updateActivityInstance() in MongoDBDao");
			return false;
		}
	}

	/****************************************  Patient DAO methods ****************************************************/
	@Override
	public List<Patient> getPatients()  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(PATIENTS_COLLECTION, Patient.class);

			return patientCollection
					.find()
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Patient> getPatients(String trialId)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			Trial trial = trialCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();
			if (trial == null) {
				ArrayList<Patient> result = new ArrayList<>();
				result.add(NullObjects.getNullPatient());
				return result;
			}

			MongoCollection<Patient> patientsCollection = database
					.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

			return patientsCollection
					.find(Filters.in(Patient.PATIENTID_ATTRIBUTE, trial.getPatients().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient createPatient(String trialId) {
		try {

			MongoCollection<Trial> trialsCollection = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();

			if(trial == null)
				return NullObjects.getNullPatient();

			//Temporary code to generate new pin. It just increments the largest pin number in the database by 1
			int newPin = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.PATIENTS_COLLECTION)
					.aggregate(Arrays.asList(Aggregates.group(null,
							Accumulators.max(Patient.PIN_ATTRIBUTE, "$" + Patient.PIN_ATTRIBUTE))))
					.first()
					.getInteger(Patient.PIN_ATTRIBUTE);
			++newPin;

			Patient newPatient = new Patient();
			ObjectId newId = ObjectId.get();
			newPatient.setPatientId(newId.toHexString());
			newPatient.setPin(newPin);
			newPatient.setCreatedAt(new Date());
			newPatient.setEndDate(new Date());
			newPatient.setStartDate(new Date());
			newPatient.setUpdatedAt(new Date());
			newPatient.setState(PatientState.CREATED.state());

			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientCollection = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class);

			patientCollection.insertOne(newPatient);

			trial.getPatients().add(newPatient.getPatientId());

			trialsCollection.replaceOne(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId), trial);

			return newPatient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient getPatient(int patientPin) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			Patient patient = database.getCollection(MongoDBDAO.PATIENTS_COLLECTION, Patient.class)
					.find(Filters.eq(Patient.PIN_ATTRIBUTE, patientPin))
					.first();

			if (patient == null)
				return NullObjects.getNullPatient();
			return patient;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Patient updatePatient(Patient patient) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Patient> patientMongoCollection =
					database.getCollection(PATIENTS_COLLECTION, Patient.class);

			return patientMongoCollection
					.findOneAndReplace(Filters.eq(Patient.PIN_ATTRIBUTE, patient.getPin()), patient);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Trial DAO methods ******************************************************/

	@Override
	public List<Trial> getTrials()  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			return trialCollection
					.find()
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN GETTRIALS() METHOD OF MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Trial> getTrials(String domain)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Domain> domainCollection = database.getCollection(MongoDBDAO.DOMAINS_COLLECTION, Domain.class);

			Domain domain1 = domainCollection.find(Filters.eq(Domain.TITLE_ATTRIBUTE, domain)).first();
			if (domain1 == null) {
				List<Trial> result = new ArrayList<>();
				result.add(NullObjects.getNullTrial());
				return result;
			}

			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			return trialCollection
					.find(Filters.in(Trial.TRIALID_ATTRIBUTE, domain1.getTrials().toArray(new String[]{})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());
		} catch (Exception e) {
			System.out.println("SOME PROBLEM IN GETTRIALS() METHOD OF MONGODBDAO");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Trial createTrial(Trial trialInstance)  {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialCollection = database.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			ObjectId newId = ObjectId.get();
			trialInstance.setTrialId(newId.toHexString());

			trialCollection.insertOne(trialInstance);

			return trialInstance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Logger DAO methods *****************************************************/

	@Override
	public Logger[] logMessage (Logger[] loggerInstance) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<Trial> trialsCollection = MongoDBDAO.getConnectedDatabase()
					.getCollection(MongoDBDAO.TRIALS_COLLECTION, Trial.class);

			MongoCollection<Logger> loggerCollection = database.getCollection(MongoDBDAO.LOGGER_COLLECTION,
					Logger.class);
			for (Logger log: loggerInstance) {
				String trialId = log.getTrialId();

				Trial trial = trialsCollection.find(Filters.eq(Trial.TRIALID_ATTRIBUTE, trialId)).first();

				if(trial != null) {
					loggerCollection.insertOne(log);
				}
			}



			return loggerInstance;
		} catch (Exception e){
			System.out.println("Some problem in storing logs");
			e.printStackTrace();
			return null;
		}
	}

	/****************************************  Other DAO methods ******************************************************/

	@Override
	public MakeBelieveSituation getMakeBelieveSituation() {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<MakeBelieveSituation> situationMongoCollection =
					database.getCollection(MongoDBDAO.MAKEBELIEVESITUATIONS_COLLECTION, MakeBelieveSituation.class);

			//Code to randomly get a situation from the database
			AggregateIterable<MakeBelieveSituation> situations = situationMongoCollection
					.aggregate(Arrays.asList(Aggregates.sample(1)));

			MakeBelieveSituation situation = null;
			for(MakeBelieveSituation temp : situations){
				situation = temp;
			}

			// Code to randomly get a name from the database
			MongoCollection<Document> namesCollection =
					database.getCollection(MongoDBDAO.MAKEBELIEVESITUATIONNAMES_COLLECTION);
			AggregateIterable<Document> names = namesCollection.aggregate(Arrays.asList(Aggregates.sample(1)));

			String name = null;
			for(Document temp : names){
				name = temp.getString("name");
			}

			situation.setName(name);
			return situation;
		}catch (NullPointerException ne){
			System.out.println("Could not get random make believe situation");
			ne.printStackTrace();
			return null;
		}catch (Exception e){
			System.out.println("Some problem in getting Make believe situation");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Activity> getEmotionsActivityInstance(String emotion, Object intensity) {
		try {
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			// needs to incorporate Emotions model. - Task #386
			MongoCollection<Document> emotionMongoCollection =
					database.getCollection(MongoDBDAO.EMOTIONS_COLLECTION);//, Emotions.class);

			FindIterable<Document> result =	emotionMongoCollection.find(Filters.eq(EmotionActivityInstance.EMOTION_NAME,emotion));

			MongoCursor<Document> cursor = result.iterator();
			List<String> rval = new ArrayList<String>();
			List<Activity> suggestedActivities = new ArrayList<Activity>();

			while(cursor.hasNext()) {
				Document doc = cursor.next();
				String tempIntensity = doc.getString(EmotionActivityInstance.INTENSITY);
				if(tempIntensity.contains((String)intensity)) {
					rval.addAll((List<String>) doc.get(EmotionActivityInstance.ACTIVITIES));
				}
			}

			MongoCollection<Activity> activityCollection = database.getCollection(ACTIVITIES_COLLECTION, Activity.class);

			suggestedActivities = activityCollection.find(Filters.in(Activity.ACTIVITYID_ATTRIBUTE,  
					rval.toArray(new String [] {})))
					.projection(Projections.excludeId())
					.into(new ArrayList<>());

			return suggestedActivities;

		}catch (NullPointerException npe){
			npe.printStackTrace();
			return null;
		}

	}

	@Override
	public String getActivityInstanceAsStringDAO(String activityInstanceId) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			// needs to incorporate Emotions model. - Task #386
			MongoCollection<Document> activityInstanceMongoCollection =
					database.getCollection(ACTIVITYINSTANCES_COLLECTION);

			FindIterable<Document> result =	activityInstanceMongoCollection
					.find(Filters.eq(ActivityInstance.ACTIVITYINSTANCEID_ATTRIBUTE, activityInstanceId));

			MongoCursor<Document> cursor = result.iterator();
			String rval = "";

			while(cursor.hasNext()) {
				Document doc = cursor.next();
				rval = doc.toJson();
			}
			return rval;

		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING ACTIVITY INSTANCE WITH ID " + activityInstanceId);
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GETACTIVITYINSTANCEID");
			e.printStackTrace();
			return null;
		}
	}

	/********************************************* Schedule Methods ***********************************************/

	@Override
	public PatientSchedule createPatientSchedule(PatientSchedule patientSchedule) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<PatientSchedule> patientScheduleMongoCollection =
					database.getCollection(PATIENT_SCHEDULE_COLLECTION, PatientSchedule.class);
			
			PatientSchedule instance = getPatientSchedule(patientSchedule.getPatientPin());
			if(instance == null) {
				patientScheduleMongoCollection.insertOne(patientSchedule);
				instance = patientSchedule;
			}
			return instance;
		}catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN CREATING PATIENT SCHEDULE FOR PIN : " + patientSchedule.getPatientPin());
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN CREATE PATIENT SCHEDULE");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PatientSchedule getPatientSchedule(int patientPin) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientSchedule> patientScheduleMongoCollection =
					database.getCollection(PATIENT_SCHEDULE_COLLECTION, PatientSchedule.class);

			PatientSchedule instance = patientScheduleMongoCollection
					.find(Filters.eq(PatientSchedule.PATIENT_PIN, patientPin))
					.projection(Projections.excludeId())
					.first();
			return instance;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING PATIENT SCHEDULE FOR PIN : " + patientPin);
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GET PATIENT SCHEDULE");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PatientSchedule updatePatientSchedule(PatientSchedule patientSchedule) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();
			MongoCollection<PatientSchedule> patientScheduleMongoCollection =
					database.getCollection(PATIENT_SCHEDULE_COLLECTION, PatientSchedule.class);

			return patientScheduleMongoCollection
					.findOneAndReplace(Filters.eq(PatientSchedule.PATIENT_PIN, patientSchedule.getPatientPin()),
							patientSchedule);

		}catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN CREATING PATIENT SCHEDULE FOR PIN : " + patientSchedule.getPatientPin());
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN CREATE PATIENT SCHEDULE");
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PatientScoreDetail getPatientScoreDetail(int patientPin) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScoreDetail> patientScoreMongoCollection =
					database.getCollection(PATIENT_SCORE_COLLECTION, PatientScoreDetail.class);

			PatientScoreDetail instance = patientScoreMongoCollection
					.find(Filters.eq(PatientSchedule.PATIENT_PIN, patientPin))
					.projection(Projections.excludeId())
					.first();
			return instance;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING PATIENT SCHEDULE FOR PIN : " + patientPin);
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GET PATIENT SCHEDULE");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean updatePatientScoreDetail(PatientScoreDetail patientScoreDetail) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<PatientScoreDetail> patientScoreMongoCollection =
					database.getCollection(PATIENT_SCORE_COLLECTION, PatientScoreDetail.class);

			PatientScoreDetail obj =patientScoreMongoCollection
					.findOneAndReplace(Filters.eq(PatientScoreDetail.PATIENT_PIN, patientScoreDetail.getPatientPin()),
							patientScoreDetail);
			if(obj == null) {
				patientScoreMongoCollection.insertOne(patientScoreDetail);
			}
			return true;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING PATIENT SCHEDULE FOR PIN : " + patientScoreDetail.getPatientPin());
			ne.printStackTrace();
			return false;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GET PATIENT SCHEDULE");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public RewardsInstance createPatientRewards(RewardsInstance rewardsInstance, int patientPin) {
		try{
			MongoDatabase database = MongoDBDAO.getConnectedDatabase();

			MongoCollection<RewardsInstance> rewardsMongoCollection =
					database.getCollection(PATIENT_REWARDS_COLLECTION, RewardsInstance.class);

			RewardsInstance obj =rewardsMongoCollection
					.findOneAndReplace(Filters.eq(PatientScoreDetail.PATIENT_PIN, rewardsInstance.getPatientPin()),
							rewardsInstance);
			if(obj == null) {
				rewardsMongoCollection.insertOne(rewardsInstance);
			}
			return rewardsInstance;
		} catch (NullPointerException ne) {
			System.out.println("SOME PROBLEM IN GETTING PATIENT SCHEDULE FOR PIN : " + rewardsInstance.getPatientPin());
			ne.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("SOME SERVER PROBLEM IN GET PATIENT SCHEDULE");
			e.printStackTrace();
			return null;
		}
	}

}
