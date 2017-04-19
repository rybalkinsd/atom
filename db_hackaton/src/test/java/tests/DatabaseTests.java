package tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.atom.dbhackaton.hibernate.LoginEntity;
import ru.atom.dbhackaton.hibernate.RegistredEntity;
import ru.atom.dbhackaton.mm.UserGameResult;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static ru.atom.dbhackaton.mm.UserGameResultDao.getByGameId;
import static ru.atom.dbhackaton.mm.UserGameResultDao.saveGameResults;
import static ru.atom.dbhackaton.model.TokenStorage.*;
import static ru.atom.dbhackaton.model.UserStorage.*;

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
            Assert.assertTrue(Class.forName("javax.persistence.PersistenceException").equals(ex.getClass()));
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
        Assert.assertEquals(null, getByToken(token));
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

    @Test
    public void mmDbTest() throws ClassNotFoundException {
        String login = "dbTester_" + Long.toString(ThreadLocalRandom.current().nextLong());
        Integer userId = ThreadLocalRandom.current().nextInt();
        Integer gameId = ThreadLocalRandom.current().nextInt();

        Assert.assertEquals(null, getByName(login));
        Assert.assertEquals(null, getById(userId));
        Assert.assertEquals(null, getByGameId(gameId));

        RegistredEntity user = new RegistredEntity(login, "pwd", new Timestamp(System.currentTimeMillis()));
        UserGameResult result = new UserGameResult(gameId, user, 2);
        try {
            saveGameResults(result);
        } catch (Exception e) {
            Assert.assertTrue(Class.forName("java.lang.IllegalStateException").equals(e.getClass()));
        }
        insert(user);
        saveGameResults(result);
        List<UserGameResult> gameResults = getByGameId(gameId);

        Assert.assertEquals(login, gameResults.get(0).getUser().getLogin());
        Assert.assertEquals(gameId, gameResults.get(0).getGameID());

        dropUser(getByName(user.getLogin()));

    }
}
