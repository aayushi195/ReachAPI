package edu.asu.heal.core.api.service;
import java.lang.reflect.Constructor;

public class HealServiceSingleton {

    private static IHealService instance;

    private HealServiceSingleton() {

    }

        public static IHealService getInstance(String className){
            if(instance==null){
                return HealServiceSingleton.initializeService(className);
            }
            return instance;
        }

        private static IHealService initializeService(String serviceClassName){
            try {

                Class<?> serviceClass = Class.forName(serviceClassName);
                Constructor<?> serviceClassConstructor = serviceClass.getConstructor();
                instance = (IHealService) serviceClassConstructor.newInstance();
            } catch (ClassNotFoundException ce) {
                System.out.println(ce.getMessage());
            } catch (Exception ex) {
                System.out.println("Exception occurred: " + ex.getMessage());
            }
            return instance;
        }

    }