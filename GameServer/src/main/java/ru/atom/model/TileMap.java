package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class TileMap {
    private List<Tile> tiles = new Vector<>();
    private final int width;
    private final int height;
    private final int tileHeight;
    private final int tileWidth;



    public TileMap(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        for (int i = 0; i < width * height; i++) {
            tiles.add(new Tile());
        }
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


    private List<Tile> getTilesUnderGeomObject(GeomObject geomObject) {
        List<Tile> tempTiles = new ArrayList<>();
        if (geomObject instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) geomObject;
            int startColumn = (int)(rectangle.getPosition().getX() / tileWidth);
            int startRow = (int)(rectangle.getPosition().getY() / tileHeight);
            int endRow = (int)((rectangle.getPosition().getY() + rectangle.getHeight()) / tileHeight + 1);
            int endColumn = (int)((rectangle.getPosition().getX() + rectangle.getWidth()) / tileWidth + 1);
            for (int row = startRow; row < endRow; row++) {
                for (int column = startColumn; column < endColumn; column++) {
                    if (row < height && row >= 0 && column < width && column >= 0) {
                        tempTiles.add(tiles.get(row * width + column));
                    }
                }
            }

        }
        return tempTiles;
    }

    public void placeGameObject(FormedGameObject gameObject) {
        List<Tile> tempTiles = getTilesUnderGeomObject(gameObject.getForm());
        for (Tile tile : tempTiles) {
            tile.geomObjects.put(gameObject.getId(), gameObject);
        }
    }

    public void removeGameObject(FormedGameObject gameObject) {
        List<Tile> tempTiles = getTilesUnderGeomObject(gameObject.getForm());
        for (Tile tile : tempTiles) {
            tile.geomObjects.remove(gameObject.getId());
        }
    }

    public HashSet<FormedGameObject> getNearbyGameObjects(FormedGameObject gameObject) {
        List<Tile> tempTiles = getTilesUnderGeomObject(gameObject.getForm());
        HashSet<FormedGameObject> gameObjects = new HashSet<FormedGameObject>();

        for (Tile tile : tempTiles) {
            tile.geomObjects.entrySet().forEach(geomEntry -> {
                if (geomEntry.getValue().getId() != gameObject.getId()) {
                    gameObjects.add(geomEntry.getValue());
                }
            });
        }
        return gameObjects;
    }


}
