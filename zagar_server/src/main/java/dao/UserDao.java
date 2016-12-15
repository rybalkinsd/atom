package dao;

import jersey.repackaged.com.google.common.base.Joiner;
import info.User;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

public class UserDao implements Dao<User> {
    @Override
    public List<User> getAll(){
        return Database.selectTransactional(session -> session.createQuery("from User").list());
    }

    @Override
    public List<User> getAllWhere(String... hqlConditions){
        String totalCondition = Joiner.on(" and ").join(Arrays.asList(hqlConditions));
        return Database.selectTransactional(session ->session.createQuery("from User where "
                + totalCondition).list());
    }

    @Override
    public void insert(User user){
        Database.doTransactional(session -> session.save(user));
    }

    public boolean passwordIsTrue(@NotNull String userName, @NotNull String password){
        try {
            List<User> user = getAllWhere("name = '" + userName + "'");
            return password.equals(user.get(0).getPassword());
        } catch (Exception e) {
            return false;
        }
    }
}