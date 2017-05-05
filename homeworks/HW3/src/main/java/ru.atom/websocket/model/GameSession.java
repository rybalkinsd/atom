package ru.atom.websocket.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private List<GameObject> gameObjects = new ArrayList<>();
    private int id = 0;

    public int getCurrentId() {
        return id;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        id++;
    }

    /**
     * can be used only before game starts
     * @param pawnId
     */
    public void killPawn(int pawnId) {
        gameObjects.remove(gameObjects.stream().filter(gameObject -> gameObject.getId() == pawnId).findAny().get());
    }

    public void plantBomb(int pawnId) {
        ((Player)gameObjects.get(pawnId)).plant();
    }

    public void movePawn(int pawnId, Movable.Direction direction) {
        Player pawn = (Player)gameObjects.get(pawnId);
        if (pawn.getDirection() == Movable.Direction.IDLE) {
            ((Player) gameObjects.get(pawnId)).setDirection(direction);
        } else {log.info("player has instruction to move already");}
    }

    @Override
    public void tick(long elapsed) {
        log.info("tick");
        ArrayList<Temporary> dead = new ArrayList<>();
        ArrayList<GameObject> born = new ArrayList<>();
        for (GameObject gameObject : gameObjects) {
            if (gameObject instanceof Tickable) {
                if (gameObject instanceof Player) {
                    Bomb bomb = ((Player) gameObject).plantBomb();
                    if (bomb !=null) {
                        bomb.setId(id);
                        id++;
                        born.add(bomb);
                    }
                }
                ((Tickable) gameObject).tick(elapsed);
            }
            if (gameObject instanceof Temporary && ((Temporary) gameObject).isDead()) {
                dead.add((Temporary)gameObject);
            }
        }
        gameObjects.removeAll(dead);
        gameObjects.addAll(born);
    }
}
