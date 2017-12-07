package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.atom.geometry.GeomObject;

public class Girl extends MovableFormedGameObject {
    private static final Logger log = LogManager.getLogger(Girl.class);
    private int maxBombAmount = 1;
    private int currentBombAmount = 0;


    public Girl(GeomObject geomObject, float velocity) {
        super(geomObject, velocity);
        log.info(" [id = " + this.getId() + "] Created: Girl( " + geomObject.toString() +
                ", velocity = " + velocity +
                " )");
    }

    public void setBomb() {

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
}
