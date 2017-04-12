package io.github.rentgen94;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import static io.github.rentgen94.MyLogger.getLog;

/**
 * Created by Western-Co on 27.03.2017.
 */
public class WorkWithProperties {
    private static Properties properties = new Properties();
    private static ResourceBundle strProp = ResourceBundle.getBundle("strings");

    static {
        try {
            properties.load(WorkWithProperties
                    .class
                    .getClassLoader()
                    .getResourceAsStream("config.properties")
            );
        } catch (IOException e) {
            getLog().error("Error when load properties: " + e.getMessage());
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    public static ResourceBundle getStrBundle() {
        return strProp;
    }
}
