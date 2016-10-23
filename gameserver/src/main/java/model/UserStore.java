package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore {

    private static UserStore instance;

    private static final Logger log = LogManager.getLogger(TokenStore.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    private ConcurrentHashMap<String, String> credentials;

    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    private UserStore(){
        credentials = new ConcurrentHashMap<>();
    }

    public static UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    @NotNull
    public ConcurrentHashMap<String, String> getUsers() {

        return this.credentials;
    }


    @Nullable
    public String getPassword(String userName) {
        return this.credentials.get(userName);
    }

    @Nullable
    public User putIfAbsent(String name, String password) {
        String result = this.credentials.putIfAbsent(name, password);
        User user = null;
        if (result != null) user = new User(name,result);
        return user;
    }

    public boolean remove(String key) {
        String value = this.credentials.remove(key);
        if (value != null) return true;
        return false;
    }

    public boolean put(String name, String password) {
        String result = this.credentials.put(name, password);
        if (result == null) return true;
        return false;
    }

    public String writeJSONNames()  throws JsonProcessingException {
        List<String> array = this.getUserNames();
        return mapper.writeValueAsString(array);
    }

    private List<String> getUserNames() {
        List<String> result = new ArrayList<>();
        for (Enumeration<String> middle = credentials.keys(); middle.hasMoreElements();) {
            result.add(middle.nextElement());
        }
        return result;
    }

    public static List readJsonNames(String json) throws IOException {
        return mapper.readValue(json, List.class);
    }

}
