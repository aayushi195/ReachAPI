package edu.asu.heal.reachv3.api.service.SuggestedActivityiesMappingServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.asu.heal.core.api.dao.impl.MongoDBDAO;
import edu.asu.heal.core.api.service.SuggestedActivityiesMappingService.MappingInterface;

public class ReachEmotionIntensityMappingImpl implements MappingInterface {

	public ReachEmotionIntensityMappingImpl() {
		
	}


	@Override
	public Object intensityMappingToDifficultyLevel(int intensity) {

		String intensityValue = null ;
		try {
			Properties properties1 = new Properties();
			properties1.load(ReachEmotionIntensityMappingImpl.class.getResourceAsStream("emotions.properties"));
			if(		( intensity >= (Integer.parseInt(properties1.getProperty("easy.low")) ) ) &&
					( intensity <= (Integer.parseInt(properties1.getProperty("easy.high")))) 
					){
				intensityValue="easy";
			} else if(		( intensity >= (Integer.parseInt(properties1.getProperty("medium.low")) ) ) &&
					( intensity <= (Integer.parseInt(properties1.getProperty("medium.high")))) 
					){
				intensityValue="medium";
			}else if(		( intensity >= (Integer.parseInt(properties1.getProperty("hard.low")) ) ) &&
					( intensity <= (Integer.parseInt(properties1.getProperty("hard.high")))) 
					){
				intensityValue="hard";
			}

		}catch (IOException e){
			e.printStackTrace();
		}
		if(intensityValue != null) {
			return intensityValue;
		}else
			return null;

	}

}
