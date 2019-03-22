package edu.asu.heal.core.api.dao;

import edu.asu.heal.core.api.models.*;
import edu.asu.heal.reachv3.api.models.*;

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

    String getActivityMakeBelieveInstanceDAO(String activityInstanceId);

    WorryHeadsSituation getWorryHeadsSituation();

    String getActivityWorryHeadsInstanceDAO(String activityInstanceId);

    StandUpSituation getStandUpSituation();

    String getActivityStandUpInstanceDAO(String activityInstanceId);

    List<FaceItChallenges> getFaceItChallenges();

    FaceItActivityInstance getActivityFaceInstanceDAO(String activityInstanceId);

    String getActivityNameById(String activityId);

    DailyDiarySituation getDailyDiarySituation();

    String getActivityDailyDiaryInstanceDAO(String activityInstanceId);

    SwapSituation getSwapSituation();

    String getActivitySwapInstanceDAO(String activityInstanceId);

    String getActivityEmotionInstanceDAO(String activityInstanceId);

}
