package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.models.IHealModelType;
import edu.asu.heal.core.api.resources.*;
import org.glassfish.jersey.uri.internal.JerseyUriBuilder;

import java.util.List;

public abstract class HEALResponse {
    public static final String ACTIVITY_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityResource.class).toTemplate().replace("/", "");
    public static final String ACTIVITY_INSTANCE_RESOURCE_PATH = new JerseyUriBuilder().path(ActivityInstanceResource.class).toTemplate().replace("/", "");
    public static final String DOMAIN_RESOURCE_PATH = new JerseyUriBuilder().path(DomainResource.class).toTemplate().replace("/", "");
    public static final String PATIENT_RESOURCE_PATH = new JerseyUriBuilder().path(PatientResource.class).toTemplate().replace("/", "");
    public static final String TRIAL_RESOURCE_PATH = new JerseyUriBuilder().path(TrialsResource.class).toTemplate().replace("/", "");
    public static final String SCHEDULE_RESOURCE_PATH = new JerseyUriBuilder().path(ScheduleResource.class).toTemplate().replace("/", "");
    public static final String MODULE_RESOURCE_PATH = new JerseyUriBuilder().path(ModuleResource.class).toTemplate().replace("/", "");

    private int statusCode;
    private Object data;
    private String serverURI;

    public final String toEntity(){
        if(this.data instanceof String)
            return toEntity((String) this.data);
        else if(this.data instanceof List)
            return toEntity((List<IHealModelType>) this.data);
        else if(this.data instanceof IHealModelType)
            return toEntity((IHealModelType) this.data);

        return null;
    }

    protected abstract String toEntity(String data);

    protected abstract String toEntity(List<IHealModelType> data);

    protected abstract String toEntity(IHealModelType data);

    public final int getStatusCode() {
        return this.statusCode;
    }

    public final Object getData() {
        return this.data;
    }

    public final String getServerURI() {
        return this.serverURI;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public final void setData(Object data) {
        this.data = data;
    }

    public final void setServerURI(String serverURI) {
        this.serverURI = serverURI;
    }
}
