package gs;

import gs.model.Bomb;
import gs.model.Explosion;
import gs.model.Pawn;
import gs.model.Wall;
import gs.tick.Tickable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.WebSocketSession;
import gs.geometry.Point;
import gs.model.Wall.Type;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 */
public class GameSession implements Tickable {
    private static final Logger log = LogManager.getLogger(GameSession.class);

    private ConcurrentHashMap<Point, Wall> allWalls = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Bomb> allBombs = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Explosion> allExplosions = new ConcurrentHashMap<>();

    private static AtomicLong idGenerator = new AtomicLong();

    public static final int PLAYERS_IN_GAME = 2;

    Pawn pawn;
    private final int playerCount;
    private final long Id = idGenerator.getAndIncrement();
    private int id=0;
    private WebSocketSession session;

    public void setSession(WebSocketSession session) {
        this.session = session;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public GameSession(int playerCount) {
        this.playerCount = playerCount;
    }

   void Test(){
        Point point =new Point(1,1);
        allWalls.put(point, new Wall(1,1, Wall.Type.Wall, 1));
        Wall obj = allWalls.get(point);
        System.out.println(allWalls.values());
        System.out.println(obj);
        System.out.println(obj.getType());
    }

    public void initCanvas() {
        pawn = new Pawn(32,32,300);

        for (int i = 0; i < 13; ++i) {
            for (int k = 0; k < 17; ++k) {
                if (i == 0 || i == 12) {
                    allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                } else if (i == 1 || i == 11) {
                    if (k == 0 || k == 16) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                    } else if (k == 1 || k == 2 || k == 14 || k == 15) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Grass, incId()));
                    } else {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wood, incId()));
                    }
                } else if (i == 2 || i == 10) {
                    if (k == 0 || k == 16) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                    } else if (k == 1 || k == 15) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Grass, incId()));
                    } else {
                        if (k % 2 == 0) {
                            allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                        } else {
                            allWalls.put(new Point(k, i), new Wall(k, i, Type.Wood, incId()));
                        }
                    }
                } else if (i % 2 != 0) {
                    if (k == 0 || k == 16) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                    } else {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wood, incId()));
                    }
                } else {
                    if (k % 2 == 0) {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wall, incId()));
                    } else {
                        allWalls.put(new Point(k, i), new Wall(k, i, Type.Wood, incId()));
                    }
                }
            }
        }
        //System.out.println(allWalls.values());
    }

    public String jsonStringWalls() {
        String objJSON = "";
        for (Point p : allWalls.keySet()) {
            Wall obj = allWalls.get(p);
            objJSON = objJSON + obj.toJson() + ",";
        }
        String result = objJSON.substring(0, (objJSON.length() - 1));
        return result;
    }

    public String jsonStringBombs() {
        if (allBombs.size() == 0) {
            return null;
        } else {
            String objJSON = "";
            for (Integer i : allBombs.keySet()) {
                Bomb obj = allBombs.get(i);
                objJSON = objJSON + obj.toJson() + ",";
            }
            String result = objJSON.substring(0, (objJSON.length() - 1));
            return result;
        }
    }

    public String jsonStringExplosions() {
        if (allExplosions.size() == 0) {
            return null;
        } else {
            String objJSON = "";
            for (Integer i : allExplosions.keySet()) {
                Explosion obj = allExplosions.get(i);
                objJSON = objJSON + obj.toJson() + ",";
            }
            String result = objJSON.substring(0, (objJSON.length() - 1));
            return result;
        }
    }


    private int incId(){
        return id++;
    }

    public long getId() {
        return Id;
    }

    public Pawn getPawn() {
        return pawn;
    }

    public void tick(long elapsed) {

    }

    ;
}
