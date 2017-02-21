package accountserver.authDAO;

import jersey.repackaged.com.google.common.base.Joiner;
import accountserver.authInfo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 05.11.2016.
 */
public class UserDAO implements AuthDAO<User>  {
    private static final Logger log = LogManager.getLogger(AuthDAO.class);

    @Override
    public List<User> getAll() {
        return DbConnection.selectTransaction("from User");
    }

    @Override
    public List<User> getAllWhere(String... hqlCondidtions) {
        String fullCondition = Joiner.on(" and ").join(Arrays.asList(hqlCondidtions));
        return DbConnection.selectTransaction("from User where " + fullCondition);
    }

    @Override
    public boolean insert(User user) {
        return DbConnection.insertTransaction(user);
    }

    @Override
    public boolean delete(User user) {
        return DbConnection.deleteTransaction(getAllWhere("login = \'" + user.getLogin() + "\'",
                "password = \'" + user.getPassword() + "\'").get(0));
    }

    public boolean update(User user){
        return DbConnection.updateTransaction(user);
    }

    public User getUserById(int userId){
        return getAllWhere("id = \'" + userId + "\'").get(0);
    }

    public int getUserId(User user){
        return getAllWhere("login = \'" + user.getLogin() + "\'",
                "password = \'" + user.getPassword() + "\'").get(0).getId();
    }

    public int getUserIdByLogin(String login){
        return getAllWhere("login = \'" + login + "\'").get(0).getId();
    }

    public User getUserByLoginData(String login, String password){
        List<User> requestResult = getAllWhere("login = \'" + login + "\'", "password = \'" + password + "\'");
        return requestResult.isEmpty() ? null : requestResult.get(0);
    }

    public boolean isNameInUse(String name){
        if(this.getAllWhere("login = \'" + name+"\'").isEmpty()) {
            return false;
        }
        return true;
    }
}
