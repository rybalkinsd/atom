package replication;

import network.ClientConnections;
import network.packets.PacketReplicate;
import main.ApplicationContext;
import matchmaker.MatchMaker;
import model.GameSession;
import model.Player;
import model.Food;
import model.PlayerCell;
import model.Virus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.websocket.api.Session;
import protocol.model.Cell;
import protocol.model.pFood;
import protocol.model.pVirus;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 18.12.2016.
 */
public class SmartReplicator implements Replicator {
    private static final Logger log = LogManager.getLogger(SmartReplicator.class);
    @Override
    public void replicate() {
        for (GameSession gameSession : ApplicationContext.get(MatchMaker.class).getActiveGameSessions()) {
            int i =0;

            List<Virus> virus1 = gameSession.getField().getVirus();
            pVirus[] virus = new pVirus[virus1.size()];
            for(Virus v: virus1){
                virus[i] = new pVirus(v.getLocation().getX(),v.getLocation().getY());
                i++;
            }

            pFood[] foodToAdd = new pFood[gameSession.getField().getFoodsToAdd().size()];
            i=0;
            for(Food f: gameSession.getField().getFoodsToAdd()){
                foodToAdd[i]= new pFood(f.getLocation().getX(),f.getLocation().getY());
                i++;
            }

            pFood[] foodToRemove = new pFood[gameSession.getField().getFoodsToRemove().size()];
            i=0;
            for(Food f: gameSession.getField().getFoodsToRemove()){
                foodToRemove[i]= new pFood(f.getLocation().getX(),f.getLocation().getY());
                i++;
            }


            int numberOfCellsInSession = 0;
            for (Player player : gameSession.getPlayers()) {
                numberOfCellsInSession += player.getCells().size();
            }
            Cell[] cells = new Cell[numberOfCellsInSession];

            i = 0;
            for (Player player : gameSession.getPlayers()) {
                for (PlayerCell playerCell : player.getCells()) {
                    cells[i] = new Cell(playerCell.getId(), player.getId(), playerCell.getRadius(), playerCell.getLocation().getX(), playerCell.getLocation().getY(),playerCell.getName());
                    i++;
                }
            }

            for (Map.Entry<Player, Session> connection : ApplicationContext.get(ClientConnections.class).getConnections()) {
                if (gameSession.getPlayers().contains(connection.getKey()) && connection.getValue().isOpen()) {
                    try {
                        new PacketReplicate(cells, foodToAdd, foodToRemove, virus).write(connection.getValue());
                    } catch (IOException e) {
                        log.error("Error sending replication");
                    }
                }
            }
            gameSession.getField().getFoodsToRemove().clear();
            gameSession.getField().getFoodsToAdd().clear();
        }
    }
}
