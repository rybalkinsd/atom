package ru.atom.dbhackaton.server;

import okhttp3.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import ru.atom.dbhackaton.server.auth.Database;
import ru.atom.dbhackaton.server.auth.Token;
import ru.atom.dbhackaton.server.auth.User;
import ru.atom.dbhackaton.server.auth.UserDao;
import ru.atom.dbhackaton.server.mm.MatchMakerServer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MatchMakerTest {
    void deleteUserCascade(Session session, String name) {
        final User user = UserDao.getInstance().getByName(session, name);
        if (user == null) return;
        final int id = user.getId();
        Transaction txn = session.beginTransaction();
        session.createNativeQuery("delete from bomber.result where userId = :id")
                .setParameter("id", id)
                .executeUpdate();
        txn.commit();
        UserDao.getInstance().deleteByNameTxn(session, name);
    }

    @Test
    public void testServer() throws Exception {
        MatchMakerServer.serverRun();

        Session session = Database.session();
        UserDao ud = UserDao.getInstance();
        final Integer maxSessionObj = (Integer) session.createNativeQuery(
                "select max(gameId) from bomber.result")
                .uniqueResult();
        final int startSession = maxSessionObj == null ? 0 : maxSessionObj + 1;
        for (int i = 0; i < 10; i++) {
            final User user = new User("TestMMUser" + i, "tummpass");
            Token tok = new Token(user.name(), user.passwordHash());
            user.setToken(tok);
            deleteUserCascade(session, user.name());
            ud.insertTxn(session, user);
            Response joinedResponse = HttpClient.join(tok.value);
            assertEquals(200, joinedResponse.code());
            String url = joinedResponse.body().string();
            assertTrue(url.endsWith(Integer.toString(startSession + i / 4)));
        }
        Response invalidTokenJoinResponse = HttpClient.join(98765235678L);
        assertEquals(403, invalidTokenJoinResponse.code());

        final String session0Results = "{\"id\" : " + startSession + ", \"result\" : {" +
                "\"TestMMUser1\" : 123, \"TestMMUser2\" : 234, " +
                "\"TestMMUser3\" : 345, \"TestMMUser4\" : 456}}";
        Response validFinishResponse = HttpClient.finish(session0Results);
        assertEquals(200, validFinishResponse.code());

        Response repeatedFinishResponse = HttpClient.finish(session0Results);
        assertEquals(400, repeatedFinishResponse.code());

        for (int i = 0; i < 10; i++) {
            deleteUserCascade(session, "TestMMUser" + i);
        }

        MatchMakerServer.serverStop();
    }
}
