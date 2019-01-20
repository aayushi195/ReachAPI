package edu.asu.heal.reachv3.api.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;
import edu.asu.heal.core.api.models.*;
import edu.asu.heal.core.api.responses.HEALResponse;
import edu.asu.heal.core.api.service.HealService;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingFactory;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;
import edu.asu.heal.reachv3.api.models.MakeBelieveActivityInstance;
import edu.asu.heal.reachv3.api.models.MakeBelieveSituation;
import edu.asu.heal.reachv3.api.models.WorryHeadsModel;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ReachService implements HealService {

    private static final String DATE_FORMAT = "MM/dd/yyyy";

    /****************************************  Service methods for Activity  ******************************************/
    @Override
    public List<Activity> getActivities(String domain) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<Activity> result = dao.getActivities(domain);

            return result;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITIES() IN REACHSERVICE CLASS");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity createActivity(String title, String description) {
        try {
            DAO dao = DAOFactory.getTheDAO();
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

    @Override
    public Activity getActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getActivity(activityId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity updateActivity(Activity activity) {
        try {
            DAO dao = DAOFactory.getTheDAO();
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

    @Override
    public Activity deleteActivity(String activityId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.deleteActivity(activityId);
        } catch (Exception e) {
            System.out.println("SOME PROBLEM IN REACH SERVICE DELETE ACTIVITY INSTANCE");
            e.printStackTrace();
            return null;
        }
    }

    /****************************************  Service methods for ActivityInstance  **********************************/
    @Override
    public List<ActivityInstance> getActivityInstances(int patientPin) {
        List<ActivityInstance> response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
            List<ActivityInstance> instances = dao.getScheduledActivities(patientPin);

            return instances;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN GETACTIVITYINSTANCES() IN REACHSERVICE");
            e.printStackTrace();
            return null;
        }
    }

 
    public String getEmotionsActivityInstance(int patientPin, String emotion, int intensity){
        try{
            DAO dao = DAOFactory.getTheDAO();
            // Task #386
            MappingInterface mapper = MappingFactory.getTheMapper();
            String intensityVal = (String)mapper.intensityMappingToDifficultyLevel(intensity);
            
            List<String> results = dao.getEmotionsActivityInstance(emotion.toLowerCase(), intensityVal);
            if(results == null)
                return "";

            StringWriter writer = new StringWriter();
            JsonGenerator generator = new JsonFactory().createGenerator(writer);
            generator.setCodec(new ObjectMapper());
            generator.writeStartObject();
            generator.writeObjectField("activities", results);
            generator.writeEndObject();

            generator.close();
            String emotionsActivities = writer.toString();
            writer.close();
            return emotionsActivities;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance getActivityInstance(String activityInstanceId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            ActivityInstance rval;
            rval =  dao.getActivityInstance(activityInstanceId);

            if(rval!=null && rval.getInstanceOf().getName().equals("MakeBelieve")) //todo need to do this more elegantly
                rval = dao.getActivityMakeBelieveInstanceDAO(activityInstanceId);

            return rval;
        } catch (Exception e) {
            System.out.println("SOME ERROR IN HEAL SERVICE getActivityInstance");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ActivityInstance createActivityInstance(ActivityInstance activityInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            if (activityInstance.getCreatedAt() == null) activityInstance.setCreatedAt(new Date());
            if (activityInstance.getState() == null) activityInstance.setState(ActivityInstanceStatus.CREATED.status());
            if (activityInstance.getUpdatedAt() == null) activityInstance.setUpdatedAt(new Date());

            if(activityInstance.getInstanceOf().getName().equals("MakeBelieve")){ //todo need a more elegant way of making the check whether it is of type make believe
                activityInstance =
                        new MakeBelieveActivityInstance(activityInstance.getActivityInstanceId(),
                        activityInstance.getCreatedAt(), activityInstance.getUpdatedAt(),
                        activityInstance.getDescription(), activityInstance.getStartTime(), activityInstance.getEndTime(),
                        activityInstance.getUserSubmissionTime(), activityInstance.getActualSubmissionTime(),
                        activityInstance.getInstanceOf(), activityInstance.getState(),
                        activityInstance.getPatientPin(), dao.getMakeBelieveSituation());
            }

            ActivityInstance newActivityInstance = dao.createActivityInstance(activityInstance);
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
            DAO dao = DAOFactory.getTheDAO();
            ObjectMapper mapper = new ObjectMapper();
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
            mapper.setDateFormat(format);

            JsonNode activityInstanceAsTree = mapper.readTree(requestBody);
            String activityInstanceType = activityInstanceAsTree.get("instanceOf").get("name").asText();

            ActivityInstance instance;
            if(activityInstanceType.equals("MakeBelieve")){ // todo Need to find a more elegant way to do this
                instance = mapper.readValue(requestBody, MakeBelieveActivityInstance.class);
                instance.setUpdatedAt(new Date());
            }else{
                instance  = mapper.readValue(requestBody, ActivityInstance.class);
                instance.setUpdatedAt(new Date());
            }
            if(dao.updateActivityInstance(instance)){
                return instance;
            }
            return NullObjects.getNullActivityInstance();
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
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomains();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain getDomain(String id) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            return dao.getDomain(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Domain addDomain(String title, String description, String state) {

        try {
            DAO dao = DAOFactory.getTheDAO();
            Domain instance = new Domain(title, description, state);
            instance.setCreatedAt(new Date());
            if (instance.getState() == null) instance.setState(DomainState.CREATED.state());

            return dao.createDomain(instance);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String addTestDomain(String title, String description, String state) {
        return null;
    }

    /****************************************  Service methods for Patient  *******************************************/
    @Override
    public List<Patient> getPatients(String trialId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
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

    @Override
    public Patient getPatient(int patientPin) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.getPatient(patientPin);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient createPatient(String trialId) {
        try {
            DAO dao = DAOFactory.getTheDAO();
            return dao.createPatient(trialId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Patient updatePatient(Patient patient) {
        try {
            DAO dao = DAOFactory.getTheDAO();
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

    @Override
    public String deletePatient(String patientPin) {
        return "DELETE PATIENT";
    }

    /****************************************  Service methods for Trial  *********************************************/

    @Override
    public List<Trial> getTrials(String domain) {
        HEALResponse response = null;
        try {
            DAO dao = DAOFactory.getTheDAO();
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

    @Override
    public Trial addTrial(Trial trialInstance) {
        try {
            DAO dao = DAOFactory.getTheDAO();

            // check if the domain exist, if yes get the id of domain
            Domain domain = dao.getDomain(trialInstance.getDomainId());
            if (domain != null) {

                Date startDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getStartDate().toString());
                Date endDateFormat = new SimpleDateFormat(ReachService.DATE_FORMAT).parse(trialInstance.getEndDate().toString());

                trialInstance.setUpdatedAt(new Date());
                trialInstance.setCreatedAt(new Date());
                trialInstance.setStartDate(startDateFormat);
                trialInstance.setEndDate(endDateFormat);
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

    /****************************************  Other Service methods  *************************************************/

    @Override
    public String getWorryHeadsInstance() {
        try {
            Random r = new Random();
            String[] o_options = {"The goalie isn't the only player on the team," +
                    " so it couldn't have been only my fault that we lost",
                    "Even though we didn't win, we tied the game, so we still did pretty well",
                    "I've practiced and I feel that I did the best I could and sometimes losing just happens",
                    "I should have practice harder."};

            WorryHeadsModel whm = new WorryHeadsModel(0,
                    "WorryHeads",
                    "You think, \"She thinks it's my fault we didn't win.\"",
                    "You are the goalie for your soccer team and today's game ended in a tie. " +
                            "After the game, you hear a teammate say that your team should have won",
                    "P text",
                    r.nextInt(4),
                    o_options);

            return new ObjectMapper().writeValueAsString(whm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
