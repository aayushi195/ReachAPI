package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.util.Properties;

public class DecoratorFactory {

	private static HealService _theService;

    private static Properties properties;

    static {
        try {
            InputStream temp = DecoratorFactory.class.getResourceAsStream("decorator.properties");
            properties = new Properties();
            properties.load(temp);
        } catch (Exception e) {
            System.out.println("SOME ERROR IN LOADING SERVICE PROPERTIES");
            e.printStackTrace();
        }
    }

    public static HealService getTheService() {
        if (_theService == null) {
            return HealServiceSingleton.initializeService(properties.getProperty("healservice.classname"));
        }

        return _theService;
    }

//    public static HealService initializeService(String serviceClassName) {
//        try {
//
//            Class<?> serviceClass = Class.forName(serviceClassName);
//            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
//            _theService = (HealService) serviceClassConstructor.newInstance();
//
//        } catch (ClassNotFoundException ce) {
//            System.out.println(ce.getMessage());
//        } catch (Exception ex) {
//            System.out.println("Exception occurred: " + ex.getMessage());
//        }
//
//        return _theService;
//    }

}
