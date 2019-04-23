package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.core.api.models.schedule.PatientScoreDetail;
import edu.asu.heal.reachv3.api.models.*;
import edu.asu.heal.reachv3.api.models.patientRewards.RewardsInstance;

import java.util.List;

public interface DAO {


    /****************************************  Domain DAO methods *****************************************************/
    List<Domain> getDomains();

    Domain getDomain(String id);

    Domain createDomain(Domain instance);

    /****************************************  Activity DAO methods ***************************************************/
    List<Activity> getActivities(String domain);

    Activity createActivity(Activity activity);

    Activity getActivity(String activityId);

    Activity updateActivity(Activity activity);

    Activity deleteActivity(String activityId);


    /****************************************  ActivityInstance DAO methods *******************************************/
    List<ActivityInstance> getScheduledActivities(int patientPin);

    ActivityInstance deleteActivityInstance(String activityInstanceId);

    ActivityInstance createActivityInstance(ActivityInstance instance);

    ActivityInstance getActivityInstance(String activityInstanceId);

    boolean updateActivityInstance(ActivityInstance instance);


    /****************************************  Patient DAO methods ****************************************************/
    List<Patient> getPatients();

    List<Patient> getPatients(String trialId);

    Patient getPatient(int patientPin);

    Patient createPatient(String trialId);

    Patient updatePatient(Patient patient);

    /****************************************  Trial DAO methods ******************************************************/
    List<Trial> getTrials();

    List<Trial> getTrials(String domain);

    Trial createTrial(Trial trialInstance);

    /****************************************  Logger DAO methods *****************************************************/
    Logger[] logMessage (Logger[] loggerInstance);

    /****************************************  Other DAO methods ******************************************************/

    List<Activity> getEmotionsActivityInstance(String emotion, Object intensity);

    MakeBelieveSituation getMakeBelieveSituation();

    WorryHeadsSituation getWorryHeadsSituation();

    StandUpSituation getStandUpSituation();

    List<FaceItChallenges> getFaceItChallenges();

    FaceItActivityInstance getActivityFaceInstanceDAO(String activityInstanceId);

    String getActivityNameById(String activityId);
    
    String getActivityIdByName(String activityName);

    DailyDiarySituation getDailyDiarySituation();

    SwapSituation getSwapSituation();
    
    String getActivityInstanceAsStringDAO (String activityInstanceId);
    
    /************************************* Schedule Methods *******************************************************/
    
    PatientSchedule createPatientSchedule(PatientSchedule patientSchedule);
    
    PatientSchedule getPatientSchedule(int patientPin);
    
    PatientSchedule updatePatientSchedule(PatientSchedule patientSchedule);
    
    /********************************* Patient Score Data *********************************************************/
    
    PatientScoreDetail getPatientScoreDetail(int patientPin);
    
    boolean updatePatientScoreDetail(PatientScoreDetail patientScoreDetail);

    RewardsInstance createPatientRewards(RewardsInstance rewardsInstance,int patientPin);

}
