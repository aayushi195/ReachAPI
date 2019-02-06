package edu.asu.heal.core.api.service;

public abstract class AHealService implements HealService{
	
	private static HealService __service;


    public static HealService getTheService() {
        if (__service == null) {
        	__service = DecoratorFactory.getTheService();
        }

        return __service;
    }

}
