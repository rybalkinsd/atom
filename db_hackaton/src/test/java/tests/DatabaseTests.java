package tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernate.RegistredEntity;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static ru.atom.dbhackaton.model.TokenStorage.*;
import static ru.atom.dbhackaton.model.UserStorage.dropUser;
import static ru.atom.dbhackaton.model.UserStorage.getByName;
import static ru.atom.dbhackaton.model.UserStorage.insert;

/**
 * Created by kinetik on 17.04.17.
 */
@FixMethodOrder(MethodSorters.JVM)
public class DatabaseTests {

    @Test
    public void userInsertion() {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd", timestamp);
        insert(user);

        Assert.assertEquals(user.getLogin(), getByName(login).getLogin());
        Assert.assertEquals(user.getRegdate(), getByName(login).getRegdate());

        dropUser(getByName(login));
    }

    @Test
    public void userDropper() {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd", timestamp);
        insert(user);
        RegistredEntity userFromDb = getByName(login);

        Assert.assertEquals(user.getLogin(), userFromDb.getLogin());
        Assert.assertEquals(user.getRegdate(), userFromDb.getRegdate());

        dropUser(userFromDb);

        Assert.assertEquals(null, getByName(login));
    }

    @Test
    public void nameEquality() throws ClassNotFoundException {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd1", timestamp);
        insert(user);

        Assert.assertEquals(user.getLogin(), getByName(login).getLogin());
        Assert.assertEquals(timestamp, getByName(login).getRegdate());

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Timestamp timestampTwo = new Timestamp(System.currentTimeMillis());
        RegistredEntity userTwo = new RegistredEntity(login, "pwd2", timestampTwo);
        try {
            insert(userTwo);
        } catch (Exception ex) {
            Assert.assertTrue(Class.forName("org.hibernate.exception.ConstraintViolationException").equals(ex.getClass()));
        }

        Assert.assertNotEquals(timestampTwo, getByName(login).getRegdate());
        Assert.assertEquals(timestamp, getByName(login).getRegdate());

        dropUser(getByName(login));
    }

    @Test
    public void loginTest() {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd1", timestamp);
        Long token = ThreadLocalRandom.current().nextLong();
        insert(user);

        LoginEntity loginUser = new LoginEntity();
        loginUser.setId(getByName(login).getUserId());
        loginUser.setToken(Long.toString(token));
        loginUser.setUser(getByName(login));
        saveLogin(loginUser);

        Assert.assertEquals(getByName(login).getUserId(), getLoginByName(login).getId());
        Assert.assertEquals(Long.toString(token), getLoginByName(login).getToken());
        Assert.assertEquals(getByName(login).getUserId(), getByToken(token).getId());
        Assert.assertEquals(Long.toString(token), getByToken(token).getToken());
        Assert.assertEquals(login, getLoginByName(login).getUser().getLogin());
        Assert.assertEquals(login, getByToken(token).getUser().getLogin());

        logoutToken(login);

        Assert.assertEquals(null, getLoginByName(login));
        Assert.assertEquals(null, getByToken(10L));
    }

    @Test
    public void badInData() throws ClassNotFoundException {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));
        Long token = ThreadLocalRandom.current().nextLong();

        try {
            LoginEntity loginUser = new LoginEntity();
            loginUser.setId(999999);
            loginUser.setToken(Long.toString(token));
            saveLogin(loginUser);
        } catch (Exception ex) {
            Assert.assertTrue(Class.forName("javax.persistence.PersistenceException").equals(ex.getClass()));
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd1", timestamp);
        insert(user);

        LoginEntity loginUser = new LoginEntity();
        loginUser.setId(getByName(login).getUserId());
        loginUser.setToken(Long.toString(token));
        saveLogin(loginUser);

        try {
            LoginEntity newLoginUser = new LoginEntity();
            newLoginUser.setId(getByName(login).getUserId());
            newLoginUser.setToken(Long.toString(token+1));
            saveLogin(newLoginUser);
        } catch (Exception ex) {
            Assert.assertTrue(Class.forName("javax.persistence.PersistenceException").equals(ex.getClass()));
        }

        String loginTwo = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());
        Timestamp timestampTwo = new Timestamp(System.currentTimeMillis());
        RegistredEntity userTwo = new RegistredEntity(loginTwo, "pwd1", timestampTwo);
        insert(userTwo);

        try {
            LoginEntity anotherLoginUser = new LoginEntity();
            anotherLoginUser.setId(getByName(loginTwo).getUserId());
            anotherLoginUser.setToken(Long.toString(token));
            saveLogin(anotherLoginUser);
        } catch (Exception e) {
            Assert.assertTrue(Class.forName("javax.persistence.PersistenceException").equals(e.getClass()));
        }

        logoutToken(login);
        dropUser(user);
        dropUser(userTwo);
    }

}
