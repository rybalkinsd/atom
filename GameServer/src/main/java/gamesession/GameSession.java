package gamesession;

import dto.ObjectDto;
import dto.PawnDto;
import geometry.Bar;
import geometry.Point;
import objects.Bomb;
import objects.Player;
import objects.Fire;
import objects.GameObject;
import objects.Wood;
import objects.Tickable;
import objects.Movable;
import objects.Wall;
import objects.Positionable;
import websocket.ConnectionPool;

import java.util.ArrayList;
import java.util.Objects;

/*
* Store game map that changing by game mechanic
*/
public class GameSession {

    private static ArrayList<Player> mapPlayers = new ArrayList<>();
    private static ArrayList<Wall> mapWalls = new ArrayList<>();
    private static ArrayList<Bomb> mapBombs = new ArrayList<>();
    private static ArrayList<Wood> mapWood = new ArrayList<>();
    private static ArrayList<Fire> mapFire = new ArrayList<>();


    public GameSession() {
        for (String name: ConnectionPool.getInstance().getAllPlayers()) {
            mapPlayers.add(new Player(name));
        }
    }

    public static ArrayList<Player> getMapPlayers() {
        return mapPlayers;
    }

    public static ArrayList<Bomb> getMapBombs() {
        return mapBombs;
    }

    public static ArrayList<Wall> getMapWalls() {
        return mapWalls;
    }

    public static ArrayList<Wood> getMapWoods() {
        return mapWood;
    }

    public static ArrayList<Fire> getMapFire() {
        return mapFire;
    }

    public static void addPlayer(String name) {
        mapPlayers.add(new Player(name));
    }

    public static void addPlayer(Player player) {
        mapPlayers.add(player);
    }

    public static void addBomb(Point point) {
        mapBombs.add(new Bomb(point));
    }

    public static void addBomb(Bomb bomb) {
        mapBombs.add(bomb);
    }

    public static void addWood(Wood brick) {
        mapWood.add(brick);
    }

    public static void addWood(Point point) {
        mapWood.add(new Wood(point));
    }

    public static void addWall(Wall wall) {
        mapWalls.add(wall);
    }

    public static void addWall(Point point) {
        mapWalls.add(new Wall(point));
    }

    public static ArrayList<Object> getAllObjects() {
        ArrayList<Object> objects = new ArrayList<>();
        objects.addAll(mapWalls);
        objects.addAll(mapWood);
        objects.addAll(mapFire);
        objects.addAll(mapBombs);
        objects.addAll(mapPlayers);
        return objects;
    }

    public static ArrayList<Object> getAllDto() {
        ArrayList<Object> objects = new ArrayList<>();
        for (Wall wall: mapWalls) {
            objects.add(new ObjectDto(wall));
        }
        for (Wood wood: mapWood) {
            objects.add(new ObjectDto(wood));
        }
        for (Fire fire: mapFire) {
            objects.add(new ObjectDto(fire));
        }
        for (Bomb bomb: mapBombs) {
            objects.add(new ObjectDto(bomb));
        }
        for (Player player: mapPlayers) {
            objects.add(new PawnDto(player));
        }

        return objects;
    }

    public static int getPlayerId(String name) {
        for (Player player: mapPlayers) {
            if (Objects.equals(player.getName(), name)) {
                return player.getId();
            }
        }
        return -1;
    }

    public static void createMap() {
        for (double x = 0; x < GameObject.width * 17; x += GameObject.width) {
            GameSession.addWall(new Point(x, 0));
            GameSession.addWall(new Point(x, GameObject.width * 12));
        }
        for (double y = 0; y < GameObject.height * 13; y += GameObject.height) {
            GameSession.addWall(new Point(0, y));
            GameSession.addWall(new Point(GameObject.width * 16, y));
        }
        for (double x = GameObject.width * 2; x < GameObject.width * 15; x += GameObject.width * 2) {
            GameSession.addWall(new Point(x, GameObject.height * 2));
            GameSession.addWall(new Point(x, GameObject.height * 4));
            GameSession.addWall(new Point(x, GameObject.height * 6));
            GameSession.addWall(new Point(x, GameObject.height * 8));
            GameSession.addWall(new Point(x, GameObject.height * 10));
        }
        for (double x = GameObject.width; x < GameObject.width * 16; x += GameObject.width) {
            if (x > 64 && x < 448) {
                GameSession.addWood(new Point(x, GameObject.width));
                GameSession.addWood(new Point(x, GameObject.width * 11));
            }
            GameSession.addWood(new Point(x, GameObject.width * 3));
            GameSession.addWood(new Point(x, GameObject.width * 5));
            GameSession.addWood(new Point(x, GameObject.width * 7));
            GameSession.addWood(new Point(x, GameObject.width * 9));
        }
        for (double x = GameObject.width; x < GameObject.width * 17; x += GameObject.width * 2) {
            if (x > 32 && x < 480) {
                GameSession.addWood(new Point(x, GameObject.width * 2));
                GameSession.addWood(new Point(x, GameObject.width * 10));
            }
            GameSession.addWood(new Point(x, GameObject.width * 4));
            GameSession.addWood(new Point(x, GameObject.width * 6));
            GameSession.addWood(new Point(x, GameObject.width * 8));
        }
    }

    public static boolean getWallByPoint(Point point) {
        for (Wall wall: mapWalls) {
            if (Objects.equals(wall.getPosition(), point))
                return true;
        }
        return false;
    }
}
