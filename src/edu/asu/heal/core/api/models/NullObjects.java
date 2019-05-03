package edu.asu.heal.core.api.models;

import java.util.Date;

public class NullObjects {
    // Class used to implement the Null Object pattern for error handling

    private static final Activity NULL_ACTIVITY;
    private static final ActivityInstance NULL_ACTIVITY_INSTANCE;
    private static final ActivityInstanceType NULL_ACTIVITY_INSTANCE_TYPE;
    private static final Patient NULL_PATIENT;
    private static final Trial NULL_TRIAL;
    private static final Domain NULL_DOMAIN;
    private static final Logger NULL_LOGGER;  

    static {
        Activity a = new Activity();
        a.setTitle("NULL");
        a.setDescription("NULL");
        a.setActivityId("NULL");
        a.setCreatedAt(new Date(0));
        a.setUpdatedAt(new Date(0));

        NULL_ACTIVITY = a;
        a = null;


        ActivityInstance instance = new ActivityInstance();
        instance.setActivityInstanceId("NULL");
        instance.setCreatedAt(new Date(0));
        instance.setUpdatedAt(new Date(0));
        instance.setStartTime(new Date(0));
        instance.setEndTime(new Date(0));
        instance.setUserSubmissionTime(new Date(0));
        instance.setActualSubmissionTime(new Date(0));
        instance.setActivityId("NULL");
        instance.setState("NULL");
        instance.setDescription("NULL");

        NULL_ACTIVITY_INSTANCE = instance;
        instance = null;

        ActivityInstanceType instanceType = new ActivityInstanceType("NULL", "NULL");
        NULL_ACTIVITY_INSTANCE_TYPE = instanceType;


        Patient patient = new Patient();
        patient.setPatientId("NULL");
        patient.setState("NULL");
        patient.setCreatedAt(new Date(0));
        patient.setStartDate(new Date(0));
        patient.setPin(-1);

        NULL_PATIENT = patient;


        Trial t = new Trial();
        t.setTrialId("NULL");
        t.setDomainId("NULL");
        t.setTitle("NULL");
        t.setCreatedAt(new Date(0));
        t.setUpdatedAt(new Date(0));

        NULL_TRIAL = t;

        Domain d = new Domain();

        d.setDomainId("NULL");
        d.setTitle("NULL");
        d.setCreatedAt(new Date(0));

        NULL_DOMAIN = d;

        Logger l = new Logger();

        l.setTrialId("NULL");
        l.setFormat("NULL");
        l.setLevel("NULL");
        l.setMetadata("NULL");
        l.setPatientPin("NULL");
        l.setPatientPin("NULL");
        l.setTimeStamp("NULL");
        l.setType("NULL");

        NULL_LOGGER = l;

    }

    public static Activity getNullActivity() {
        return NULL_ACTIVITY;
    }

    public static ActivityInstance getNullActivityInstance() {
        return NULL_ACTIVITY_INSTANCE;
    }

    public static ActivityInstanceType getNullActivityInstanceType() {
        return NULL_ACTIVITY_INSTANCE_TYPE;
    }

    public static Patient getNullPatient() {
        return NULL_PATIENT;
    }

    public static Trial getNullTrial() {
        return NULL_TRIAL;
    }

    public static Domain getNullDomain() {
        return NULL_DOMAIN;
    }

    public static Logger getNullLogger() { return NULL_LOGGER; }
}
