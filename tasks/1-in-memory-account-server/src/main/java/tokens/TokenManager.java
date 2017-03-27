package tokens;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import services.User;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by kinetik on 26.03.17.
 */
@Authorized
@Provider
public class TokenManager implements ContainerRequestFilter {
    private static final ConcurrentHashMap<Long, User> tokenInserted =
            new ConcurrentHashMap<>();

    public TokenManager() {
    }

    public Token getNewToken(User user) {
        Long tokenValue;
        while (tokenInserted.containsKey(tokenValue = ThreadLocalRandom.current().nextLong())){ }
        tokenInserted.put(tokenValue, user);
        user.setToken(new Token(tokenValue));
        return new Token(tokenValue);
    }

    public boolean validateToken(String tokenIn) {
        try {
            Long token = Long.parseLong(tokenIn);
            if (tokenInserted.get(token) != null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public String logout(Long token) {
        User user = tokenInserted.get(token);
        tokenInserted.remove(token);
        return user.getName();
    }

    public User getUserByToken(Long token) {
        return tokenInserted.get(token);
    }

    public String getLoginUsers() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<String> userLogins = new ArrayList<>();
        for (User user: tokenInserted.values()) {
            userLogins.add(user.getName());
        }
        HashMap<String, ArrayList<String>> forJson = new HashMap<>();
        forJson.put("Users", userLogins);
        String jsonString = mapper.writeValueAsString(forJson);
        return jsonString;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        String token = authorizationHeader.trim();

        try {
            validateToken(token);
        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
