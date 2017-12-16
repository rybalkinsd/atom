package gameobjects;

import boxes.ConnectionPool;
import gameserver.Broker;
import gameserver.Ticker;
import geometry.Point;
import message.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);
    private Ticker ticker;
    private ConcurrentHashMap<Integer, BomberGirl> inGameBomberGirls = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Wall> inGameWalls = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Box> inGameBoxes = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Bomb> inGameBombs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Ground> inGameGround = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Explosion> inGameExplosions = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Bonus> inGameBonus = new ConcurrentHashMap<>();

    private List<GameObject> gameObjects = new ArrayList<>();
    private Cell[][] gameArea = new Cell[17][13];
    private long id;
    private int bonusChance;
    private int playerCount;

    public GameSession(long id, Ticker ticker, int playerCount) {
        this.id = id;
        this.ticker = ticker;
        this.bonusChance = 6;
        this.playerCount = playerCount;
    }

    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);

        if (gameObject.getClass().getSimpleName().equals("Bomb")) {
            inGameBombs.put(gameObject.getId(), (Bomb) gameObject);
            ticker.registerTickable((Tickable) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("Box")) {
            inGameBoxes.put(gameObject.getId(), (Box) gameObject);
            ticker.registerTickable((Tickable) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("Bonus")) {
            inGameBonus.put(gameObject.getId(), (Bonus) gameObject);
            ticker.registerTickable((Tickable) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("BomberGirl")) {
            inGameBomberGirls.put(gameObject.getId(), (BomberGirl) gameObject);
            ticker.registerTickable((Tickable) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("Explosion")) {
            inGameExplosions.put(gameObject.getId(), (Explosion) gameObject);
            ticker.registerTickable((Tickable) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("Ground")) {
            inGameGround.put(gameObject.getId(), (Ground) gameObject);
        }
        if (gameObject.getClass().getSimpleName().equals("Wall")) {
            inGameWalls.put(gameObject.getId(), (Wall) gameObject);
        }
    }

    public void removeGameObject(GameObject gameObject) {
        log.info("{} was deleted", gameObject.getClass().getSimpleName());
        gameObjects.remove(gameObject);
        if (gameObject.getClass().getSimpleName().equals("Bomb")) {
            inGameBombs.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("Box")) {
            inGameBoxes.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("Bonus")) {
            inGameBonus.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("BomberGirl")) {
            inGameBomberGirls.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("Explosion")) {
            inGameExplosions.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("Ground")) {
            inGameGround.remove(gameObject.getId());
        }
        if (gameObject.getClass().getSimpleName().equals("Wall")) {
            inGameWalls.remove(gameObject.getId());
        }
        ticker.unregisterTickable((Tickable) gameObject);
    }

    public void initGameArea() {

        for (int x = 0; x < 17; x++) {
            for (int y = 0; y < 13; y++) {
                if ((x == 0) || (x == 16)) {
                    addGameObject(new Wall(x * 32, y * 32));
                    gameArea[x][y] = new Cell(x, y, State.WALL);
                } else if (x % 2 != 0) {
                    if ((y == 0) || (y == 12)) {
                        addGameObject(new Wall(x * 32, y * 32));
                        gameArea[x][y] = new Cell(x, y, State.WALL);
                    }
                } else {
                    if (y % 2 == 0) {
                        addGameObject(new Wall(x * 32, y * 32));
                        gameArea[x][y] = new Cell(x, y, State.WALL);
                    }
                }
            }
        }
        for (int x = 1; x < 16; x++) {
            for (int y = 1; y < 12; y++) {
                if ((x % 2) != 0) {
                    addGameObject(new Ground(x * 32, y * 32));
                    gameArea[x][y] = new Cell(x, y, State.GROUND);
                } else {
                    if (y % 2 != 0) {
                        addGameObject(new Ground(x * 32, y * 32));
                        gameArea[x][y] = new Cell(x, y, State.GROUND);
                    }
                }
            }
        }
        for (int x = 1; x < 16; x++) {
            for (int y = 1; y < 12; y++) {
                if ((x == 1) || (x == 15)) {
                    if ((y > 2) && (y < 10)) {
                        addGameObject(new Box(x * 32, y * 32, this));
                        gameArea[x][y].addState(State.BOX);
                        if ((bonusChance > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSSPEED);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if ((bonusChance < (int) (Math.random() * 100))
                                && ((bonusChance * 2) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSFIRE);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if (((bonusChance * 2) < (int) (Math.random() * 100))
                                && ((bonusChance * 3) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSBOMB);
                            gameArea[x][y].addState(State.BONUS);
                        }

                    }
                } else if ((x == 2) || (x == 14)) {
                    if ((y % 2 != 0) && (y != 1) && (y != 11)) {
                        addGameObject(new Box(x * 32, y * 32, this));
                        gameArea[x][y].addState(State.BOX);

                        if ((bonusChance > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSSPEED);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if ((bonusChance < (int) (Math.random() * 100))
                                && ((bonusChance * 2) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSFIRE);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if (((bonusChance * 2) < (int) (Math.random() * 100))
                                && ((bonusChance * 3) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSBOMB);
                            gameArea[x][y].addState(State.BONUS);
                        }

                    }
                } else {
                    if (y % 2 != 0) {
                        addGameObject(new Box(x * 32, y * 32, this));
                        gameArea[x][y].addState(State.BOX);

                        if ((bonusChance > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSSPEED);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if ((bonusChance < (int) (Math.random() * 100))
                                && ((bonusChance * 2) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSFIRE);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if (((bonusChance * 2) < (int) (Math.random() * 100))
                                && ((bonusChance * 3) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSBOMB);
                            gameArea[x][y].addState(State.BONUS);
                        }


                    } else if (x % 2 != 0) {
                        addGameObject(new Box(x * 32, y * 32, this));
                        gameArea[x][y].addState(State.BOX);

                        if ((bonusChance > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSSPEED);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if ((bonusChance < (int) (Math.random() * 100))
                                && ((bonusChance * 2) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSFIRE);
                            gameArea[x][y].addState(State.BONUS);
                        }
                        if (((bonusChance * 2) < (int) (Math.random() * 100))
                                && ((bonusChance * 3) > (int) (Math.random() * 100))
                                && !gameArea[x][y].getState().contains(State.BONUS)) {
                            addGameObject(new Bonus(x * 32, y * 32, this, 1));
                            gameArea[x][y].addState(State.BONUSBOMB);
                            gameArea[x][y].addState(State.BONUS);
                        }


                    }
                }
            }
        }
        ConcurrentLinkedQueue<WebSocketSession> playerQueue = ConnectionPool.getInstance()
                .getSessionsWithGameId((int) id);
        addGameObject(new BomberGirl(32, 32, playerQueue.poll(), this, State.BOMB1));
        gameArea[1][1].addState(State.BOMBERGIRL);

        if (playerCount > 1) {
            addGameObject(new BomberGirl(480, 352, playerQueue.poll(), this, State.BOMB2));
            gameArea[15][11].addState(State.BOMBERGIRL);
        }
        if (playerCount > 2) {
            addGameObject(new BomberGirl(480, 32, playerQueue.poll(), this, State.BOMB3));
            gameArea[15][1].addState(State.BOMBERGIRL);
        }
        if (playerCount > 3) {
            addGameObject(new BomberGirl(32, 352, playerQueue.poll(), this, State.BOMB4));
            gameArea[1][11].addState(State.BOMBERGIRL);
        }
    }

    public Cell[][] getGameArea() {
        return gameArea;
    }

    public Cell getCellFromGameArea(int x, int y) {
        return gameArea[x / 32][y / 32];
    }

    public void addStateToCell(int x, int y, State state) {
        gameArea[x / 32][y / 32].addState(state);
    }

    public boolean removeStateFromCell(int x, int y, State state) {
        return (gameArea[x / 32][y / 32].getState().remove(state));
    }

    public int getId() {
        return (int) this.id;
    }

    public String jsonStringGround() {
        String objjson = "";
        for (Integer p : inGameGround.keySet()) {
            Ground obj = inGameGround.get(p);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, (objjson.length() - 1));
        return result;
    }

    public String jsonStringBonus() {
        String objjson = "";
        for (Integer i : inGameBonus.keySet()) {
            Bonus obj = inGameBonus.get(i);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, objjson.length());
        return result;

    }


    public String jsonStringExplosions() {
        String objjson = "";
        for (Integer i : inGameExplosions.keySet()) {
            Explosion obj = inGameExplosions.get(i);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, objjson.length());
        return result;
    }

    public String jsonStringBombs() {
        String objjson = "";
        for (Integer i : inGameBombs.keySet()) {
            Bomb obj = inGameBombs.get(i);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, objjson.length());
        return result;
    }

    public String jsonStringBoxes() {
        String objjson = "";
        for (Integer p : inGameBoxes.keySet()) {
            Box obj = inGameBoxes.get(p);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, objjson.length());
        return result;
    }

    public String jsonStringWalls() {
        String objjson = "";
        for (Integer p : inGameWalls.keySet()) {
            Wall obj = inGameWalls.get(p);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, objjson.length());
        return result;
    }

    public String jsonBomberGirl() {
        String objjson = "";
        for (Integer p : inGameBomberGirls.keySet()) {
            BomberGirl obj = inGameBomberGirls.get(p);
            objjson = objjson + obj.toJson() + ",";
        }
        String result = objjson.substring(0, (objjson.length() - 1));
        return result;
    }

    public boolean getGameOver() {
        if (inGameBomberGirls.size() <= 1)
            return true;
        else
            return false;
    }


    @Override
    public void tick(long elapsed) {
        log.info("tick");

    }
}