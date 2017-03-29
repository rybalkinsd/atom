package accounts;

/**
 * Created by ilnur on 29.03.17.
 */
public class UserProfile {
    private final String login;
    private final String pass;



    public UserProfile(String login, String pass) {
        this.login = login;
        this.pass = pass;

    }

    public UserProfile(String login) {
        this.login = login;
        this.pass = login;

    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

}
