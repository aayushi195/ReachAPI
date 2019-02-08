package edu.asu.heal.core.api.service.SuggestedActivityiesMappingService;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Properties;

import edu.asu.heal.core.api.dao.DAO;
import edu.asu.heal.core.api.dao.DAOFactory;

public class MappingFactory {

	private static Properties _properties;

    private static MappingInterface _theMpper;

    private MappingFactory() {}

    public static MappingInterface getTheMapper() throws Exception{
        if (_theMpper == null) {
            throw new Exception("Mapper not properly initialized");
        }
        return _theMpper;
    }

    public static Properties getMapperProperties() {
        return _properties;
    }

    static {
        _properties = new Properties();
        try {
            InputStream propFile = MappingFactory.class.getResourceAsStream("mapper.properties");
            _properties.load(propFile);
            propFile.close();

            String mapperClass = _properties.getProperty("mapper.class");

            if (mapperClass != null) {
                Class<?> mapper = Class.forName(mapperClass);
                Constructor<?> constructor = mapper.getConstructor();
                _theMpper = (MappingInterface) constructor.newInstance();
            }
        } catch (Throwable t) {
            t.printStackTrace();
            try {
                throw new Exception(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
