package edu.asu.heal.core.api.service;

import java.lang.reflect.Constructor;

public class HealServiceSingleton {
	
	private static HealService instance;
	
	private HealServiceSingleton() {
		
	}
	
	public static HealService getInstance(String className) {
		if(instance == null) {
			return HealServiceSingleton.initializeService(className);
		}
		return instance;
	}
	
	private static HealService initializeService(String serviceClassName) {
        try {

            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            instance = (HealService) serviceClassConstructor.newInstance();

        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }

        return instance;
    }

}
