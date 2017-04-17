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
    public void nameEquality() {
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
        } catch (ConstraintViolationException ex) {
            Assert.assertTrue(ex instanceof ConstraintViolationException);
        }

        Assert.assertNotEquals(timestampTwo, getByName(login));
        Assert.assertEquals(timestamp, getByName(login));

        dropUser(getByName(login));
    }

    @Test
    public void loginTest() {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd1", timestamp);
        insert(user);

        LoginEntity loginUser = new LoginEntity();
        loginUser.setId(getByName(login).getUserId());
        loginUser.setToken("10L");
        saveLogin(loginUser);

        Assert.assertEquals(getByName(login).getUserId(), getLoginByName(login).getId());
        Assert.assertEquals("10L", getLoginByName(login).getToken());
        Assert.assertEquals(getByName(login).getUserId(), getByToken(10L).getId());
        Assert.assertEquals("10L", getByToken(10L).getToken());
        Assert.assertEquals(login, getLoginByName(login).getUser().getLogin());
        Assert.assertEquals(login, getByToken(10L).getUser().getLogin());

        logoutToken(login);

        Assert.assertEquals(null, getLoginByName(login));
        Assert.assertEquals(null, getByToken(10L));
    }

    @Test
    public void badInData() {
        String login = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());

        Assert.assertEquals(null, getByName(login));

        try {
            LoginEntity loginUser = new LoginEntity();
            loginUser.setId(999999);
            loginUser.setToken("10L");
            saveLogin(loginUser);
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof ConstraintViolationException);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        RegistredEntity user = new RegistredEntity(login, "pwd1", timestamp);
        insert(user);

        LoginEntity loginUser = new LoginEntity();
        loginUser.setId(getByName(login).getUserId());
        loginUser.setToken("10L");
        saveLogin(loginUser);

        try {
            LoginEntity newLoginUser = new LoginEntity();
            newLoginUser.setId(getByName(login).getUserId());
            newLoginUser.setToken("11L");
            saveLogin(newLoginUser);
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof ConstraintViolationException);
        }

        String loginTwo = "dbTest_" + Long.toString(ThreadLocalRandom.current().nextLong());
        Timestamp timestampTwo = new Timestamp(System.currentTimeMillis());
        RegistredEntity userTwo = new RegistredEntity(loginTwo, "pwd1", timestampTwo);
        insert(userTwo);

        try {
            LoginEntity anotherLoginUser = new LoginEntity();
            anotherLoginUser.setId(getByName(loginTwo).getUserId());
            anotherLoginUser.setToken("10L");
            saveLogin(anotherLoginUser);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ConstraintViolationException);
        }

        logoutToken(login);
        dropUser(user);
        dropUser(userTwo);
    }

}
