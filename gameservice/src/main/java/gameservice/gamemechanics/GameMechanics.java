package gameservice.gamemechanics;

import gameservice.Sessions;
import gameservice.geometry.Bar;
import gameservice.geometry.Point;
import gameservice.model.Bomb;
import gameservice.model.Fire;
import gameservice.model.GameObject;
import gameservice.model.GameSession;
import gameservice.model.Movable;
import gameservice.model.Pawn;
import gameservice.model.Tickable;
import gameservice.model.Wall;
import gameservice.model.Wood;
import gameservice.network.Broker;
import gameservice.network.Topic;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class GameMechanics extends Thread {
    public static final int PLAYERS_COUNT = 2;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameMechanics.class);
    private static final int FPS = 60;
    private static final int FRAME_TIME = 1000 / FPS;
    private final Broker broker = Broker.getInstance();
    private final Queue<Action> inputQueue = new LinkedBlockingQueue<>();

    @Autowired
    Sessions storage;

    private GameSession gameSession;
    private ArrayList<Pawn> movedPawns = new ArrayList<>(PLAYERS_COUNT);

    public GameMechanics(GameSession session) {
        this.gameSession = session;
    }

    /*class Replica {
        ArrayList<GameObject> objects;
        boolean gameOver = false;

        Replica(ArrayList<GameObject> objects) {
            this.objects = objects;
        }

        public void setObjects(ArrayList<GameObject> objects) {
            this.objects = objects;
        }

        public void setGameOver(boolean gameOver) {
            this.gameOver = gameOver;
        }
    }*/

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            long started = System.currentTimeMillis();
            handleQueue();
            act(FRAME_TIME);
            checkCollisions();
            detonationBomb();
            for (WebSocketSession session : storage.getWebsocketsByGameSession(gameSession)) {
                broker.send(session, Topic.REPLICA, /*new Replica(gameSession.getObjectsWithoutWalls())*/
                        gameSession.getObjectsWithoutWalls());
            }
            long elapsed = System.currentTimeMillis() - started;
            if (elapsed < FRAME_TIME) {
                LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(FRAME_TIME - elapsed));
            } else {
                log.warn("tick lag {} ms", elapsed - FRAME_TIME);
            }
        }
    }

    public void putAction(Action action) {
        inputQueue.offer(action);
    }

    private void handleQueue() {
        movedPawns.clear();
        for (Action action : inputQueue) {
            if (action.getAction().equals(Topic.PLANT_BOMB) && action.getActor().getBombCapacity() != 0) {
                Bomb bomb = new Bomb(gameSession, closestPoint(action.getActor().getPosition()), action.getActor());
                gameSession.addGameObject(bomb);
                gameSession.addGameObject(bomb);
            }
            if (action.getAction().equals(Topic.MOVE) && !movedPawns.contains(action.getActor())) {
                action.getActor().setDirection(action.getData());
                movedPawns.add(action.getActor());
            }
        }
        inputQueue.clear();
    }

    private void act(int elapsed) {
        for (GameObject object : gameSession.getGameObjects()) {
            if (object instanceof Tickable)
                ((Tickable) object).tick(elapsed);
        }
    }

    public void checkCollisions() {
        for (Pawn pawn : gameSession.getPawns()) {
            Bar barPawn = pawn.getBar();
            for (Wall wall : gameSession.getWalls()) {
                Bar barWall = wall.getBar();
                if (!barPawn.isColliding(barWall)) {
                    pawn.moveBack(FRAME_TIME);
                }
            }
            for (Wood wood : gameSession.getWood()) {
                Bar barWood = wood.getBar();
                if (!barPawn.isColliding(barWood)) {
                    pawn.moveBack(FRAME_TIME);
                }
            }
            pawn.setDirection(Movable.Direction.IDLE);
        }
    }

    public Point closestPoint(Point point) {
        double modX = point.getX() % GameObject.getWidthBox();
        double modY = point.getY() % GameObject.getHeightBox();
        int divX = (int) point.getX() / (int) GameObject.getWidthBox();
        int divY = (int) point.getY() / (int) GameObject.getHeightBox();
        if (modX < GameObject.getWidthBox() / 2) {
            if (modY < GameObject.getHeightBox() / 2)
                return new Point(divX * GameObject.getWidthBox(), divY * GameObject.getHeightBox());
            else
                return new Point(divX * GameObject.getWidthBox(), (divY + 1) * GameObject.getHeightBox());
        } else if (modY < 16)
            return new Point((divX + 1) * GameObject.getWidthBox(), divY * GameObject.getWidthBox());
        return new Point((divX + 1) * GameObject.getWidthBox(), (divY + 1) * GameObject.getHeightBox());
    }


    public void detonationBomb() {
        ArrayList<Bomb> bombList = new ArrayList<>();
        ArrayList<Wood> woodList = new ArrayList<>();
        ArrayList<Pawn> pawnList = new ArrayList<>();
        ArrayList<Fire> fireList = new ArrayList<>();

        boolean right = true;
        boolean up = true;
        boolean left = true;
        boolean down = true;
        boolean notBonus = true;

        for (Fire fire : gameSession.getFire()) {
            if (fire.dead())
                fireList.add(fire);
        }
        for (Bomb bomb : gameSession.getBombs()) {
            if (bomb.dead()) {
                bombList.add(bomb);
                Bar upBar = new Bar(Point.getUpPoint(bomb.getPosition()));
                Bar rightBar = new Bar(Point.getRightPoint(bomb.getPosition()));
                Bar downBar = new Bar(Point.getDownPoint(bomb.getPosition()));
                Bar leftBar = new Bar(Point.getLeftPoint(bomb.getPosition()));
                Bar centralBar = new Bar(bomb.getPosition());

                for (Wall wall : gameSession.getWalls()) {
                    Bar barWall = wall.getBar();
                    if (!barWall.isColliding(rightBar)) {
                        right = false;
                    }
                    if (!barWall.isColliding(upBar)) {
                        up = false;
                    }
                    if (!barWall.isColliding(leftBar)) {
                        left = false;
                    }
                    if (!barWall.isColliding(downBar)) {
                        down = false;
                    }
                }
                for (Wood wood : gameSession.getWood()) {
                    Bar barWood = wood.getBar();
                    if (up && !barWood.isColliding(upBar)) {
                        woodList.add(wood);
                        continue;
                    }
                    if (right &&  !barWood.isColliding(rightBar)) {
                        woodList.add(wood);
                        continue;
                    }
                    if (down && !barWood.isColliding(downBar)) {
                        woodList.add(wood);
                        continue;
                    }
                    if (left && !barWood.isColliding(leftBar)) {
                        woodList.add(wood);
                    }
                }

                for (Pawn pawn : gameSession.getPawns()) {
                    Bar pawnBar = pawn.getBar();

                    if (up && !pawnBar.isColliding(upBar)) {
                        pawnList.add(pawn);
                        continue;
                    }
                    if (right &&  !pawnBar.isColliding(rightBar)) {
                        pawnList.add(pawn);
                        continue;
                    }
                    if (down &&  !pawnBar.isColliding(downBar)) {
                        pawnList.add(pawn);
                        continue;
                    }
                    if (left && !pawnBar.isColliding(leftBar)) {
                        pawnList.add(pawn);
                        continue;
                    }
                    if (!pawnBar.isColliding(centralBar)) {
                        pawnList.add(pawn);
                    }
                }
            }
        }

        for (Bomb bomb : bombList) {
            gameSession.addGameObject(new Fire(gameSession, bomb.getPosition()));
            if (up) {
                gameSession.addGameObject(new Fire(gameSession, Point.getUpPoint(bomb.getPosition())));
            }
            if (down) {
                gameSession.addGameObject(new Fire(gameSession, Point.getDownPoint(bomb.getPosition())));
            }
            if (right) {
                gameSession.addGameObject(new Fire(gameSession, Point.getRightPoint(bomb.getPosition())));
            }
            if (left) {
                gameSession.addGameObject(new Fire(gameSession, Point.getLeftPoint(bomb.getPosition())));
            }
            gameSession.removeGameObject(bomb);
        }

        for (Wood wood : woodList) {
            gameSession.removeGameObject(wood);
        }
        for (Pawn pawn : pawnList) {
            gameSession.removeGameObject(pawn);
        }
        for (Fire fire : fireList) {
            gameSession.removeGameObject(fire);
        }
    }
}
