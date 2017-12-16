package gameobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class Bomb extends Field implements Positionable, Tickable, Comparable {
    private static final Logger log = LogManager.getLogger(Bomb.class);
    private final int id;
    private GameSession gameSession;
    private long time;
    private boolean alive;
    private int x;
    private int y;
    private int bombPower;
    private BomberGirl owner;
    private State bombType;


    public Bomb(int x, int y, GameSession gameSession, int bombPower, BomberGirl owner, State bombType) {
        super((x / 32) * 32, (y / 32) * 32);
        this.x = (x / 32) * 32;
        this.y = (y / 32) * 32;
        this.id = getId();
        this.alive = true;
        this.bombType = bombType;
        this.gameSession = gameSession;
        this.time = 2000;
        this.bombPower = bombPower;
        this.owner = owner;
        log.info("Bombid = " + id + "; " + "Bomb place = (" + this.x + "," +
                this.y + ")" + "; " + "Bomb timer = " + time);
    }

    public void bang() {
        log.info("KaBoom!");
        for (int i = 0; i < bombPower; i++) {
            if (!gameSession.getCellFromGameArea(x, y + 32 + i * 32)
                    .getState().contains(State.WALL)) {
                gameSession.removeStateFromCell(x, y + 32 + i * 32, State.BOMBERGIRL);
                gameSession.addStateToCell(x, y + 32 + i * 32, State.EXPLOSION);
                gameSession.addGameObject(new Explosion(x,
                        y + 32 + i * 32, gameSession));
                if (gameSession.removeStateFromCell(x, y + 32 + i * 32, State.BOX)) break;
            } else break;
        }
        for (int i = 0; i < bombPower; i++) {
            if (!gameSession.getCellFromGameArea(x, y - 32 - i * 32)
                    .getState().contains(State.WALL)) {
                gameSession.removeStateFromCell(x, y - 32 - i * 32, State.BOMBERGIRL);
                gameSession.addStateToCell(x, y - 32 - i * 32, State.EXPLOSION);
                gameSession.addGameObject(new Explosion(x,
                        y - 32 - i * 32, gameSession));
                if (gameSession.removeStateFromCell(x, y - 32 - i * 32, State.BOX)) break;
            } else break;
        }
        for (int i = 0; i < bombPower; i++) {
            if (!gameSession.getCellFromGameArea(x + 32 + i * 32, y)
                    .getState().contains(State.WALL)) {
                gameSession.removeStateFromCell(x + 32 + i * 32, y, State.BOMBERGIRL);
                gameSession.addStateToCell(x + 32 + i * 32, y, State.EXPLOSION);
                gameSession.addGameObject(new Explosion(x + 32 + i * 32,
                        y, gameSession));
                if (gameSession.removeStateFromCell(x + 32 + i * 32, y, State.BOX)) break;
            } else break;
        }
        for (int i = 0; i < bombPower; i++) {
            if (!gameSession.getCellFromGameArea(x - 32 - i * 32, y)
                    .getState().contains(State.WALL)) {
                gameSession.removeStateFromCell(x - 32 - i * 32, y, State.BOMBERGIRL);
                gameSession.addStateToCell(x - 32 - i * 32, y, State.EXPLOSION);
                gameSession.addGameObject(new Explosion(x - 32 - i * 32, y, gameSession));
                if (gameSession.removeStateFromCell(x - 32 - i * 32, y, State.BOX)) break;
            } else break;
        }
        gameSession.removeStateFromCell(x, y, bombType);
        gameSession.addStateToCell(x, y, State.EXPLOSION);
        gameSession.addGameObject(new Explosion(x,
                y, gameSession));
    }

    @Override
    public void tick(long elapsed) {
        log.info("bomb {} tick", id);
        if (alive) {
            if (time < elapsed) {
                time = 0;
                alive = false;
                owner.changeBombStatus();
                bang();
            } else {
                time -= elapsed;
            }
        } else gameSession.removeGameObject(this);
    }


    public String toJson() {
        String json = "{\"type\":\"Bomb\",\"id\":" +
                this.getId() + ",\"position\":{\"x\":" + (x + 7) + ",\"y\":" + (y - 7) + "}}";
        return json;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        return 0;
    }

}

