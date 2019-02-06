package edu.asu.heal.core.api.service;

import java.io.InputStream;
import java.util.Properties;

public class DecoratorFactory {

    private static Properties properties;

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
            return HealServiceSingleton.getInstance(properties.getProperty("healserviceImpl.classname"));
        }

}
