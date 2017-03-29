package accounts;

import java.util.*;

/**
 * Created by ilnur on 29.03.17.
 */


public class AccountService {
    private final Map<String, UserProfile> loginToProfile;
    private final Map<String, UserProfile> sessionIdToProfile;
    private List<String> hs = new LinkedList<>();

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile) {
        hs.add(userProfile.getLogin());
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UserProfile getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UserProfile userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }

    public List<String> getusers() {
        return hs;
    }
}
