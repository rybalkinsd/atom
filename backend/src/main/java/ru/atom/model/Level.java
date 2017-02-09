package ru.atom.model;

import ru.atom.model.actor.tile.SpawnPlace;
import ru.atom.model.actor.tile.Tile;
import ru.atom.model.actor.tile.Wall;
import ru.atom.model.actor.tile.Wood;
import ru.atom.util.V;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by sergey on 2/8/17.
 */
public enum Level {
    STANDARD(
            new Wall(V.of(0, 12)), new Wall(V.of(1, 12)), new Wall(V.of(2, 12)), new Wall(V.of(3, 12)), new Wall(V.of(4, 12)), new Wall(V.of(5, 12)), new Wall(V.of(6, 12)), new Wall(V.of(7, 12)), new Wall(V.of(8, 12)), new Wall(V.of(9, 12)), new Wall(V.of(10, 12)), new Wall(V.of(11, 12)), new Wall(V.of(12, 12)), new Wall(V.of(13, 12)), new Wall(V.of(14, 12)), new Wall(V.of(15, 12)), new Wall(V.of(16, 12)),
            new Wall(V.of(0, 11)), new SpawnPlace(V.of(1, 11)),                  new Wood(V.of(3, 11)), new Wood(V.of(4, 11)), new Wood(V.of(5, 11)), new Wood(V.of(6, 11)), new Wood(V.of(7, 11)), new Wood(V.of(8, 11)), new Wood(V.of(9, 11)), new Wood(V.of(10, 11)), new Wood(V.of(11, 11)), new Wood(V.of(12, 11)), new Wood(V.of(13, 11)),                   new SpawnPlace(V.of(15, 11)), new Wall(V.of(16, 11)),
            new Wall(V.of(0, 10)),                      new Wall(V.of(2, 10)), new Wood(V.of(3, 10)), new Wall(V.of(4, 10)), new Wood(V.of(5, 10)), new Wall(V.of(6, 10)), new Wood(V.of(7, 10)), new Wall(V.of(8, 10)), new Wood(V.of(9, 10)), new Wall(V.of(10, 10)), new Wood(V.of(11, 10)), new Wall(V.of(12, 10)), new Wood(V.of(13, 10)), new Wall(V.of(14, 10)),                           new Wall(V.of(16, 10)),
            new Wall(V.of(0, 9)), new Wood(V.of(1, 9)), new Wood(V.of(2, 9)), new Wood(V.of(3, 9)), new Wood(V.of(4, 9)), new Wood(V.of(5, 9)), new Wood(V.of(6, 9)), new Wood(V.of(7, 9)), new Wood(V.of(8, 9)), new Wood(V.of(9, 9)), new Wood(V.of(10, 9)), new Wood(V.of(11, 9)), new Wood(V.of(12, 9)), new Wood(V.of(13, 9)), new Wood(V.of(14, 9)), new Wood(V.of(15, 9)),                 new Wall(V.of(16, 9)),
            new Wall(V.of(0, 8)), new Wood(V.of(1, 8)), new Wall(V.of(2, 8)), new Wood(V.of(3, 8)), new Wall(V.of(4, 8)), new Wood(V.of(5, 8)), new Wall(V.of(6, 8)), new Wood(V.of(7, 8)), new Wall(V.of(8, 8)), new Wood(V.of(9, 8)), new Wall(V.of(10, 8)), new Wood(V.of(11, 8)), new Wall(V.of(12, 8)), new Wood(V.of(13, 8)), new Wall(V.of(14, 8)), new Wood(V.of(15, 8)),                 new Wall(V.of(16, 8)),
            new Wall(V.of(0, 7)), new Wood(V.of(1, 7)), new Wood(V.of(2, 7)), new Wood(V.of(3, 7)), new Wood(V.of(4, 7)), new Wood(V.of(5, 7)), new Wood(V.of(6, 7)), new Wood(V.of(7, 7)), new Wood(V.of(8, 7)), new Wood(V.of(9, 7)), new Wood(V.of(10, 7)), new Wood(V.of(11, 7)), new Wood(V.of(12, 7)), new Wood(V.of(13, 7)), new Wood(V.of(14, 7)), new Wood(V.of(15, 7)),                 new Wall(V.of(16, 7)),
            new Wall(V.of(0, 6)), new Wood(V.of(1, 6)), new Wall(V.of(2, 6)), new Wood(V.of(3, 6)), new Wall(V.of(4, 6)), new Wood(V.of(5, 6)), new Wall(V.of(6, 6)), new Wood(V.of(7, 6)), new Wall(V.of(8, 6)), new Wood(V.of(9, 6)), new Wall(V.of(10, 6)), new Wood(V.of(11, 6)), new Wall(V.of(12, 6)), new Wood(V.of(13, 6)), new Wall(V.of(14, 6)), new Wood(V.of(15, 6)),                 new Wall(V.of(16, 6)),
            new Wall(V.of(0, 5)), new Wood(V.of(1, 5)), new Wood(V.of(2, 5)), new Wood(V.of(3, 5)), new Wood(V.of(4, 5)), new Wood(V.of(5, 5)), new Wood(V.of(6, 5)), new Wood(V.of(7, 5)), new Wood(V.of(8, 5)), new Wood(V.of(9, 5)), new Wood(V.of(10, 5)), new Wood(V.of(11, 5)), new Wood(V.of(12, 5)), new Wood(V.of(13, 5)), new Wood(V.of(14, 5)), new Wood(V.of(15, 5)),                 new Wall(V.of(16, 5)),
            new Wall(V.of(0, 4)), new Wood(V.of(1, 4)), new Wall(V.of(2, 4)), new Wood(V.of(3, 4)), new Wall(V.of(4, 4)), new Wood(V.of(5, 4)), new Wall(V.of(6, 4)), new Wood(V.of(7, 4)), new Wall(V.of(8, 4)), new Wood(V.of(9, 4)), new Wall(V.of(10, 4)), new Wood(V.of(11, 4)), new Wall(V.of(12, 4)), new Wood(V.of(13, 4)), new Wall(V.of(14, 4)), new Wood(V.of(15, 4)),                 new Wall(V.of(16, 4)),
            new Wall(V.of(0, 3)), new Wood(V.of(1, 3)), new Wood(V.of(2, 3)), new Wood(V.of(3, 3)), new Wood(V.of(4, 3)), new Wood(V.of(5, 3)), new Wood(V.of(6, 3)), new Wood(V.of(7, 3)), new Wood(V.of(8, 3)), new Wood(V.of(9, 3)), new Wood(V.of(10, 3)), new Wood(V.of(11, 3)), new Wood(V.of(12, 3)), new Wood(V.of(13, 3)), new Wood(V.of(14, 3)), new Wood(V.of(15, 3)),                 new Wall(V.of(16, 3)),
            new Wall(V.of(0, 2)),                       new Wall(V.of(2, 2)), new Wood(V.of(3, 2)), new Wall(V.of(4, 2)), new Wood(V.of(5, 2)), new Wall(V.of(6, 2)), new Wood(V.of(7, 2)), new Wall(V.of(8, 2)), new Wood(V.of(9, 2)), new Wall(V.of(10, 2)), new Wood(V.of(11, 2)), new Wall(V.of(12, 2)), new Wood(V.of(13, 2)), new Wall(V.of(14, 2)),                                        new Wall(V.of(16, 2)),
            new Wall(V.of(0, 1)), new SpawnPlace(V.of(1, 1)),                 new Wood(V.of(3, 1)), new Wood(V.of(4, 1)), new Wood(V.of(5, 1)), new Wood(V.of(6, 1)), new Wood(V.of(7, 1)), new Wood(V.of(8, 1)), new Wood(V.of(9, 1)), new Wood(V.of(10, 1)), new Wood(V.of(11, 1)), new Wood(V.of(12, 1)), new Wood(V.of(13, 1)),                                  new SpawnPlace(V.of(15, 1)), new Wall(V.of(16, 1)),
            new Wall(V.of(0, 0)), new Wall(V.of(1, 0)), new Wall(V.of(2, 0)), new Wall(V.of(3, 0)), new Wall(V.of(4, 0)), new Wall(V.of(5, 0)), new Wall(V.of(6, 0)), new Wall(V.of(7, 0)), new Wall(V.of(8, 0)), new Wall(V.of(9, 0)), new Wall(V.of(10, 0)), new Wall(V.of(11, 0)), new Wall(V.of(12, 0)), new Wall(V.of(13, 0)), new Wall(V.of(14, 0)), new Wall(V.of(15, 0)), new Wall(V.of(16, 0))
    );

    public static final int WIDTH = 17;
    public static final int HEIGHT = 13;
    public static final int MAX_PLAYERS = 4;
    private Tile[][] tiles;

    Level(Tile ... tiles) {
        this.tiles = new Tile[WIDTH][HEIGHT];

        Arrays.stream(tiles).forEach(t -> {
            this.tiles[(int) t.getPosition().getX()][(int) t.getPosition().getY()] = t;
        });
    }

    public List<V> getSpawnPlaces() {
        return filter(SpawnPlace.class);
    }

    private <T extends Tile> List<V> filter(Class<T> clazz) {
        return Arrays.stream(tiles)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull)
                .filter(clazz::isInstance)
                .map(Tile::getAbsolutePosition)
                .collect(Collectors.toList());
    }

    public Collection<Tile> getTiles() {
        return Arrays.stream(tiles)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Level ").append(name()).append(" {\n");
        for (int j = HEIGHT -1; j >= 0; j--) {
            for (int i = 0; i < WIDTH; i++) {
                if (tiles[i][j] == null) {
                    sb.append(" ");
                } else {
                    sb.append(tiles[i][j].getSimpleMapIcon());
                }
            }
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }


//    public void generateLevelTileConstructors() throws Exception {
//        for (int i = 0; i < Level.WIDTH; i++) {
//            System.out.print(String.format("new Wall(V.of(%d, %d)), ", i, Level.HEIGHT - 1));
//        }
//        System.out.println();
//
//        for (int j = Level.HEIGHT - 2; j > 0; j--) {
//            for (int i = 0; i < Level.WIDTH; i++) {
//                if (i == 0 || i == Level.WIDTH -1 || i % 2 == 0 && j % 2 == 0) {
//                    System.out.print(String.format("new Wall(V.of(%d, %d)), ", i, j));
//                } else {
//                    System.out.print(String.format("new Wood(V.of(%d, %d)), ", i, j));
//                }
//
//            }
//            System.out.println();
//        }
//
//        for (int i = 0; i < Level.WIDTH; i++) {
//            System.out.print(String.format("new Wall(V.of(%d, %d)), ", i, 0));
//        }
//        System.out.println();
//    }

}
