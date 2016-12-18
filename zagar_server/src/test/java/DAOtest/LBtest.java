package DAOtest;

import client.AuthRequests;
import accountserver.api.Authentification;
import accountserver.authInfo.Leader;
import accountserver.authInfo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 07.11.2016.
 */
public class LBtest {
    private static final Logger log = LogManager.getLogger(LBtest.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "127.0.0.1";
    private static final String PORT = "8081";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    @Test
    public void InsertTest() {
        int userid = 247859;
        Authentification.LB.insert(userid);
        Assert.assertEquals(0, Authentification.LB.getUserScore(userid));
        Authentification.LB.delete(userid);
    }

    @Test
    public void UpdateScoreTest() {
        int user = 247859;
        Authentification.LB.insert(user);
        Authentification.LB.updateScore(user, 25);
        Assert.assertEquals(25, Authentification.LB.getUserScore(user));
        Authentification.LB.delete(user);
    }

    @Test
    public void LeaderRegisterTest(){
        String user="LeaderTest";
        String password="LeaderTest";
        AuthRequests AR=new AuthRequests();
        AR.register(user,password);
        User jUser= Authentification.userDAO.getUserByLoginData(user,password);
        Assert.assertEquals(0, Authentification.LB.getUserScore(jUser.getId()));
        Authentification.userDAO.delete(jUser);
        Authentification.LB.delete(jUser.getId());
    }

    @Test
    public void getLeaderTest1(){
        String testJSON="{ \"LeaderUser1\": 2147483647, \"LeaderUser2\": 2147483646, \"LeaderUser3\": 2147483645 }";
        String l1="LeaderUser1";
        String l2="LeaderUser2";
        String l3="LeaderUser3";
        List<String> ls = new ArrayList<String>();

        List<User> jUser = new ArrayList<>();
        ls.add(l1);
        ls.add(l2);
        ls.add(l3);

        AuthRequests AR= new AuthRequests();

        for(int i=0;i<3;i++) {
            String user = ls.get(i);
            String password = user;
            AR.register(user,password);
            jUser.add( Authentification.userDAO.getUserByLoginData(user,password));
        }
        Authentification.LB.updateScore(jUser.get(0).getId(),2147483647);
        Authentification.LB.updateScore(jUser.get(1).getId(),2147483646);
        Authentification.LB.updateScore(jUser.get(2).getId(),2147483645);

        List<Leader> l= Authentification.LB.getAll(3);

        Assert.assertEquals(testJSON,AR.getLeaders(3));

        for(int i=0;i<3;i++){
            Authentification.userDAO.delete(jUser.get(i));
        }

        Authentification.LB.delete(jUser.get(0).getId());
        Authentification.LB.delete(jUser.get(1).getId());
        Authentification.LB.delete(jUser.get(2).getId());
    }

}

