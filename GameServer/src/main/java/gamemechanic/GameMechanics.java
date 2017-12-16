package gamemechanic;

import client.Action;
import client.Message;
import client.Topic;
import gamesession.GameSession;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class GameMechanics implements Tickable {
    private boolean collisionOut = false;
    private ArrayList<Action> lastActions = new ArrayList<>();

    public void changeTickables() {
        for (Action action: lastActions) {
            if (action == null) {
                continue;
            }
            for (Player player: GameSession.getMapPlayers()) {
                if (Objects.equals(player.getName(), action.getName())) {
                    if (Objects.equals(action.getMessage().getTopic(), Topic.MOVE)) {
                        player.setDirection(action.getMessage().getData().getDirection());
                    }
                    if (Objects.equals(action.getMessage().getTopic(), Topic.PLANT_BOMB)) {
                        GameSession.addBomb(new Bomb(closesPoint(player.getPosition())));
                    }
                }
            }
        }
        lastActions.clear();
    }

    public void readQueue() {

        while (lastActions.size() < 8) {
            lastActions.add(null);
        }

        for (Action action: InputQueue.getInstance()) {
            if (Objects.equals(action.getMessage().getTopic().toString(), "PLANT_BOMB")) {
                lastActions.add(GameSession.getPlayerId(action.getName()) - 1, action);
                continue;
            }
            lastActions.add(GameSession.getPlayerId(action.getName()) + 3, action);
        }
        InputQueue.getInstance().clear();
    }

    public void checkCollisions() {
        for (Player player: GameSession.getMapPlayers()) {
            Bar barPlayer = new Bar(player);
            for (Wall wall: GameSession.getMapWalls()) {
                Bar barWall = new Bar(wall);
                if (barPlayer.isColliding(barWall)) {
                    player.moveBack();
                }
            }

            for (Wood wood: GameSession.getMapWoods()) {
                Bar barWood = new Bar(wood);
                if (barPlayer.isColliding(barWood)) {
                    player.moveBack();
                }
            }

            collisionOut = false;

            for (Bomb bomb: GameSession.getMapBombs()) {
                Bar barBomb = new Bar(bomb);
                if (!barPlayer.isColliding(barBomb)) {
                    collisionOut = true;
                }

                if (barPlayer.isColliding(barBomb) & collisionOut) {
                    player.moveBack();
                    collisionOut = false;
                }
            }
            player.setDirection("IDLE");
        }
    }

    public Point closesPoint(Point point) {
        double modX = point.getX() % GameObject.width;
        double modY = point.getY() % GameObject.height;
        int divX = (int) point.getX() / (int) GameObject.width;
        int divY = (int) point.getY() / (int) GameObject.height;
        if (modX < 16) {
            if (modY < 16)
                return new Point(divX * GameObject.width, divY * GameObject.height);
            else
                return new Point(divX * GameObject.width, (divY + 1) * GameObject.height);
        } else if (modY < 16)
            return new Point((divX + 1) * GameObject.width, divY * GameObject.height);
        return new Point((divX + 1) * GameObject.width, (divY + 1) * GameObject.height);
    }

    public void detonationBomb() {
        ArrayList<Bomb> bombList = new ArrayList<>();
        ArrayList<Wood> woodList = new ArrayList<>();
        ArrayList<Player> playerList = new ArrayList<>();
        ArrayList<Fire> fireList = new ArrayList<>();

        boolean horizontalRight = true;
        boolean verticalUp = true;
        boolean horizontalLeft = true;
        boolean verticalDown = true;

        for (Fire fire: GameSession.getMapFire()) {
            if (fire.getLifeTime() <= 0) {
                fireList.add(fire);
            }
        }

        for (Bomb bomb: GameSession.getMapBombs()) {
            if (bomb.getLifeTime() <= 0) {
                bombList.add(bomb);

                Bar barBombVerticalUp1 = new Bar(Point.getUp1Position(bomb.getPosition()));
                Bar barBombVerticalUp2 = new Bar(Point.getUp2Position(bomb.getPosition()));
                Bar barBombHorizontalRight1 = new Bar(Point.getRight1Position(bomb.getPosition()));
                Bar barBombHorizontalRight2 = new Bar(Point.getRight2Position(bomb.getPosition()));

                Bar barBombVerticalDown1 = new Bar(Point.getDown1Position(bomb.getPosition()));
                Bar barBombVerticalDown2 = new Bar(Point.getDown2Position(bomb.getPosition()));
                Bar barBombHorizontalLeft1 = new Bar(Point.getLeft1Position(bomb.getPosition()));
                Bar barBombHorizontalLeft2 = new Bar(Point.getLeft2Position(bomb.getPosition()));

                for (Wall wall: GameSession.getMapWalls()) {
                    Bar barWall = new Bar(wall);
                    if (barWall.isColliding(barBombHorizontalRight1)) {
                        horizontalRight = false;
                    }
                    if (barWall.isColliding(barBombVerticalUp1)) {
                        verticalUp = false;
                    }
                    if (barWall.isColliding(barBombHorizontalLeft1)) {
                        horizontalLeft = false;
                    }
                    if (barWall.isColliding(barBombVerticalDown1)) {
                        verticalDown = false;
                    }
                }

                for (Wood wood: GameSession.getMapWoods()) {
                    Bar barWood = new Bar(wood);
                    if (verticalUp && (barWood.isColliding(barBombVerticalUp2)
                            || barWood.isColliding(barBombVerticalUp1))) {
                        woodList.add(wood);
                        continue;
                    }
                    if (horizontalRight && (barWood.isColliding(barBombHorizontalRight2)
                            || barWood.isColliding(barBombHorizontalRight1))) {
                        woodList.add(wood);
                        continue;
                    }
                    if (verticalDown && (barWood.isColliding(barBombVerticalDown2)
                            || barWood.isColliding(barBombVerticalDown1))) {
                        woodList.add(wood);
                        continue;
                    }
                    if (horizontalLeft && (barWood.isColliding(barBombHorizontalLeft2)
                            || barWood.isColliding(barBombHorizontalLeft1))) {
                        woodList.add(wood);
                    }
                }
                for (Player player: GameSession.getMapPlayers()) {
                    Bar barPlayer = new Bar(player);
                    if (verticalUp && (barPlayer.isColliding(barBombVerticalUp2)
                            || barPlayer.isColliding(barBombVerticalUp1))) {
                        playerList.add(player);
                        continue;
                    }
                    if (horizontalRight && (barPlayer.isColliding(barBombHorizontalRight2)
                            || barPlayer.isColliding(barBombHorizontalRight1))) {
                        playerList.add(player);
                        continue;
                    }
                    if (verticalDown && (barPlayer.isColliding(barBombVerticalDown2)
                            || barPlayer.isColliding(barBombVerticalDown1))) {
                        playerList.add(player);
                        continue;
                    }
                    if (horizontalLeft && (barPlayer.isColliding(barBombHorizontalLeft2)
                            || barPlayer.isColliding(barBombHorizontalLeft1))) {
                        playerList.add(player);
                    }
                }
            }
        }

        for (Bomb bomb: bombList) {
            GameSession.getMapFire().add(new Fire(bomb.getPosition()));
            if (verticalUp) {
                GameSession.getMapFire().add(new Fire(Point.getUp1Position(bomb.getPosition())));
                if (!GameSession.getWallByPoint(Point.getUp2Position(bomb.getPosition())))
                    GameSession.getMapFire().add(new Fire(Point.getUp2Position(bomb.getPosition())));
            }
            if (verticalDown) {
                GameSession.getMapFire().add(new Fire(Point.getDown1Position(bomb.getPosition())));
                if (!GameSession.getWallByPoint(Point.getDown2Position(bomb.getPosition())))
                    GameSession.getMapFire().add(new Fire(Point.getDown2Position(bomb.getPosition())));
            }
            if (horizontalRight) {
                GameSession.getMapFire().add(new Fire(Point.getRight1Position(bomb.getPosition())));
                if (!GameSession.getWallByPoint(Point.getRight2Position(bomb.getPosition())))
                    GameSession.getMapFire().add(new Fire(Point.getRight2Position(bomb.getPosition())));
            }
            if (horizontalLeft) {
                GameSession.getMapFire().add(new Fire(Point.getLeft1Position(bomb.getPosition())));
                if (!GameSession.getWallByPoint(Point.getLeft2Position(bomb.getPosition())))
                    GameSession.getMapFire().add(new Fire(Point.getLeft2Position(bomb.getPosition())));
            }
            GameSession.getMapBombs().remove(bomb);
        }

        for (Wood wood: woodList) {
            GameSession.getMapWoods().remove(wood);
        }
        for (Player player: playerList) {
            GameSession.getMapPlayers().remove(player);
        }
        for (Fire fire: fireList) {
            GameSession.getMapFire().remove(fire);
        }


    }

    @Override
    public void tick(long elapsed) {
        checkCollisions();
        detonationBomb();
    }
}
