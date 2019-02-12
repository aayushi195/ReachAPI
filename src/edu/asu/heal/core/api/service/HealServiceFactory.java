package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class HealServiceFactory {

    /* TODO -- Doubt - @dpurbey -- discuss with team & Dr.G
    *
    * here returning the service instance is singleton instance, which makes me wonder, if we are to deploy
    * for example 3 API from a single WAR, then how to achieve service instantiation of 2 other applications
    *
    * We will have to change this from singleton to something else where you can have multiple instances of service,
    * each for a specific applications
    *
    * */

    // service bound to HealService should be instantiated
    private static HealService _theService,_coreImpl;

    private static Properties properties;

    static {
        try {
            InputStream temp = HealServiceFactory.class.getResourceAsStream("service.properties");
            properties = new Properties();
            properties.load(temp);
        } catch (Exception e) {
            System.out.println("SOME ERROR IN LOADING SERVICE PROPERTIES");
            e.printStackTrace();
        }
    }

    public static HealService getTheService() {
        if (_theService == null) {
        	_coreImpl = DecoratorFactory.getTheService();
            _theService= HealServiceFactory.initializeService(properties.getProperty("healservice.classname"));
        }

        return _theService;
    }

    private static HealService initializeService(String serviceClassName) {
        try {
            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
            _theService = (HealService) serviceClassConstructor.newInstance(_coreImpl);
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
        return _theService;
    }

}
