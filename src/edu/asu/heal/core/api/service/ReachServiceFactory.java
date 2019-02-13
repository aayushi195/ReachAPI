package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

public class ReachServiceFactory {

    /* TODO -- Doubt - @dpurbey -- discuss with team & Dr.G
    *
    * here returning the service instance is singleton instance, which makes me wonder, if we are to deploy
    * for example 3 API from a single WAR, then how to achieve service instantiation of 2 other applications
    *
    * We will have to change this from singleton to something else where you can have multiple instances of service,
    * each for a specific applications
    *
    * */

    // service bound to IHealService should be instantiated
    private IHealService _reachImpl;
    private Properties properties;
    private static ReachServiceFactory reachFactory;

    private ReachServiceFactory(){
    	
   	 try {
            InputStream temp = ReachServiceFactory.class.getResourceAsStream("reachservice.properties");
            properties = new Properties();
            properties.load(temp);
        } catch (Exception e) {
            System.out.println("SOME ERROR IN LOADING DECORATOR PROPERTIES");
            e.printStackTrace();
        }
   }

   public static ReachServiceFactory getFactory(){
       if(reachFactory == null){
    	   reachFactory = new ReachServiceFactory();
       }
       return reachFactory;
   }

    public IHealService getTheService() {
        if (_reachImpl == null) {
          //  _theService = HealServiceSingleton.getInstance(properties.getProperty("ReachService.classname"));
        	_reachImpl = reachFactory.initializeService(properties.getProperty("reachservice.classname"));
        }

        return _reachImpl;
    }

    private IHealService initializeService(String serviceClassName) {
        try {
            Class<?> serviceClass = Class.forName(serviceClassName);
            Constructor<?> serviceClassConstructor = serviceClass.getConstructor(IHealService.class);
            _reachImpl = (IHealService) serviceClassConstructor.newInstance(HealServiceFactory.getFactory().getTheService());
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception occurred: reach factory " + ex.getMessage());
        }
        return _reachImpl;
    }

}
