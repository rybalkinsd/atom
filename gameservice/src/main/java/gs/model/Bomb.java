package gs.model;

import gs.geometry.Point;
import org.slf4j.LoggerFactory;

public class Bomb extends GameObject implements Tickable {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Bomb.class);
    private static final int LIFETIME = 300;
    private static final int BOMB_WIDTH = 28;
    private static final int BOMB_HEIGHT = 28;

    private Girl owner;
    private int rangeOfExplosion = 1;
    private int elapsed = 0;

    public Bomb(GameSession session, Point position, Girl owner) {
        super(session, position, BOMB_WIDTH, BOMB_HEIGHT);
        this.owner = owner;
        logger.info("New Bomb id={}, position={}, session_ID", id, position, session.getId());
    }

    //TODO: Чет дерьмово выглядит
    private void spawnFire() {
        new Fire(session, position);
        for (int i = 1; i <= rangeOfExplosion; i++) {
            if (session.getGameObjectByPosition(position.getRightPoint(i))
                    .getClass() == Wall.class) {
                break;
            } else if (session.getGameObjectByPosition(position.getRightPoint(i))
                    .getClass() == Brick.class) {
                new Fire(session, position.getRightPoint(i));
                break;
            } else {
                new Fire(session, position.getRightPoint(i));
            }
        }

        for (int i = 1; i <= rangeOfExplosion; i++) {
            if (session.getGameObjectByPosition(position.getLeftPoint(i))
                    .getClass() == Wall.class) {
                break;
            } else if (session.getGameObjectByPosition(position.getLeftPoint(i))
                    .getClass() == Brick.class) {
                new Fire(session, position.getLeftPoint(i));
                break;
            } else {
                new Fire(session, position.getLeftPoint(i));
            }
        }

        for (int i = 1; i <= rangeOfExplosion; i++) {
            if (session.getGameObjectByPosition(position.getUpperPoint(i))
                    .getClass() == Wall.class) {
                break;
            } else if (session.getGameObjectByPosition(position.getUpperPoint(i))
                    .getClass() == Brick.class) {
                new Fire(session, position.getUpperPoint(i));
                break;
            } else {
                new Fire(session, position.getUpperPoint(i));
            }
        }

        for (int i = 1; i <= rangeOfExplosion; i++) {
            if (session.getGameObjectByPosition(position.getLowerPoint(i))
                    .getClass() == Wall.class) {
                break;
            } else if (session.getGameObjectByPosition(position.getLowerPoint(i))
                    .getClass() == Brick.class) {
                new Fire(session, position.getLowerPoint(i));
                break;
            } else {
                new Fire(session, position.getLowerPoint(i));
            }
        }
    }

    @Override
    public void tick(int elapsed) {
        this.elapsed += elapsed;
        if (this.elapsed >= LIFETIME) {
            session.removeById(id);
            owner.incBombCapacity();
        }
    }
}
