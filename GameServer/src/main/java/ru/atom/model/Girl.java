package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Rectangle;

public class Girl extends MovableFormedGameObject implements BombExplosListener {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private int maxBombAmount = 1;
    private int currentBombAmount = 0;
    private int maxBombExplosRadius = 4;
    private int bombExplosRadius = 1;

    BombPlacedListener listener = null;

    public boolean addbombPlacedListener(BombPlacedListener bombPlacedListener) {
        if (this.listener == null) {
            this.listener = bombPlacedListener;
            return true;
        }
        return false;
    }

    public void processFeed(Feed.FeedType feedType) {
        if (feedType == Feed.FeedType.AMMUNITION_INCR) {
            ++maxBombAmount;
        } else if (feedType == Feed.FeedType.SPEED_BOOTS) {
            velocity += 0.02;
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

    public void removebombPlacedListener() {
        listener = null;
    }


    public Girl(GeomObject geomObject, float velocity) {
        super(geomObject, velocity);
        log.info(this.toString());
    }

    public void setBomb() {
        if(currentBombAmount == maxBombAmount) {
            return;
        }
        ++currentBombAmount;
        listener.handleBombPlaceEvent(this);

    }

    @Override
    public void tick(long elapsed) { }

    @Override
    public String toString() {
        return "{" + getForm().toString() +
                ",\"id\":" + getId() +
                ",\"velocity\":" + getVelocity() +
                ",\"maxBombs\":" + maxBombAmount +
                ",\"bombPower\":" + 1 +
                ",\"speedModifier\":" + 1.0 +
                ",\"type\":\"Pawn\"" +
                "}";
    }

    @Override
    public Rectangle getForm() {
        return (Rectangle)geomObject;
    }

    public void handleBombExplodeEvent(Bomb bomb) {
        --currentBombAmount;

    }
}
