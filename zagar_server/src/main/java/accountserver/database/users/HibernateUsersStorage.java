package accountserver.database.users;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import utils.HibernateHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by xakep666 on 03.11.16.
 * <p>
 * Stores {@link User} in database using Hibernate framework
 */
public class HibernateUsersStorage implements UserDao {
    private static final Logger log = LogManager.getLogger(HibernateUsersStorage.class);

    public HibernateUsersStorage() {
        log.info("Initialized Hibernate users storage");
    }

    @Override
    public void addUser(@NotNull User user) {
        log.info("Adding user " + user + " to database");
        HibernateHelper.doTransactional(session -> session.save(user));
    }

    @Override
    public @Nullable User getUserById(int id) {
        log.info("Searching user with id " + id);
        List response = HibernateHelper.selectTransactional(session ->
                session.createQuery("from User u where u.id = :id")
                        .setParameter("id", id)
                        .list());
        if (response == null || response.isEmpty()) return null;
        return (User) response.get(0);
    }

    @Override
    public @Nullable User getUserByName(@NotNull String name) {
        log.info("Searching user with name " + name);
        List response = HibernateHelper.selectTransactional(session ->
                session.createQuery("from User u where u.name = :name")
                        .setParameter("name", name)
                        .list());
        if (response == null || response.isEmpty()) return null;
        return (User) response.get(0);
    }

    @Override
    public void removeUser(@NotNull User user) {
        log.info("Removing user " + user);
        HibernateHelper.doTransactional(session -> session.createQuery("delete from User u where u.id = :id")
                .setParameter("id", user.getId())
                .executeUpdate());
    }

    @Override
    public @NotNull List<User> getAllUsers() {
        log.info("Getting all users");
        List resp = HibernateHelper.selectTransactional(session -> session.createQuery("from User").list());
        if (resp == null || !(resp.get(0) instanceof User)) {
            log.error("Could not retrieve users");
            return new LinkedList<>();
        }

        @SuppressWarnings("unchecked")
        List<User> ret = (List<User>) resp;
        return ret;
    }

    @Override
    public void updateUser(@NotNull User user) {
        log.info("Updating user" + user + "record");
        HibernateHelper.doTransactional(session -> {
            session.update(user);
            return 0;
        });
    }
}
