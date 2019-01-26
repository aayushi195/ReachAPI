package edu.asu.heal.core.api.models;

import java.util.Date;

public class Logger implements IHealModelType {

    public static String TRIAL_ATTRIBUTE = "trialId";
    public static String TIMESTAMP_ATTRIBUTE = "timeStamp";
    public static String LEVEL_ATTRIBUTE = "level";
    public static String TYPE_ATTRIBUTE = "type";
    public static String FORMAT_ATTRIBUTE = "format";
    public static String SUBTYPE_ATTRIBUTE = "subtype";
    public static String PATIENTPIN_ATTRIBUTE = "patientPin";
    public static String METADATA_ATTRIBUTE = "metadata";

    private String trialId;
    private String timeStamp;
    private String level;
    private String type;
    private String format;
    private String subtype;
    private String patientPin;
    private String metadata;

    public Logger() {

    }

    public Logger(String trial, String timeStamp, String level, String type, String format, String subtype,
                  String patientPin, String metadata) {
        this.trialId = trial;
        this.timeStamp = timeStamp;
        this.level = level;
        this.type = type;
        this.format = format;
        this.subtype = subtype;
        this.patientPin = patientPin;
        this.metadata = metadata;
    }

    public String getTrialId() {
        return trialId;
    }

    public String getFormat() {
        return format;
    }

    public String getLevel() {
        return level;
    }

    public String getMetadata() {
        return metadata;
    }

    public String getPatientPin() {
        return patientPin;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public void setPatientPin(String patientPin) {
        this.patientPin = patientPin;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTrialId(String trial) {
        this.trialId = trial;
    }

    @Override
    public String toString() {
        return "Log{" +
                ", trialId='" + trialId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", level='" + level + '\'' +
                ", format='" + format + '\'' +
                ", subtype='" + subtype + '\'' +
                ", patientPin='" + patientPin + '\'' +
                ", metadata='" + metadata + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int PRIME = 31;
        int result = 3;
        result = PRIME * result + (this.trialId == null ? 0 :this.trialId.hashCode());
        result = PRIME * result + (this.timeStamp == null ? 0 :this.timeStamp.hashCode());
        result = PRIME * result + (this.level == null ? 0 :this.level.hashCode());
        result = PRIME * result + (this.format == null ? 0 :this.format.hashCode());
        result = PRIME * result + (this.subtype == null ? 0 :this.subtype.hashCode());
        result = PRIME * result + (this.patientPin == null ? 0 :this.patientPin.hashCode());
        result = PRIME * result + (this.metadata == null ? 0 :this.metadata.hashCode());
        return result;
    }
}