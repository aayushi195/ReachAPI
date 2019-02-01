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

    MakeBelieveActivityInstance getActivityMakeBelieveInstanceDAO(String activityInstanceId);

    List<WorryHeadsSituation> getWorryHeadsSituation();

    WorryHeadsActivityInstance getActivityWorryHeadsInstanceDAO(String activityInstanceId);

    List<StandUpSituation> getStandUpSituation();

    StandUpActivityInstance getActivityStandUpInstanceDAO(String activityInstanceId);

}
