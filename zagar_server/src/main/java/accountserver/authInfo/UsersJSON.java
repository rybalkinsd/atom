package accountserver.authInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by User on 24.10.2016.
 */
public class UsersJSON {

    public static final ObjectMapper mapper = new ObjectMapper();
    public List<User> users;
    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    public String writeJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    public UsersJSON(List<User> users){
        this.users = users;
    }
}
