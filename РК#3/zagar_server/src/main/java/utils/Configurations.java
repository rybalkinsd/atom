package utils;

import java.io.IOException;
import java.util.List;

/**
 * Created by svuatoslav on 11/28/16.
 */
public class Configurations {
    private static PropertiesReader reader;

    static {
        try {
            reader = new PropertiesReader("src/main/resources/config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getIntProperty(String name)
    {
        return reader.getIntProperty(name);
    }

    public static String getStringProperty(String name)
    {
        return reader.getStringProperty(name);
    }

    public static List<String> getListProperty(String name)
    {
        return reader.getListProperty(name);
    }
}
