package ru.atom.model.object.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jetbrains.annotations.NotNull;
import ru.atom.model.collision.CollisionProfile;
import ru.atom.model.input.InputAction;
import ru.atom.util.V;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by sergei-r on 11.01.17.
 */
public class Pawn extends Actor {
    @JsonIgnore
    private boolean wantPlantBomb = false;
    @JsonIgnore
    private Collection<InputAction> inbox = new ArrayList<>();

    //skills
    private int maxBombs = 1;
    private int bombPower = 1;
    private double speedModifier = 1;

    public static Pawn create(V position) {
        Pawn instance = new Pawn(position);
        instance.postConstruct();
        return instance;
    }

    private Pawn(V position) {
        setPosition(position);
        setVelocity(V.ZERO);
    }

    @Override
    public void tick(long time) {
        inbox.forEach(x -> x.act(this));
        super.tick(time);
        plantBomb();
        inbox.clear();
    }

    private void plantBomb() {
        if (!wantPlantBomb) {
            return;
        }

        // action
        wantPlantBomb = false;
    }

    public void addInput(InputAction inputAction) {
        inbox.add(inputAction);
    }

    public void setWantPlantBomb(boolean wantPlantBomb) {
        this.wantPlantBomb = wantPlantBomb;
    }

    @NotNull
    @Override
    public CollisionProfile getProfile() {
        return CollisionProfile.DYNAMIC;
    }

    public void addSpeed(double value) {
        assert value > 0;
        speedModifier += value;
    }

    public void addBombPower(int value) {
        assert value > 0;
        bombPower += value;
    }

    public void addMaxBombs(int value) {
        assert value > 0;
        maxBombs += value;
    }
}

