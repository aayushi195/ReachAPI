package edu.asu.heal.reachv3.api.service;

import java.util.List;

import edu.asu.heal.core.api.models.Activity;
import edu.asu.heal.core.api.service.HealService;

public interface IReachService extends HealService {
	
	List<Activity> getSuggestedEmotionsActivities(int patientPin, String emotion, int intensity);

}
