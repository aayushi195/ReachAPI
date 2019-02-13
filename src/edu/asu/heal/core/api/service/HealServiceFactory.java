package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class HealServiceFactory {

    private static Properties properties;
    private IHealService _coreImpl;
    private static HealServiceFactory factory;

    private HealServiceFactory(){}

    private static HealServiceFactory getFactory(){

        if(factory == null){
            factory = new HealServiceFactory();
        }
        return factory;
    }

   static  {
        try {
            InputStream temp = HealServiceFactory.class.getResourceAsStream("HealService.properties");
            properties = new Properties();
            properties.load(temp);
        } catch (Exception e) {
            System.out.println("SOME ERROR IN LOADING DECORATOR PROPERTIES");
            e.printStackTrace();
        }
    }

    public IHealService getTheService() {
    	if(_coreImpl == null) {
    	    return HealServiceFactory.initializeService(properties.getProperty("HealService.classname"));
        }
    	return _coreImpl;
        }

	private IHealService initializeService(String serviceClassName) {
        try {

            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            _coreImpl = (IHealService) serviceClassConstructor.newInstance();

        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }

        return _coreImpl;
    }
}
