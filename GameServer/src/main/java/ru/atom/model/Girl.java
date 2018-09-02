package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;
import ru.atom.model.listners.BombExplosListener;
import ru.atom.model.listners.BombPlacedListener;

import java.util.Vector;

public class Girl extends MovableFormedGameObject implements BombExplosListener {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private int maxBombAmount = GameServerParams.getInstance().getStartBombAmount();
    private int currentBombAmount = 0;
    private int maxBombExplosRadius = GameServerParams.getInstance().getMaxExplosRadius();
    private int bombExplosRadius = GameServerParams.getInstance().getStartExplosRadius();

    public Girl(GeomObject geomObject, float velocity) {
        super(geomObject, velocity);
        log.info(this.toString());
    }

    Vector<BombPlacedListener> listeners = new Vector<>();

    public void addBombPlacedListener(BombPlacedListener bombPlacedListener) {
        this.listeners.add(bombPlacedListener);

    }

    public void processFeed(Feed.FeedType feedType) {
        if (feedType == Feed.FeedType.AMMUNITION_INCR) {
            ++maxBombAmount;
        } else if (feedType == Feed.FeedType.SPEED_BOOTS) {
            velocity += GameServerParams.getInstance().getSpeedModifier();
        } else if (feedType == Feed.FeedType.EXPLOS_BOOST) {
            if (bombExplosRadius == maxBombExplosRadius) {
                return;
            }
            ++bombExplosRadius;

        }
    }

    public int getBombExplosRadius() {
        return bombExplosRadius;
    }

    public void setBomb() {
        if (currentBombAmount == maxBombAmount) {
            return;
        }
        ++currentBombAmount;
        listeners.forEach(bombPlacedListener -> {
            bombPlacedListener.handleBombPlaceEvent(this);
        });
    }


    @Override
    public Rectangle getForm() {
        return (Rectangle)geomObject;
    }

    @Override
    public void tick(long elapsed) { }

    public void handleBombExplodeEvent(Bomb bomb) {
        --currentBombAmount;
    }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"velocity\":" + getVelocity() +
                ",\"maxBombs\":" + maxBombAmount +
                ",\"bombPower\":" + bombExplosRadius +
                ",\"speedModifier\":" + 1.0 +
                ",\"type\":\"Pawn\"" +
                "}";
    }

}
