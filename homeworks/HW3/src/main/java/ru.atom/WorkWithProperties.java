package ru.atom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class WorkWithProperties {
    private static final Logger log = LogManager.getLogger(WorkWithProperties.class);
    private static Properties properties = new Properties();
    //private static ResourceBundle strProp = ResourceBundle.getBundle("strings");

    static {
        try {
            properties.load(WorkWithProperties
                    .class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")
            );
        } catch (IOException e) {
            log.error("Error when load properties: " + e.getMessage());
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    //public static ResourceBundle getStrBundle() {
    //    return strProp;
    //}
}
