package accountserver.misc;

/**
 * Created by eugene on 10/25/16.
 */
public class CredentialsPolicy {
    public static boolean checkLogin(String login){
        return login != null;
    }

    public static boolean checkPassword(String password){
        return password != null;
    }
}
