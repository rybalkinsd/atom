package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by svuatoslav on 11/26/16.
 */
public class PropertiesReader {
    private final Properties properties;

    @NotNull
    private final static Logger log = LogManager.getLogger(PropertiesReader.class);

    private PropertiesReader(String propertiesFile) throws IOException {
        InputStream in;
        in = new FileInputStream(propertiesFile);
        properties = new Properties();
        properties.load(in);
        in.close();
    }

    public static PropertiesReader getInstance(String propertiesFile)
    {
        PropertiesReader propertiesReader;
        try {
            propertiesReader = new PropertiesReader(propertiesFile);
        } catch (IOException e) {
            log.error("Failed to open config file",propertiesFile,e);
            return null;
        }
        return propertiesReader;
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
