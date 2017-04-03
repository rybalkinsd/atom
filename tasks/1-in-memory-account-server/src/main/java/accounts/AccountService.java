package accounts;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ilnur on 29.03.17.
 */


public class AccountService {
    private final Map<String , UserProfile> tokenToProfile;  //авторизованные пользователи
    private final Map<String, UserProfile> loginToProfile;  //зареганые пользователи
    private  List<String> usersList;                            //Лист логинов


    public AccountService() {
        loginToProfile = new HashMap<>();
        tokenToProfile = new HashMap<>();
        usersList = new LinkedList<>();
    }

    public void addNewUser(UserProfile userProfile) {
        usersList.add(userProfile.getLogin());
       // tokenToProfile.put(userProfile.getUtoken(), userProfile);
        loginToProfile.put(userProfile.getLogin(),userProfile);
    }

    public UserProfile getUserByLogin(String login) {
        if (loginToProfile.containsKey(login)) {
            return loginToProfile.get(login); }
        else
            return null;
    }

    public UserProfile getUserByToken(String token) {
        return tokenToProfile.get(token);
    }


    public void deleteUser(String token){
        usersList.remove(tokenToProfile.get(token));
        loginToProfile.remove(tokenToProfile.get(token).getLogin());
        //tokenToProfile.remove(token);
    }

    public boolean isActive(UserProfile userProfile){
        if (tokenToProfile.containsKey(userProfile.getUtoken())) {
            return true;
        } else {
            return false;
        }
    }
    public List<String> getUsers() {
        return usersList;
    }

    public void addSession(UserProfile thisUP) {
        tokenToProfile.put(thisUP.getUtoken(),thisUP);
    }

    public void deleteSession(String token) {
        tokenToProfile.remove(token);
    }
}
