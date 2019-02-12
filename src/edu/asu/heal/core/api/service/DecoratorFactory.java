package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class DecoratorFactory {

    private static Properties properties;
    private static HealService _coreImpl;
    
    static {
        try {
            InputStream temp = DecoratorFactory.class.getResourceAsStream("decorator.properties");
            properties = new Properties();
            properties.load(temp);
        } catch (Exception e) {
            System.out.println("SOME ERROR IN LOADING DECORATOR PROPERTIES");
            e.printStackTrace();
        }
    }

    public static HealService getTheService() {
    	if(_coreImpl == null)
            return DecoratorFactory.initializeService(properties.getProperty("healserviceImpl.classname"));
    	return _coreImpl;
        }

	private static HealService initializeService(String serviceClassName) {
        try {

            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            _coreImpl = (HealService) serviceClassConstructor.newInstance();

        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }

        return _coreImpl;
    }
}
