package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class HealServiceFactory {

    private Properties properties;
    private IHealService _healImpl;
    private static HealServiceFactory healFactory;

    private HealServiceFactory(){
    	
    	 try {
             InputStream temp = HealServiceFactory.class.getResourceAsStream("healservice.properties");
             properties = new Properties();
             properties.load(temp);
         } catch (Exception e) {
             System.out.println("SOME ERROR IN LOADING DECORATOR PROPERTIES");
             e.printStackTrace();
         }
    }

    public static HealServiceFactory getFactory() {
        if(healFactory == null){
        	healFactory = new HealServiceFactory();
        }
        return healFactory;
    }

    public IHealService getTheService() {
    	if(_healImpl == null) {
    	    return healFactory.initializeService(properties.getProperty("healservice.classname"));
        }
    	return _healImpl;
        }

	private IHealService initializeService(String serviceClassName) {
        try {

            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            _healImpl = (IHealService) serviceClassConstructor.newInstance();

        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }

        return _healImpl;
    }
}
