package accountserver.authDAO;

import accountserver.authInfo.Leader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Max on 07.11.2016.
 */
public class LB implements AuthDAO<Leader> {
    private static final Logger log = LogManager.getLogger(LB.class);
    private static final String INSERT_POINTS_TEMPLATE =
            "INSERT INTO leaderboard VALUES (%d);";
    private static final String UPDATE_POINTS =
            "UPDATE leaderboard SET score = %d WHERE userid =%d;";
    private static final String SELECT_LEADERS =
            "SELECT * FROM leaderboard ORDER BY score DESC LIMIT %d;";

    @Override
    public  List<Leader> getAll() {
        int N=300;
        List<Leader> leaders = new ArrayList<>();
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_LEADERS,N));
            while (rs.next()) {
                leaders.add(mapToLeader(rs));
                log.info("Userid " + rs.getInt("userid") + "pts "+ rs.getInt("score"));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return leaders;
    }

    public List<Leader> getAll(int N){
        List<Leader> leaders = new ArrayList<>();
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(String.format(SELECT_LEADERS,N));
            while (rs.next()) {
                leaders.add(mapToLeader(rs));
                log.info("Userid " + rs.getInt("userid") + "pts "+ rs.getInt("score"));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return leaders;
    }

    @Override
    public  List<Leader> getAllWhere(String... hqlConditions){
        List<Leader> l1= new ArrayList<>();
        return l1;
    }

    @Override
    public  boolean insert(Leader L){
        int userid = L.getId();
        log.info(String.format(INSERT_POINTS_TEMPLATE, userid));
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(INSERT_POINTS_TEMPLATE, userid));
            log.info("User " + userid +" Inserted to leaderbord");
            log.info(String.format(INSERT_POINTS_TEMPLATE, userid));
            return true;
        } catch (SQLException e) {
            log.error("Failed to insert user id");
            return false;
        }
    }

    public void insert(int userid){
        Leader l= new Leader();
        l.setId(userid);
        this.insert(l);
    }

    @Override
    public  boolean delete(Leader L){
        int userid = L.getId();
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute("DELETE FROM leaderboard WHERE userid = " + userid +";");
            log.info("UserId "+ userid + " deleted");
            return true;
        }catch (SQLException e) {
            log.error("Failed to delete", e);
            return false;
        }
    }

    public void delete(int userId){
        Leader l = new Leader();
        l.setId(userId);
        this.delete(l);
    }

    public void updateScore(int userid, int pts){
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            stm.execute(String.format(UPDATE_POINTS, pts, userid));
            log.info("user "+ userid + " pts "+ pts + " updated");
            log.info(String.format(UPDATE_POINTS, pts, userid));
        } catch (SQLException e) {
            log.error("Failed to update pts");
        }
    }

    public int getUserScore(int userid){
        try (Connection con = JDBCDbConnection.getConnection();
             Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery("SELECT * FROM leaderboard WHERE userid = " + userid + ";");
            rs.next();
            return rs.getInt("score");
        }catch (SQLException e) {
            log.error("Failed to getScore.", e);
            return -1;
        }
    }

    private static Leader mapToLeader(ResultSet rs) throws SQLException{
        return new Leader().setId(rs.getInt("userid")).setPts(rs.getInt("score"));
    }


}
