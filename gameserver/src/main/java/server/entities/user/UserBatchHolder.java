package server.entities.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBatchHolder {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    private List<User> users = new ArrayList<>();

    public UserBatchHolder(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public static UserBatchHolder readJson(String json) throws IOException {
        return mapper.readValue(json, UserBatchHolder.class);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

}
