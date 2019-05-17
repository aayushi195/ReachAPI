package edu.asu.heal.core.api.responses;

import java.util.List;

import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.models.ActivityInstance;
import edu.asu.heal.core.api.models.IHealModelType;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;

public class ScheduleResponse extends HEALResponse {

	  @Override
	    protected String toEntity(String data) {
	        return data;
	    }

	    @Override
	    protected String toEntity(List<IHealModelType> data) {
	        List<PatientSchedule> activities = (List<PatientSchedule>)(List<?>) data;
	        return HALHelperFactory
	                .getHALGenerator()
	                .getPatientScheduleJSON(activities, this.getServerURI() + SCHEDULE_RESOURCE_PATH,
	                		this.getServerURI() + PATIENT_RESOURCE_PATH
	                		);
	    }

	    @Override
	    protected String toEntity(IHealModelType instance) {
	        PatientSchedule activityInstance = (PatientSchedule) instance;
	        return HALHelperFactory
	                .getHALGenerator()
	                .getPatientScheduleJSON(activityInstance,
	                        this.getServerURI() + SCHEDULE_RESOURCE_PATH,
	                        this.getServerURI() + PATIENT_RESOURCE_PATH
	                        );
	    }
}
