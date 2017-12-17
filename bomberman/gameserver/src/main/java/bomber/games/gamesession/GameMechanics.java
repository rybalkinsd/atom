package bomber.games.gamesession;

import bomber.connectionhandler.EventHandler;
import bomber.connectionhandler.PlayerAction;
import bomber.connectionhandler.json.Json;
import bomber.games.gameobject.Bonus;
import bomber.games.gameobject.Box;
import bomber.games.gameobject.Explosion;
import bomber.games.gameobject.Wall;
import bomber.games.gameobject.Player;
import bomber.games.gameobject.Bomb;
import bomber.games.geometry.Point;

import bomber.games.model.GameObject;
import bomber.games.model.Movable;
import bomber.games.model.Tickable;
import bomber.games.util.BonusRandom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMechanics {

    private static final Logger log = LoggerFactory.getLogger(GameMechanics.class);
    private static final int MAX_PLAYER_IN_GAME = 4;
    private Map<Integer, PlayerAction> actionOnMap = new HashMap<>();
    private final int gameZoneX = 17;//0,16 - стенки по X
    private final int gameZoneY = 13; //0,12 - стенки по Y
    private int playersCount;//Число игроков
    private final int brickSize = 32;//в будущем, когда будет накладываться на это дело фронтенд, это пригодится

    private final List<Integer> listPlayerId;
    private static List<List<Point>> spawnPositionsCollection = new ArrayList<>();
    private int positionSetting;  //choose which spawn positions will be applied
    private Set<Tickable> tickables = new ConcurrentSkipListSet<>();
    private ArrayList<GameObject> map = new ArrayList<>();

    public GameMechanics(int positionSetting, int playersCount) {
        this.positionSetting = positionSetting;
        this.playersCount = playersCount;
        this.listPlayerId = new ArrayList<>();
        listPlayerId.add(null);
        this.listPlayerId.addAll(EventHandler.getSessionIdList());
        List<Point> defaultPositions = new ArrayList<>();
        defaultPositions.add(null);
        defaultPositions.add(new Point(brickSize, brickSize));
        defaultPositions.add(new Point(gameZoneX * brickSize - brickSize * 2, brickSize));
        defaultPositions.add(new Point(brickSize, gameZoneY * brickSize - brickSize * 2));
        defaultPositions.add(new Point(gameZoneX * brickSize - brickSize * 2,
                gameZoneY * brickSize - brickSize * 2));
        spawnPositionsCollection.add(new ArrayList<Point>(defaultPositions));
    }

    public void setupGame(Map<Integer, GameObject> replica, AtomicInteger idGenerator) {

        idGenerator.getAndIncrement();
        replica.put(idGenerator.get(), new Explosion(idGenerator.get(),
                new Point(2 * brickSize, 2 * brickSize)));
        idGenerator.getAndIncrement();
        replica.put(idGenerator.get(), new Bonus(idGenerator.get(),
                new Point(brickSize, 2 * brickSize), Bonus.Type.Bonus_Speed));

        idGenerator.getAndIncrement();
        replica.put(idGenerator.get(), new Bonus(idGenerator.get(),
                new Point(2 * brickSize, brickSize), Bonus.Type.Bonus_Bomb));

        BonusRandom bonusRandom = new BonusRandom(playersCount);

        for (int x = 0; x <= gameZoneX; x++) {
            for (int y = 0; y <= gameZoneY; y++) {
                if (y == 0 || x == 0 || x * brickSize == (gameZoneX * brickSize - brickSize)
                        || y * brickSize == (gameZoneY * brickSize - brickSize)) {
                    idGenerator.getAndIncrement();
                    replica.put(idGenerator.get(), new Wall(idGenerator.get(),
                            new Point(x * brickSize, y * brickSize)));
                }
                if (!(y == 0 || x == 0 || x * brickSize == (gameZoneX * brickSize - brickSize)
                        || y * brickSize == (gameZoneY * brickSize - brickSize)) && ((x % 2 == 0) && (y % 2 == 0))) {
                    idGenerator.getAndIncrement();
                    replica.put(idGenerator.get(), new Wall(idGenerator.get(),
                            new Point(x * brickSize, y * brickSize)));
                } else {
                    if (!(y == 0 || x == 0 || x * brickSize == (gameZoneX * brickSize - brickSize)
                            || y * brickSize == (gameZoneY * brickSize - brickSize))) {
                        if (!isPlayerSpawn(x, y)) {
                            Bonus.Type bonus = bonusRandom.randomBonus();
                            if (bonus != null) {
                                idGenerator.getAndIncrement();
                                replica.put(idGenerator.get(), new Bonus(idGenerator.get(),
                                        new Point(x * brickSize, y * brickSize), bonus));
                            }
                            idGenerator.getAndIncrement();
                            replica.put(idGenerator.get(), new Box(idGenerator.get(),
                                    new Point(x * brickSize, y * brickSize)));
                        }
                    }
                }
            }
        }
        for (int i = 1; i <= playersCount; i++) {
            replica.put(listPlayerId.get(i), new Player(listPlayerId.get(i),
                    spawnPositionsCollection.get(positionSetting).get(i)));
            registerTickable((Tickable) replica.get(listPlayerId.get(i)));
            try {
                EventHandler.sendPossess(listPlayerId.get(i));
            } catch (IOException e) {
                log.error("unable to sendPosses");
            }
        }
    }

    private boolean isPlayerSpawn(int x, int y) {
        boolean flag = false;
        for (int i = 1; i <= playersCount; i++) {
            Point playerPoint = new Point(spawnPositionsCollection.get(positionSetting).get(i).getX(),
                    spawnPositionsCollection.get(positionSetting).get(i).getY());
            Point currentPoint = new Point(x * brickSize, y * brickSize);
            if (playerPoint.equals(currentPoint))
                flag = true;
            if (new Point(playerPoint.getX() + brickSize, playerPoint.getY()).equals(currentPoint))
                flag = true;
            if (new Point(playerPoint.getX(), playerPoint.getY() + brickSize).equals(currentPoint))
                flag = true;
            if (new Point(playerPoint.getX() - brickSize, playerPoint.getY()).equals(currentPoint))
                flag = true;
            if (new Point(playerPoint.getX(), playerPoint.getY() - brickSize).equals(currentPoint))
                flag = true;
        }
        return flag;
    }

    public void readInputQueue(ConcurrentLinkedQueue<PlayerAction> inputQueue) {

        while (!inputQueue.isEmpty()) { //делать до тех пор пока очередь не опустеет
            Integer playerId = inputQueue.element().getId(); //заранее узнаем id игрока, возглавляющего очередь
            if (!actionOnMap.containsKey(playerId)) { //если действий от этого игрока еще не было
                actionOnMap.put(playerId, inputQueue.element());//Запишем действие в мапу
            }
            log.info("Здесь будет actionOnMap");
            log.info(actionOnMap.toString());
            inputQueue.remove();//удаляем главу этой очереди
        }
    }

    public void clearInputQueue(ConcurrentLinkedQueue<PlayerAction> inputQueue) {
        if (!inputQueue.isEmpty())
            inputQueue.clear();
        actionOnMap.clear();
    }


    public void doMechanic(Map<Integer, GameObject> replica, AtomicInteger idGenerator) {
        for (GameObject gameObject : replica.values()) {
            MechanicsSubroutines mechanicsSubroutines = new MechanicsSubroutines(idGenerator);
            //подняли вспомогательные методы
            if (gameObject instanceof Player) {
                Player currentPlayer = ((Player) gameObject);
                Point previosPos = currentPlayer.getPosition();
                if (actionOnMap.containsKey(gameObject.getId())) {
                    log.info("currentPlayerId = " + currentPlayer.getId());
                    switch (actionOnMap.get(currentPlayer.getId()).getType()) {
                        //либо шагает Up,Down,Right,Left, либо ставит бомбу Bomb
                        case UP: //если идет вверх
                            //задали новые координаты
                            currentPlayer.setPosition(currentPlayer.move(Movable.Direction.UP));
                            if (!(mechanicsSubroutines.collisionCheck(currentPlayer, replica))) {
                                currentPlayer.setPosition(previosPos);
                            }
                            break;

                        case DOWN:
                            //задали новые координаты
                            currentPlayer.setPosition(currentPlayer.move(Movable.Direction.DOWN));
                            if (!(mechanicsSubroutines.collisionCheck(currentPlayer, replica))) {
                                currentPlayer.setPosition(previosPos);
                            }
                            break;
                        case LEFT:
                            //задали новые координаты

                            currentPlayer.setPosition(currentPlayer.move(Movable.Direction.LEFT));
                            if (!(mechanicsSubroutines.collisionCheck(currentPlayer, replica))) {
                                currentPlayer.setPosition(previosPos);
                            }
                            break;
                        case RIGHT:

                            currentPlayer.setPosition(currentPlayer.move(Movable.Direction.RIGHT));
                            if (!(mechanicsSubroutines.collisionCheck(currentPlayer, replica))) {
                                currentPlayer.setPosition(previosPos);
                            }
                            break;
                        case BOMB:
                            Point bombPosition = new Point(currentPlayer.getPosition().getX(),
                                    currentPlayer.getPosition().getY());
                            idGenerator.getAndIncrement();
                            Bomb tmpBomb = new Bomb(idGenerator.get(), bombPosition,
                                    currentPlayer.getBombPower());
                            replica.put(idGenerator.get(), tmpBomb);
                            log.info("Bomb must be here");
                            log.info("========================================");
                            log.info(Json.replicaToJson(replica));
                            registerTickable(tmpBomb);
                            break;
                        default:
                            break;
                    }
                }
                if (!(mechanicsSubroutines.bonusCheck(currentPlayer, replica) == null)) { //если был взят бонус
                    Integer tmp = mechanicsSubroutines.bonusCheck(currentPlayer, replica);
                    Bonus getBonus = (Bonus) replica.get(tmp);
                    switch (getBonus.getType()) { //Узнаем что это за бонус
                        case Bonus_Bomb:
                            currentPlayer.setMaxBombs(currentPlayer.getMaxBombs() + 1);
                            break;
                        case Bonus_Fire:
                            currentPlayer.setBombPower(currentPlayer.getBombPower() + 1);
                            break;
                        case Bonus_Speed:
                            currentPlayer.setVelocity(currentPlayer.getVelocity() * 2);
                            break;
                        default:
                            break;
                    }
                    replica.remove(tmp);
                }
            }

            if (gameObject instanceof Bomb) { //начинаем работать с бомбами
                if (!((Bomb) gameObject).isAlive()) { //если эта бомба еще не взорвалась

                    Bomb currentBomb = (Bomb) gameObject;
                    log.info("Удаляем бомбу");
                    replica.remove(gameObject.getId()); //Удалим бомбу из replica
                    log.info("Бомба удалена");
                    //Слооожнаа


                    //Начальные координаты (он же эпицентр взрыва)
                    int epicenterX = currentBomb.getPosition().getX();
                    int epicenterY = currentBomb.getPosition().getY();


                    for (GameObject boxObject : replica.values()) {
                        //пройдем по реплике в поисках жертв
                        if ((boxObject instanceof Box) || (boxObject instanceof Wall)) {
                            //упростим прогон, пробежавшись только по коробкам
                            log.info(currentBomb.getPosition().toString() + "Координаты бомбы");
                            log.info(boxObject.getPosition().toString() + "Координаты сравниваемого объекта");

                            boolean up = true;
                            boolean down = true;
                            boolean left = true;
                            boolean right = true;
                            //это все индикаторы, отслеживающие был ли уже взрыв объекта по одной
                            // их 4х сторон взрыва, чего то более изящного не придумал

                            for (int power = 1; power <= (currentBomb).getExplosionRange(); power++) {
                                //надо узнать силу взрыва
                                for (int side = 1; side <= 4; side++) { //взрыв на все 4 стороны
                                    switch (side) {
                                        case 1: //если идет вверх
                                            if (up) {
                                                Point currentPoint =
                                                        new Point(epicenterX, epicenterY + power * brickSize);
                                                //сместились вверх от эпицентра, в зависимости от глубины взрыва
                                                if (mechanicsSubroutines.createExplosions(currentPoint, replica)) {
                                                    idGenerator.getAndIncrement();
                                                    replica.put(idGenerator.get(), new Explosion(idGenerator.get(),
                                                            currentPoint)); //место не занято, отрисуем взрыв
                                                } else {
                                                    up = false;
                                                    //взрыв был потрачен либо в стену, либо в коробку,
                                                    // дальше взрыв не пойдет
                                                }
                                            }
                                            break;
                                        case 2://если идет вниз
                                            if (down) {
                                                Point currentPoint = new Point(epicenterX,
                                                        epicenterY - power * brickSize);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, replica)) {
                                                    idGenerator.getAndIncrement();
                                                    replica.put(idGenerator.get(), new Explosion(idGenerator.get(),
                                                            currentPoint)); //место не занято, отрисуем взрыв
                                                } else {
                                                    down = false;
                                                }
                                            }
                                            break;
                                        case 3://если идет влево
                                            if (left) {
                                                Point currentPoint =
                                                        new Point(epicenterX - power * brickSize, epicenterY);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, replica)) {
                                                    idGenerator.getAndIncrement();
                                                    replica.put(idGenerator.get(),
                                                            new Explosion(idGenerator.get(), currentPoint));
                                                    //место не занято, отрисуем взрыв
                                                } else {
                                                    left = false;
                                                }
                                            }
                                            break;
                                        case 4://если идет вправо
                                            if (right) {
                                                Point currentPoint =
                                                        new Point(epicenterX + power * brickSize, epicenterY);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, replica)) {
                                                    idGenerator.getAndIncrement();
                                                    replica.put(idGenerator.get(),
                                                            new Explosion(idGenerator.get(), currentPoint));
                                                    //место не занято, отрисуем взрыв
                                                } else {
                                                    right = false;
                                                }
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }

            }
            if (gameObject instanceof Explosion) { //убираем Explosion с карты
                if (!((Explosion) gameObject).isAlive()) {
                    replica.remove(gameObject.getId());
                } else {
                    mechanicsSubroutines.youDied(replica, (Explosion) gameObject);
                }
            }
        }
    }


    private List<Point> setSpawnPositions() {   //only default realisation now, may be expanded for more spawn options
        return null;
    }

    public void setTickables(Set<Tickable> tickables) {
        this.tickables = tickables;
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }

    public ArrayList<GameObject> getMap() {
        return map;
    }
}

