package matchmaker;

import model.GameConstants;
import model.GameSession;
import model.GameSessionImpl;
import model.Player;
import utils.RandomPlayerPlacer;
import utils.UniformVirusGenerator;
import model.GameField;
import utils.UniformFoodGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static model.GameConstants.MAX_PLAYERS_IN_SESSION;

/**
 * Creates {@link GameSession} for single player
 *
 * @author Alpi
 */
public class MatchMakerImpl implements MatchMaker {
    @NotNull
    private final Logger log = LogManager.getLogger(MatchMakerImpl.class);
    @NotNull
    private final List<GameSession> activeGameSessions = new ArrayList<>();
    @NotNull
    private final ConcurrentHashMap<Integer,Integer> PlayerSession = new ConcurrentHashMap<>();

    /**
     * Creates new GameSession for single player
     *
     * @param player single player
     */
    @Override
    public void joinGame(@NotNull Player player) {
        if(PlayerSession.get(player.getId())==null) {
            if (activeGameSessions.isEmpty()) {
                GameSession newGameSession = createNewGame();
                activeGameSessions.add(newGameSession);
                newGameSession.join(player);
                PlayerSession.put(player.getId(), 0);
                log.info(player + " joined " + newGameSession);
            } else {
                for (GameSession g : activeGameSessions) {
                    if (g.getPlayers().size() < MAX_PLAYERS_IN_SESSION) {
                        g.join(player);
                        PlayerSession.put(player.getId(), activeGameSessions.indexOf(g));
                        break;
                    } else {
                        if (activeGameSessions.indexOf(g) == activeGameSessions.size() - 1) {
                            GameSession newGameSession = createNewGame();
                            activeGameSessions.add(newGameSession);
                            newGameSession.join(player);
                            PlayerSession.put(player.getId(), activeGameSessions.indexOf(newGameSession));
                            log.info(player + " joined " + newGameSession);
                        }
                    }
                }
            }
        }
        else{
            log.info("Player already in session #"+PlayerSession.get(player.getId()));
        }
    }

    @NotNull
    public List<GameSession> getActiveGameSessions() {
        return new ArrayList<>(activeGameSessions);
    }


    private @NotNull GameSession createNewGame() {
        GameField field = new GameField();
        //TODO
        UniformFoodGenerator foodGenerator = new UniformFoodGenerator(field, GameConstants.FOOD_PER_SECOND_GENERATION,
                GameConstants.MAX_FOOD_ON_FIELD);
        return new GameSessionImpl(foodGenerator, new RandomPlayerPlacer(field), new UniformVirusGenerator(field, GameConstants.NUMBER_OF_VIRUSES),
                field);
    }

    @Override
    public GameSession getSessionForPlayer(Player player){
        for(GameSession session: activeGameSessions){
            if(session.getPlayers().contains(player)){
                return session;
            }
        }
        return null;
    }

    @Override
    public ConcurrentHashMap<Integer,Integer> getPlayerSession(){return PlayerSession;}

    @Override
    public void tick() {
        for(GameSession session: activeGameSessions){
            session.tick();
        }
    }

    @Override
    public void removePlayerSession(int id){
        log.info("here " + PlayerSession.get(id));
        PlayerSession.remove(id);}
}
