package accounts;

/**
 * Created by ilnur on 29.03.17.
 */
public class UserProfile {
    private final String login;
    private final String pass;
    private String userToken;

    public String getUtoken() {
        return userToken;
    }

    public void setUtoken() {
        this.userToken = Integer.toString(login.hashCode());
    }

    public UserProfile(String login, String pass) {
        this.login = login;
        this.pass = pass;
        this.setUtoken();
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

}
