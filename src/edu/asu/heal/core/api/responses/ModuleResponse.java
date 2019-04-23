package edu.asu.heal.core.api.responses;

import edu.asu.heal.core.api.hal.HALHelperFactory;
import edu.asu.heal.core.api.models.IHealModelType;
import edu.asu.heal.core.api.models.schedule.PatientSchedule;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleActivityList;
import edu.asu.heal.reachv3.api.models.moduleProgession.ModuleInstance;

import java.util.List;

public class ModuleResponse extends HEALResponse {

	  @Override
	    protected String toEntity(String data) {
	        return data;
	    }

	    @Override
	    protected String toEntity(List<IHealModelType> data) {
	    	return null;
	    }

	    @Override
	    protected String toEntity(IHealModelType instance) {
	        ModuleInstance moduleInstance = (ModuleInstance) instance;
	        return HALHelperFactory
	                .getHALGenerator()
                    .getmoduleProgressionJSON(moduleInstance,
							this.getServerURI() + PATIENT_RESOURCE_PATH);
	    }
	    
	    protected String toEntity(ModuleActivityList instance) {
	       // ModuleInstance moduleInstance = (ModuleInstance) instance;
	        return HALHelperFactory
	                .getHALGenerator()
                    .getmoduleActivitiesJSON(instance,
							this.getServerURI() + PATIENT_RESOURCE_PATH);
	    }
}
