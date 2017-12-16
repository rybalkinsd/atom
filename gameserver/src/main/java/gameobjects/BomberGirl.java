package gameobjects;

import boxes.ConnectionPool;
import boxes.InputQueue;
import gameserver.Broker;
import geometry.Point;
import message.DirectionMessage;
import message.Input;
import message.Topic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.WebSocketSession;
import util.JsonHelper;

import java.util.concurrent.ConcurrentLinkedQueue;


public class BomberGirl extends Field implements Tickable, Movable, Comparable {
    private static final Logger log = LogManager.getLogger(BomberGirl.class);
    private int x;
    private int y;
    private int id;
    private double velocity;
    private boolean alive = true;
    private int maxBombs;
    private int bombPower;
    private double speedModifier = 1.0;
    private State bombType;
    private WebSocketSession session;
    private GameSession gameSession;
    private ConcurrentLinkedQueue<Boolean> bombStatus = new ConcurrentLinkedQueue<>();

    public BomberGirl(int x, int y, WebSocketSession session, GameSession gameSession, State bombType) {
        super(x, y);
        this.x = x;
        this.y = y;
        this.id = getId();
        this.session = session;
        this.gameSession = gameSession;
        this.bombType = bombType;
        this.velocity = 0.2;
        this.bombPower = 1;
        this.maxBombs = 1;
        log.info("New BomberGirl with id {}", id);
        Broker.getInstance().send(ConnectionPool.getInstance().getPlayer(session), Topic.POSSESS, id);
    }

    public void tick(long elapsed) {
        //log.info("tick");
        if (gameSession.getCellFromGameArea(this.x + 12, this.y + 12)
                .getState().contains(State.EXPLOSION)) {
            alive = false;
        }
        if (alive) {
            if (gameSession.getCellFromGameArea(x + 12, y + 12).getState().contains(State.BONUSBOMB)) {
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUS);
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUSBOMB);
                this.maxBombs++;
            }
            if (gameSession.getCellFromGameArea(x + 12, y + 12).getState().contains(State.BONUSFIRE)) {
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUS);
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUSFIRE);
                this.bombPower++;
                log.info("Stop");
            }
            if (gameSession.getCellFromGameArea(x + 12, y + 12).getState().contains(State.BONUSSPEED)) {
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUS);
                gameSession.removeStateFromCell(x + 12, y + 12, State.BONUSSPEED);
                this.speedModifier += 0.3;
            }
            Input input;
            //log.info("producing action");
            if (Input.hasMoveInputForPlayer(session)) {
                input = Input.getInputForPlayer(session);
                log.warn(receiveDirection(session, input.getMessage().getData()).getDirection());
                move(receiveDirection(session, input.getMessage().getData()).getDirection(), elapsed);
                InputQueue.getInstance().remove(input);
            }
            if (Input.hasBombInputForPlayer(session)) {
                input = Input.getInputForPlayer(session);
                if (maxBombs > bombPlantedCount()) {
                    gameSession.addGameObject(new Bomb(this.x + 12, this.y + 12, gameSession, bombPower,
                            this, bombType));
                    gameSession.getCellFromGameArea(this.x, this.y)
                            .addState(bombType);
                    bombStatus.offer(true);
                    InputQueue.getInstance().remove(input);
                }
            }
        } else gameSession.removeGameObject(this);
    }

    public Point move(Movable.Direction direction, long time) {
        int shift = (int) (time * velocity * speedModifier);
        if (direction == Movable.Direction.UP) {
            if (!isCellSolid(x, y + 23 + shift) && !isCellSolid(x + 23, y + 23 + shift)) {
                y = y + shift;
            } else {
                for (int step = shift - 1; step > 0; step--)
                    if (!isCellSolid(x, y + 23 + step) && !isCellSolid(x + 23, y + 23 + step)) {
                        y = y + step;
                        step = 0;
                    }
            }
            if ((isCellSolid(x, y + 24) || isCellSolid(x + 23, y + 24))
                    && !isCellSolid(x + 26, y + 24)) {
                if (!isCellSolid(x + 23 + shift, y) && !isCellSolid(x + 23 + shift, y + 23))
                    x = x + shift;
            } else if ((isCellSolid(x, y + 24) || isCellSolid(x + 23, y + 24))
                    && !isCellSolid(x - 2, y + 24)) {
                if (!isCellSolid(x - shift, y) && !isCellSolid(x - shift, y + 23))
                    x = x - shift;
            }
            //log.info(this.y);
        }
        if (direction == Movable.Direction.DOWN) {
            if (!isCellSolid(x, y - shift) && !isCellSolid(x + 23, y - shift)) {
                y = y - shift;
            } else {
                for (int step = shift - 1; step > 0; step--)
                    if (!isCellSolid(x, y - step) && !isCellSolid(x + 23, y - step)) {
                        y = y - step;
                        step = 0;
                    }
            }
            if ((isCellSolid(x, y - 1) || isCellSolid(x + 23, y - 1))
                    && !isCellSolid(x + 26, y - 1)) {
                if (!isCellSolid(x + 23 + shift, y) && !isCellSolid(x + 23 + shift, y + 23))
                    x = x + shift;
            } else if ((isCellSolid(x, y - 1) || isCellSolid(x + 23, y - 1))
                    && !isCellSolid(x - 2, y - 1)) {
                if (!isCellSolid(x - shift, y) && !isCellSolid(x - shift, y + 23))
                    x = x - shift;
            }
        }
        if (direction == Movable.Direction.LEFT) {
            if (!isCellSolid(x - shift, y) && !isCellSolid(x - shift, y + 23)) {
                x = x - shift;
            } else {
                for (int step = shift - 1; step > 0; step--)
                    if (!isCellSolid(x - step, y) && !isCellSolid(x - step, y + 23)) {
                        x = x - step;
                        step = 0;
                    }
            }
            if ((isCellSolid(x - 1, y) || isCellSolid(x - 1, y + 23))
                    && !isCellSolid(x - 1, y + 26)) {
                if (!isCellSolid(x, y + 23 + shift) && !isCellSolid(x + 23, y + 23 + shift))
                    y = y + shift;
            } else if ((isCellSolid(x - 1, y) || isCellSolid(x - 1, y + 23))
                    && !isCellSolid(x - 1, y - 2)) {
                if (!isCellSolid(x, y - shift) && !isCellSolid(x + 23, y - shift))
                    y = y - shift;
            }
        }
        if (direction == Movable.Direction.RIGHT) {
            if (!isCellSolid(x + 23 + shift, y) && !isCellSolid(x + 23 + shift, y + 23)) {
                x = x + shift;
            } else {
                for (int step = shift - 1; step > 0; step--)
                    if (!isCellSolid(x + 23 + step, y) && !isCellSolid(x + 23 + step, y + 23)) {
                        x = x + step;
                        step = 0;
                    }
            }
            if ((isCellSolid(x + 24, y) || isCellSolid(x + 24, y + 23))
                    && !isCellSolid(x + 24, y + 26)) {
                if (!isCellSolid(x, y + 23 + shift) && !isCellSolid(x + 23, y + 23 + shift))
                    y = y + shift;
            } else if ((isCellSolid(x + 24, y) || isCellSolid(x + 24, y + 23))
                    && !isCellSolid(x + 24, y - 2)) {
                if (!isCellSolid(x, y - shift) && !isCellSolid(x + 23, y - shift))
                    y = y - shift;
            }
        }
        return new Point(x, y);
    }

    public String toJson() {
        String json = "{\"position\":{\"x\":" + this.x + ",\"y\":" + this.y + "},\"id\":" +
                this.getId() + ",\"velocity\":" +
                this.velocity + ",\"maxBombs\":" +
                this.maxBombs + ",\"speedModifier\":" +
                this.speedModifier + ",\"type\":\"Pawn\"}";
        return json;
    }

    public DirectionMessage receiveDirection(@NotNull WebSocketSession session, @NotNull String msg) {
        DirectionMessage message = JsonHelper.fromJson(msg, DirectionMessage.class);
        return message;
    }

    public boolean isCellSolid(int x, int y) {
        boolean result = false;
        switch (bombType) {
            case BOMB1: {
                result = (gameSession.getCellFromGameArea(x,
                        y).getState().contains(State.WALL)) || (gameSession.getCellFromGameArea(x, y)
                        .getState().contains(State.BOX))
                        || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB2)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB3)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB4));
                break;
            }
            case BOMB2: {
                result = (gameSession.getCellFromGameArea(x,
                        y).getState().contains(State.WALL)) || (gameSession.getCellFromGameArea(x, y)
                        .getState().contains(State.BOX))
                        || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB1)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB3)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB4));
                break;
            }
            case BOMB3: {
                result = (gameSession.getCellFromGameArea(x,
                        y).getState().contains(State.WALL)) || (gameSession.getCellFromGameArea(x, y)
                        .getState().contains(State.BOX))
                        || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB2)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB1)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB4));
                break;
            }
            case BOMB4: {
                result = (gameSession.getCellFromGameArea(x,
                        y).getState().contains(State.WALL)) || (gameSession.getCellFromGameArea(x, y)
                        .getState().contains(State.BOX))
                        || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB2)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB3)) || (gameSession.getCellFromGameArea(x,
                        y)
                        .getState().contains(State.BOMB4));
                break;
            }
            default:
                break;
        }
        return result;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }

    public int bombPlantedCount() {
        int count = 0;
        for (boolean s : bombStatus) {
            if (s)
                count++;
        }
        return count;
    }

    public void changeBombStatus() {
        for (boolean b : bombStatus)
            if (b)
                bombStatus.poll();
    }


}
