package edu.asu.heal.core.api.responses;

import java.util.List;

import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.IHealModelType;

public class ScheduleResponse extends HEALResponse {

	  @Override
	    protected String toEntity(String data) {
	        return data;
	    }

	    @Override
	    protected String toEntity(List<IHealModelType> data) {
	        List<ActivityInstance> activityInstances = (List<ActivityInstance>)(List<?>) data;
	        return HALHelperFactory
	                .getHALGenerator()
	                .getActivityInstancesJSON(activityInstances,
	                        this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH,
	                        this.getServerURI() + PATIENT_RESOURCE_PATH,
	                        this.getServerURI() + ACTIVITY_RESOURCE_PATH);
	    }

	    @Override
	    protected String toEntity(IHealModelType instance) {
	        ActivityInstance activityInstance = (ActivityInstance) instance;
	        return HALHelperFactory
	                .getHALGenerator()
	                .getActivityInstancesJSON(activityInstance,
	                        this.getServerURI() + ACTIVITY_INSTANCE_RESOURCE_PATH,
	                        this.getServerURI() + PATIENT_RESOURCE_PATH,
	                        this.getServerURI() + ACTIVITY_RESOURCE_PATH);
	    }
}
