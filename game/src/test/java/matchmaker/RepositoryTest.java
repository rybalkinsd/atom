package matchmaker;

import matchmaker.monitoring.MonitoringController;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by imakarycheva on 04.04.18.
 */
public class RepositoryTest {

    private static JdbcTemplate jdbcTemplate;

    @BeforeClass
    public static void setUp() {
        DataSource db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .addScript("monitoring_test.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(db);
    }

    @Test
    public void getRankForNewUserTest() {
        MatchMakerRepository repository = new MatchMakerRepository(jdbcTemplate);
        String newLogin = "NEW_USER";
        int rank = repository.getUserRank(newLogin);
        Assert.assertTrue(rank == 0);
        Object[] param = {newLogin};
        Integer userCount = jdbcTemplate.query("SELECT count(*) as count FROM mm.users WHERE login = ?", param,
            (rs, num) -> rs.getInt("count"))
                .get(0);
        Assert.assertTrue(userCount.equals(1));
        jdbcTemplate.update("DELETE from mm.users u WHERE u.login = 'NEW_USER'");
    }

    @Test
    public void getRankForExistentUserTest() {
        MatchMakerRepository repository = new MatchMakerRepository(jdbcTemplate);
        String existentLogin = "User2";
        int expectedRank = 15;
        int gotRank = repository.getUserRank(existentLogin);
        Assert.assertEquals(String.format("Expected %d, got %d", expectedRank, gotRank), expectedRank, gotRank);
    }

    @Test
    public void saveSessionTest() {
        MatchMakerRepository repository = new MatchMakerRepository(jdbcTemplate);
        String[] logins = {"saveSessionTest1", "saveSessionTest2", "saveSessionTest3"};
        long sessionId = 404L;

        for (String login : logins) {
            repository.saveLogin(login);
        }

        repository.saveGameSession(sessionId, logins);

        Object[] param = {sessionId};
        Integer sessionCount = jdbcTemplate.query("SELECT count(*) as count FROM mm.game_sessions WHERE id = ?",
                param, (rs, num) -> rs.getInt("count"))
                .get(0);
        Assert.assertTrue(sessionCount.equals(1));

        List<String> gotLogins = jdbcTemplate.query("SELECT login FROM mm.users t " +
                        "JOIN mm.game_sessions_to_users t2 ON t.id = t2.user_id WHERE t2.game_session_id = ? " +
                        "ORDER BY t.login", param, (rs, num) -> rs.getString("login"));
        Assert.assertEquals(logins.length, gotLogins.size());
        for (int i = 0; i < logins.length; i++) {
            Assert.assertEquals(logins[i], gotLogins.get(i));
        }

        jdbcTemplate.update("DELETE from mm.game_sessions s WHERE s.id = 404");
        jdbcTemplate.update("DELETE from mm.users u WHERE u.login = 'saveSessionTest1'");
        jdbcTemplate.update("DELETE from mm.users u WHERE u.login = 'saveSessionTest2'");
        jdbcTemplate.update("DELETE from mm.users u WHERE u.login = 'saveSessionTest3'");
    }

    @Test
    public void getUsersTest() {
        MonitoringController controller = new MonitoringController(new MatchMakerRepository(jdbcTemplate));
        String expected = "<h2>Список игроков</h2><table><tr><th>ID</th><th>Логин</th>" +
                "<th>Ранг</th><th>Игр сыграно</th></tr><tr><th>1</th><th>User1</th><th>0</th><th>0</th></tr>" +
                "<tr><th>2</th><th>User2</th><th>15</th><th>1</th></tr></table>";
        String got = controller.getUsers().getBody();
        Assert.assertEquals(String.format("Expected\n%s\n\nGot\n%s", expected, got), expected, got);
    }

    @Test
    public void getSessionsTest() {
        MonitoringController controller = new MonitoringController(new MatchMakerRepository(jdbcTemplate));
        String expected = "<h2>Список сессий</h2><table><tr><th>ID</th><th>Дата начала</th><th>Игроки</th></tr>" +
                "<tr><th>1</th><th>2018-04-08</th><th>User2</th></tr></table>";
        String got = controller.getSessions().getBody();
        Assert.assertEquals(String.format("Expected\n%s\n\nGot\n%s", expected, got), expected, got);
    }
}
