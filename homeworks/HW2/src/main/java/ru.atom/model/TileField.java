package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TileField {
    private List<Tile> tiles = new ArrayList<Tile>();
    private final int width;
    private final int height;
    private final int tileHeight;
    private final int tileWidth;



    public TileField(int width, int height, int tileWidth, int tileHeight) {
        this.width = width;
        this.height = height;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
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
            int startColumn = rectangle.getPosition().getX() / tileWidth;
            int startRow = rectangle.getPosition().getY() / tileHeight;
            int endRow = startRow + rectangle.getHeight() / tileHeight + 1;
            int endColumn = startColumn + rectangle.getWidth() / tileWidth + 1;
            for (int row = startRow; row < endRow; row++) {
                for (int column = startColumn; column < endColumn; column++) {
                    if (row < width && row >= 0 && column < width && column >= 0) {
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

    public List<FormedGameObject> getNearbyGameObjects(FormedGameObject gameObject) {
        List<Tile> tempTiles = getTilesUnderGeomObject(gameObject.getForm());
        List<FormedGameObject> gameObjects = new ArrayList<>();

        for (Tile tile : tempTiles) {
            for (Integer key: tile.geomObjects.keySet()) {
                if (tile.geomObjects.get(key).getId() != gameObject.getId()) {
                    gameObjects.add(tile.geomObjects.get(key));
                }
            }
        }
        return gameObjects;
    }


}
