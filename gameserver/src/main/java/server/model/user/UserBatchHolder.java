package server.model.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserBatchHolder {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public UserBatchHolder() {
    }

    private List<User> users = new ArrayList<>();

    public UserBatchHolder(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public static UserBatchHolder readJson(final String json) throws IOException {
        return MAPPER.readValue(json, UserBatchHolder.class);
    }

    public String writeJson() throws JsonProcessingException {
        return MAPPER.writeValueAsString(this);
    }

}
