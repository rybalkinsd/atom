package ru.atom.dbhackaton.server.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import ru.atom.dbhackaton.server.model.Token;
import ru.atom.dbhackaton.server.model.User;

import java.util.List;

public class UserDao {
    private static final Logger log = LogManager.getLogger(UserDao.class);

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    private UserDao(){}

    public List<User> getAll(Session session) {
        return session.createCriteria(User.class).list();
    }

    public void insert(Session session, User user) {
        session.saveOrUpdate(user);
    }
    
    public void remove(Session session, Token token) {
    	
    }

    public User getByName(Session session, String name) {
        return (User) session
                .createQuery("from user where login = :name")
                .setParameter("login", name)
                .uniqueResult();
    }

    /*
    NO MORE MANUAL MAPPING!

    private static User mapToUser(ResultSet rs) throws SQLException {
        return new User()
                .setId(rs.getInt("id"))
                .setLogin(rs.getString("login"));
    }
    */
}
