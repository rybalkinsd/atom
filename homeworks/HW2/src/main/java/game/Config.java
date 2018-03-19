package atom.game;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Config {
    private final org.slf4j.Logger log = org.slf4j
                                            .LoggerFactory
                                            .getLogger(Config.class);
    private final String defaultFilename = "config.txt";
    private String filename;
    private HashMap<String, String> configMap;

    public Config() {
        filename = defaultFilename;
        configMap = new HashMap<String, String>();
    }

    public Config(String filename) {
        this.filename = filename;
        configMap = new HashMap<String, String>();
    }

    public HashMap<String, String> getConfigMap() {
        return configMap;
    }

    public void load() throws ConfigException {
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filename);
            bufferedReader = new BufferedReader(new FileReader(file));
            String currLine = null;
            while ((currLine = bufferedReader.readLine()) != null) {
                String[] splittedLine = currLine.split("=");
                if (splittedLine.length != 2)
                    throw new ConfigException("Config lines must have two elements separated only one symbol '='");
                configMap.put(splittedLine[0], splittedLine[1]);
            }
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException in load, config filename = {}", filename);
            throw new ConfigException(e);
        } catch (IOException e) {
            log.error("IOException in load, config filename = {}", filename);
            throw new ConfigException(e);
        } catch (ConfigException e) {
            log.error("ConfigException in load, config filename = {}", filename);
            throw e;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public String getStringByName(String name) throws ConfigException {
        if (!configMap.containsKey(name)) {
            log.error("ConfigException in getStringByName.");
            throw new ConfigException("Config don't contain key with name: " + name);
        }
        return configMap.get(name);
    }

    public int getIntByName(String name) throws ConfigException {
        if (!configMap.containsKey(name)) {
            log.error("ConfigException in getIntByName.");
            throw new ConfigException("Config don't contain key with name: " + name);
        }

        int result;
        try {
            result = Integer.parseInt(configMap.get(name));
        } catch (NumberFormatException e) {
            log.error("NumberFormatException in getIntByName");
            throw new ConfigException(e);
        }

        return result;
    }
}
