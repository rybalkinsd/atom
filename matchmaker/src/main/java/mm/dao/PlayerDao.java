package mm.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import static mm.dao.ReturnValue.TRUE;
import static mm.dao.ReturnValue.FALSE;
import static mm.dao.ReturnValue.ERROR;

public class PlayerDao implements Dao<Player> {
    private static final Logger log = LogManager.getLogger(PlayerDao.class);

    @Language("sql")
    private static final String SELECT_ALL_USERS =
            "select * " +
                    "from game.player";

    @Language("sql")
    private static final String SELECT_ALL_USERS_WHERE =
            "select * " +
                    "from game.player " +
                    "where ";

    @Language("sql")
    private static final String INSERT_USER_TEMPLATE =
            "insert into game.player (gameid, login) " +
                    "values ('%d', '%s');";

    @Language("sql")
    private static final String INSERT_INTO_TABLE_TEMPLATE =
            "insert into %s (login) " +
                    "values ('%s');";

    @Language("sql")
    private static final String DELETE_USER =
            "delete * " +
                    "from game.player " +
                    "where name='%s'";

    @Language("sql")
    private static final String RESET_SCHEMA = "drop schema if exists game cascade;\n" +
            "create schema game;";


    @Language("sql")
    private static final String RESET_TABLE = "DROP TABLE IF EXISTS game.player;\n" +
            "CREATE TABLE game.player (\n" +
            "  gameId   BIGINT          ,\n" +
            "  login    VARCHAR(20)     UNIQUE NOT NULL,\n" +
            "  PRIMARY KEY (login)\n" +
            ");";

    @Language("sql")
    private static final String CHECK_FOR_PLAYER =
            "SELECT * from serverdata.list where login = '%s'";


    @Language("sql")
    private static final String GET_PLAYER_RANK =
            "SELECT rank FROM serverdata.list where login = '%s'";

    @Language("sql")
    private static final String LOGIN =
            "SELECT password FROM serverdata.list where login = '%s' " +
                    "and password = '%s';";

    @Language("sql")
    private static final String REGISTER =
            "INSERT INTO serverdata.list (login,password) " +
                    "VALUES ('%s', '%s');";

    @Language("sql")
    private static final String GET_PLAYER_HISTORY =
            "select players,result from serverdata.gamehistory where '%s' = any (players);";

    @Language("sql")
    private static final String GET_ALL_GAME_ID =
            "SELECT gameid from serverdata.gamehistory";

    @Language("sql")
    private static final String ADD_TO_HISTORY =
            "INSERT INTO serverdata.gamehistory VALUES\n" +
                    "('%d', '%s', '%s');";

    @Override
    public List<Player> getAll() {
        List<Player> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(SELECT_ALL_USERS);
            while (rs.next()) {
                persons.add(mapToPlayer(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll.", e);
            return Collections.emptyList();
        }

        return persons;
    }

    @Override
    public List<Player> getAllWhere(String... conditions) {
        List<Player> persons = new ArrayList<>();
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {

            String condition = String.join(" and ", conditions);
            ResultSet rs = stm.executeQuery(SELECT_ALL_USERS_WHERE + condition);
            while (rs.next()) {
                persons.add(mapToPlayer(rs));
            }
        } catch (SQLException e) {
            log.error("Failed to getAll where.", e);
            return Collections.emptyList();
        }

        return persons;
    }

    @Override
    public void insert(Player player) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(INSERT_USER_TEMPLATE, player.getGameId(), player.getLogin()));
        } catch (SQLException e) {
            log.error("Failed to create player {}", player.getLogin(), e);
        }
    }

    public void insertIntoTable(String table, String name) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            if (!(playerExists(name) == TRUE))
                stm.execute(String.format(INSERT_INTO_TABLE_TEMPLATE, table, name));
            else
                log.error("player already registered");
        } catch (SQLException e) {
            log.error("Failed to insert player " + name + " into table " + table, e);
        }
    }

    @Override
    public void delete(Player player) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(DELETE_USER, player.getLogin()));
        } catch (SQLException e) {
            log.error("Failed to create player {}", player.getLogin(), e);
        }
    }

    public void reset() {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(RESET_SCHEMA);
            stm.execute(RESET_TABLE);
        } catch (SQLException e) {
            log.error("Failed to reset DB", e);
        }
    }

    public ReturnValue playerExists(String name) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(String.format(CHECK_FOR_PLAYER, name));
            int rscounter = 0;
            while (rs.next())
                rscounter++;
            if (rscounter != 0)
                return TRUE;
            return FALSE;
        } catch (SQLException e) {
            log.error("Failed to check if player exists");
            return ERROR;
        }
    }

    public int getPlayerRank(String name) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(String.format(GET_PLAYER_RANK, name));
            rs.next();
            return rs.getInt("rank");
        } catch (SQLException e) {
            log.error("Failed to get player " + name + "rank");
            return 0;
        }
    }

    public ReturnValue login(String login, String password) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(String.format(LOGIN, login, password));
            if (rs.next())
                return TRUE;
            else
                return FALSE;
        } catch (SQLException e) {
            log.error("Failed to login player " + login + "\ndue to exception: " + e);
            return ERROR;
        }
    }

    public ReturnValue register(String login, String password) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(REGISTER, login, password));
            return TRUE;
        } catch (SQLException e) {
            log.error("Failed to register player " + login + "\ndue to exception: " + e);
            return ERROR;
        }
    }

    public ArrayList<Map<String, Integer>> getPlayerHistory(String login) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(String.format(GET_PLAYER_HISTORY, login));
            ArrayList<Map<String, Integer>> resultArray = new ArrayList<>();
            while (rs.next()) {
                String players = rs.getString(1);
                players = players.substring(1, players.length() - 1);
                ArrayList<String> playersArray = new ArrayList<>(Arrays.asList(players.split(",")));

                String results = rs.getString(2);
                results = results.substring(1, results.length() - 1);
                ArrayList<String> resultsArray = new ArrayList<>(Arrays.asList(results.split(",")));

                Map<String, Integer> game = new ConcurrentHashMap<>();
                if (resultsArray.size() == playersArray.size()) {
                    for (int i = 0; i < playersArray.size(); i++)
                        game.put(playersArray.get(i), Integer.parseInt(resultsArray.get(i)));
                }
                resultArray.add(game);
            }
            return resultArray;

        } catch (SQLException e) {
            log.error("Failed to return game history for player " + login + "\ndue to exception: " + e);
            return null;
        }
    }

    public List<Integer> getAllGameId(String player) {
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            ResultSet rs = stm.executeQuery(GET_ALL_GAME_ID);
            List<Integer> returnList = new LinkedList<>();
            while (rs.next()) {
                returnList.add(rs.getInt(1));
            }
            return returnList;
        } catch (SQLException e) {
            log.error("Failed to get all game id \ndue to exception: " + e);
            return null;
        }
    }

    public void addToHistory(JsonHistory historyEntry) {
        StringBuilder playersString = new StringBuilder();
        playersString.append("{");
        StringBuilder scoreString = new StringBuilder();
        scoreString.append("{");
        for (JsonHistory.JsonPlayer player :
                historyEntry.players) {
            playersString.append(player.name).append(",");
            scoreString.append(player.score).append(",");
        }
        playersString.deleteCharAt(playersString.length() - 1);
        playersString.append("}");
        scoreString.deleteCharAt(scoreString.length() - 1);
        scoreString.append("}");
        try (Connection con = DbConnector.getConnection();
             Statement stm = con.createStatement()
        ) {
            stm.execute(String.format(ADD_TO_HISTORY, historyEntry.gameid, playersString, scoreString));
        } catch (SQLException e) {
            log.error("Failed to add game to history \ndue to exception: " + e);
        }
        log.info(playersString);
    }

    private static Player mapToPlayer(ResultSet rs) throws SQLException {
        return new Player()
                .setGameId(rs.getInt("gameId"))
                .setLogin(rs.getString("login"));
    }
}
