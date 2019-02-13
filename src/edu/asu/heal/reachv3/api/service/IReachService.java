package edu.asu.heal.reachv3.api.service;

import java.util.List;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.service.IHealService;

public interface IReachService extends IHealService {
	
	List<Activity> getSuggestedEmotionsActivities(int patientPin, String emotion, int intensity);

}
