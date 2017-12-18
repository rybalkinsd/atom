package bomber.games.gamesession;

import bomber.games.gameobject.Bonus;
import bomber.games.gameobject.Box;
import bomber.games.gameobject.Explosion;
import bomber.games.gameobject.Player;
import bomber.games.gameobject.Bomb;
import bomber.games.geometry.Bar;
import bomber.games.geometry.Point;
import bomber.games.model.GameObject;
import bomber.games.model.Tickable;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MechanicsSubroutines {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MechanicsSubroutines.class);
    private final AtomicInteger idGenerator;

    public MechanicsSubroutines(AtomicInteger idGenerator) {
        this.idGenerator = idGenerator;
    }

    public boolean collisionCheck(GameObject currentPlayer, Map<Integer, GameObject> replica) {
        final int brickSize = 31;
        final int playerSize = 25;
        int playerX = currentPlayer.getPosition().getX();
        int playerY = currentPlayer.getPosition().getY();

        Bar playerBar = new Bar(playerX, playerX + playerSize, playerY, playerY + playerSize);
        int playerId = currentPlayer.getId();


        //Повторюсь, надо проверить новые координаты игроков на коллизии с другими объектами
        for (GameObject gameObject : replica.values()) {
            int brickX = gameObject.getPosition().getX();
            int brickY = gameObject.getPosition().getY();
            Bar brickBar = new Bar(brickX, brickX + brickSize, brickY, brickY + brickSize);
            if ((!(gameObject instanceof Bonus)) && (!(gameObject instanceof Bomb))
                    && (!(gameObject instanceof Player) && (!(gameObject instanceof Explosion)))) {
                if ((brickBar.isColliding(playerBar)) && !(gameObject.getId() == playerId)) {
                    log.info("isColliding");
                    return false;
                }
            }

            if (gameObject instanceof Bomb) {
                if (!brickBar.isColliding(playerBar)) {
                    ((Bomb) gameObject).setNewBombStillCollide(false);
                }
                if ((!((Bomb) gameObject).isNewBombStillCollide()) && (brickBar.isColliding(playerBar))) {
                    return false;
                }
            }
        }


        return true;
    }

    public Integer bonusCheck(Player currentPlayer, Map<Integer, GameObject> replica) {


        int playerId = currentPlayer.getId();
        Point playerPosition = currentPlayer.getPosition();

        final int bonusSize = 31;
        final int playerSize = 27;
        int playerX = currentPlayer.getPosition().getX();
        int playerY = currentPlayer.getPosition().getY();

        Bar playerBar = new Bar(playerX, playerX + playerSize, playerY, playerY + playerSize);


        for (GameObject gameObject : replica.values()) {
            int brickX = gameObject.getPosition().getX();
            int brickY = gameObject.getPosition().getY();
            Bar brickBar = new Bar(brickX, brickX + bonusSize, brickY, brickY + bonusSize);

            if (gameObject instanceof Bonus) {
                if (brickBar.isColliding(playerBar)) {
                    return gameObject.getId();
                }
            }
        }
        return null;
    }

    public Boolean createExplosions(Point currentPoint, Map<Integer, GameObject> replica) {

        final int brickSize = 31;
        final int fireSize = 27;

        int fireX = currentPoint.getX();
        int fireY = currentPoint.getY();

        Bar fireBar = new Bar(fireX, fireX + fireSize, fireY, fireY + fireSize);

        for (GameObject gameObject : replica.values()) {
            int brickX = gameObject.getPosition().getX();
            int brickY = gameObject.getPosition().getY();
            Bar brickBar = new Bar(brickX, brickX + brickSize, brickY, brickY + brickSize);
            if (!(gameObject instanceof Bonus) && !(gameObject instanceof Player)) {
                if (brickBar.isColliding(currentPoint)) { //если на пути взрыва встал НЛО
                    if (gameObject instanceof Box) { //и это НЛО - коробка
                        //idGenerator.getAndIncrement();
                        //replica.put(idGenerator.get(), new Explosion(idGenerator.get(), gameObject.getPosition()));
                        //просто отрисуем взрыв красоты ради
                        //replica.remove(idGenerator.get());//и сразу удаляем, его даже тикать не надо
                        replica.remove(gameObject.getId()); //удаляем взорвавшуюся коробку
                    }
                    return false;//все, один объект взорвался, дальше не надо работать по этому кейсу
                }
            }

        }

        return true;
    }


    public void youDied(Map<Integer, GameObject> replica, Explosion explosion, Set<Tickable> tickables) {

        final int brickSize = 31;
        final int fireSize = 27;
        int fireX = explosion.getPosition().getX();
        int fireY = explosion.getPosition().getY();

        Bar fireBar = new Bar(fireX, fireX + fireSize, fireY, fireY + fireSize);

        for (GameObject gameObject : replica.values()) {
            if (gameObject instanceof Player) {
                int playerX = gameObject.getPosition().getX();
                int playerY = gameObject.getPosition().getY();
                Bar playerBar = new Bar(playerX, playerX + brickSize, playerY, playerY + brickSize);

                if (playerBar.isColliding(fireBar)) {
                    log.info("Игрок" + Integer.toString(gameObject.getId()) + "подорвался");
                    replica.remove(gameObject.getId());//удаляем взорвавшегося неудачника
                    tickables.remove(gameObject);
                }
            }

        }
    }
}