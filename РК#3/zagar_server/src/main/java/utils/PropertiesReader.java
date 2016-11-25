package utils;

import main.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class PropertiesReader {
    private final Properties properties;

    public PropertiesReader(String propertiesFile) throws IOException {
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        properties = new Properties();
        properties.load(in);
    }

    public int getIntProperty(String name)
    {
        String s = properties.getProperty(name);
        int n;
        if ((n = s.indexOf('#')) >= 0)
            s = s.substring(0, n).trim();
        s=s.trim();
        return Integer.parseInt(s);
    }

    public String getStringProperty(String name)
    {
        String s = properties.getProperty(name);
        int n;
        if ((n = s.indexOf('#')) >= 0)
            s = s.substring(0, n);
        s=s.trim();
        return s;
    }

    public List<String> getListProperty(String name)
    {
        List<String> res=new LinkedList<>();
        String s = properties.getProperty(name);
        int n;
        if((n=s.indexOf('#'))>=0)
            s=s.substring(0,n);
        do {
            String i;
            n=s.indexOf(',');
            if(n>0) {
                i = s.substring(0, n);
                s = s.substring(n + 1);
            }
            else
                i=s;
            i=i.trim();
            if (!i.isEmpty())
                res.add(i);
        } while (n>0);
        return res;
    }
}
