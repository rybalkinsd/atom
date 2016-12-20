package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by svuatoslav on 11/28/16.
 */
public final class Configurations {
    private final static Logger log = LogManager.getLogger(Configurations.class);

    private static PropertiesReader reader=null;

    public static void setConfigs(String path)
    {
        reader = PropertiesReader.getInstance(path);
    }

    public static boolean isSet()
    {
        return reader!=null;
    }

    public static Integer getIntProperty(String name)
    {
        if(reader!=null)
            return reader.getIntProperty(name);
        return null;
    }

    public static String getStringProperty(String name)
    {
        if(reader!=null)
            return reader.getStringProperty(name);
        return null;
    }

    public static List<String> getListProperty(String name)
    {
        if(reader!=null)
            return reader.getListProperty(name);
        return null;
    }
}
