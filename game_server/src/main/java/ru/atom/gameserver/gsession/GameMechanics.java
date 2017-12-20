package ru.atom.gameserver.gsession;

import com.fasterxml.jackson.databind.JsonNode;
import ru.atom.gameserver.geometry.Bar;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.message.Message;
import ru.atom.gameserver.model.Bomb;
import ru.atom.gameserver.model.Buff;
import ru.atom.gameserver.model.Fire;
import ru.atom.gameserver.model.GameObject;
import ru.atom.gameserver.model.Grass;
import ru.atom.gameserver.model.Movable;
import ru.atom.gameserver.model.Pawn;
import ru.atom.gameserver.model.Static;
import ru.atom.gameserver.model.Wall;
import ru.atom.gameserver.model.Wood;
import ru.atom.gameserver.tick.Tickable;
import ru.atom.gameserver.tick.Ticker;
import ru.atom.gameserver.util.JsonHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameMechanics implements Tickable, GarbageCollector, ModelsManager {

    private static final int DEF_SIZE = 32;

    private final Ticker ticker;
    private final Replicator replicator;
    private final InputQueue inputQueue;

    private final Map<Integer, Pawn> pawns = new HashMap<>();
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private final Set<GameObject> garbageIndexSet = new HashSet<>();
    private int idGenerator = 0;

    private final Field field;

    GameMechanics(Ticker ticker, Replicator replicator, InputQueue inputQueue) {
        this.ticker = ticker;
        this.replicator = replicator;
        this.inputQueue = inputQueue;
        this.field = new Field();
        initByMap();
    }

    private int nextId() {
        return idGenerator++;
    }

    private void initByMap() {
        List<Wood> woodCollection = new ArrayList<>();
        for (int row = 0; row < Field.ROWS; ++row) {
            for (int col = 0; col < Field.COLS; ++col) {
                Field.Cell cell = new Field.Cell(col, row);
                int id = nextId();
                switch (field.getCellType(cell)) {
                    case Field.WALL: {
                        field.setId(cell, id);
                        gameObjects.add(new Wall(id, indexToPoint(col, row)));
                    }
                        break;
                    case Field.EMPTY: {
                        field.setId(cell, id);
                        gameObjects.add(new Grass(id, indexToPoint(col, row)));
                    }
                        break;
                    case Field.BOX: {
                        gameObjects.add(new Grass(id, indexToPoint(col, row)));
                        id = nextId();
                        field.setId(cell, id);
                        Wood wood = new Wood(id, indexToPoint(col, row));
                        gameObjects.add(wood);
                        woodCollection.add(wood);
                    }
                        break;
                    default:
                        break;
                }
            }
        }
        Collections.shuffle(woodCollection);
        Random rnd = new Random();
        Buff.BuffType[] buffTypes = Buff.BuffType.values();
        for (int i = 0; i < 30; ++i) {
            woodCollection.get(i).setBuffType(buffTypes[rnd.nextInt(buffTypes.length)]);
        }
    }

    int addPlayer() {
        int id = nextId();
        Point point = null;
        switch (pawns.size()) {
            case 0:
                point = new Point(DEF_SIZE + 1.0f, DEF_SIZE + 1.0f);
                break;
            case 1:
                point = new Point(DEF_SIZE * 15 - 1.0f, DEF_SIZE * 11 - 1.0f);
                break;
            case 2:
                point = new Point(DEF_SIZE + 1.0f, DEF_SIZE * 11 - 1.0f);
                break;
            case 3:
                point = new Point(DEF_SIZE * 15 - 1.0f, DEF_SIZE + 1.0f);
                break;
            default:
                point = null;
        }
        Pawn pawn = new Pawn(id, point, 100.0f, 1);
        pawn.setGarbageCollector(this);
        pawn.setModelsManager(this);
        gameObjects.add(pawn);
        pawns.put(id, pawn);
        ticker.insertTickableFront(pawn);
        return id;
    }

    @Override
    public void tick(long elapsed) {
        boolean gameOverFlag = false;

        Set<Integer> translated = new HashSet<>();
        List<Message> messages = inputQueue.pollMessages();
        for (Message message : messages) {
            JsonNode jsonNode = JsonHelper.getJsonNode(message.getData());
            switch (message.getTopic()) {
                case MOVE: {
                    int possess = jsonNode.get("possess").asInt();
                    if (!pawns.containsKey(possess) || translated.contains(possess)) {
                        continue;
                    }
                    translated.add(possess);
                    Movable.Direction direction = Movable.Direction.valueOf(jsonNode.get("direction").asText());
                    pawns.get(possess).move(direction, elapsed);
                }
                    break;
                case PLANT_BOMB: {
                    int possess = jsonNode.asInt();
                    if (!pawns.containsKey(possess) || translated.contains(possess)) {
                        continue;
                    }
                    translated.add(possess);
                    pawns.get(possess).plainBombEvent();
                }
                    break;
                default:
                    break;
            }
        }

        for (GameObject gameObject : garbageIndexSet) {
            if (gameObject instanceof Tickable) {
                ticker.unregisterTickable((Tickable) gameObject);
            }
            if (gameObject instanceof Pawn) {
                pawns.remove(gameObject.getId());
            }
            gameObjects.remove(gameObject);
        }
        garbageIndexSet.clear();
        if (pawns.size() < 2) {
            gameOverFlag = true;
            ticker.stopGameLoop();
        }
        replicator.writeReplica(gameObjects, gameOverFlag);
        if (gameOverFlag) {
            replicator.writeWinner(pawns.isEmpty() ? -1 : pawns.keySet().stream().findFirst().get());
        }
    }

    private Point indexToPoint(int i, int j) {
        return new Point(i * DEF_SIZE, j * DEF_SIZE);
    }

    private Point normilizePoint(Point point) {
        return new Point(DEF_SIZE * ((int)point.getX() / DEF_SIZE), DEF_SIZE * ((int)point.getY() / DEF_SIZE));
    }

    private Field.Cell pointToCell(Point point) {
        int col = (int)point.getX() / DEF_SIZE;
        int row = (int)point.getY() / DEF_SIZE;
        return  new Field.Cell(col, row);
    }

    private Point cellToPoint(Field.Cell cell) {
        return new Point(cell.col * DEF_SIZE, cell.row * DEF_SIZE);
    }

    @Override
    public void mark(GameObject gameObject) {
        garbageIndexSet.add(gameObject);
    }

    @Override
    public Bomb putBomb(Point point, long lifetime, int power) {
        Point normPoint = normilizePoint(point);
        Bomb bomb = new Bomb(nextId(), normPoint, lifetime, power);
        bomb.setGarbageCollector(this);
        bomb.setModelsManager(this);
        gameObjects.add(bomb);
        ticker.insertTickableFront(bomb);
        return bomb;
    }

    @Override
    public List<Fire> putFire(Point point, long lifetime, int power) {
        List<Field.Cell> cells = field.getFireCells(pointToCell(point), power);
        List<Fire> fires = new ArrayList<>();
        for (Field.Cell fireCell : cells) {
            Fire fire = new Fire(nextId(), cellToPoint(fireCell), lifetime);
            fire.setGarbageCollector(this);
            fire.setModelsManager(this);
            gameObjects.add(fire);
            ticker.insertTickableFront(fire);
            fires.add(fire);
        }
        cells = field.applyFireCells(cells);
        for (Field.Cell cell : cells) {
            int id = field.getId(cell);
            Wood wood = (Wood)gameObjects.stream().filter(g -> g.getId() == id).findFirst().get();
            if (wood.containsBuff()) {
                putBonus(wood.getPosition(), wood.getBuffType());
            }
            garbageIndexSet.add(wood);
        }
        return fires;
    }

    @Override
    public Buff putBonus(Point point, Buff.BuffType buffType) {
        Buff buff = new Buff(nextId(), point, buffType);
        buff.setGarbageCollector(this);
        buff.setModelsManager(this);
        field.setBonus(pointToCell(point));
        gameObjects.add(buff);
        ticker.insertTickableFront(buff);
        return buff;
    }

    @Override
    public List<Pawn> getIntersectPawns(Bar bar) {
        List<Pawn> intersectPawns = new ArrayList<>();
        for (Pawn pawn : pawns.values()) {
            if (pawn.getBar().isColliding(bar)) {
                intersectPawns.add(pawn);
            }
        }
        return intersectPawns;
    }

    @Override
    public List<GameObject> getIntersectStatic(Bar bar) {
        List<GameObject> staticObjects = new ArrayList<>();
        gameObjects.forEach(gameObject -> {
            if (gameObject instanceof Static) {
                if (gameObject.getBar().isColliding(bar)) {
                    staticObjects.add(gameObject);
                }
            }
        });
        return staticObjects;
    }
}
