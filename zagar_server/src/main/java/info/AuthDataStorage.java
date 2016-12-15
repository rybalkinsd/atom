package info;

import dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.constraints.NotNull;
import java.sql.SQLClientInfoException;

public class AuthDataStorage{
    private static final Logger log = LogManager.getLogger(AuthDataStorage.class);
    private static UserDao credentials;

    static {
        credentials = new UserDao();
        try {
            registerNewUser("admin", "admin");
        }
        catch(Exception e){
            log.info("Can't add user");
        }
    }

    public static void registerNewUser(@NotNull String user, @NotNull String password) throws Exception{
        if (!credentials.getAllWhere("name = '"+user+"'").isEmpty())
            throw new SQLClientInfoException();
        credentials.insert(new User(user, password));
    }

    public static boolean authenticate(String user, String password) throws Exception {
        return credentials.passwordIsTrue(user, password);
    }
}