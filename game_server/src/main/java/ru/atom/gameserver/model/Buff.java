package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

import java.util.List;

public class Buff extends SaneGameObject implements Tickable {

    private final BuffType buffType;

    public Buff(int id, Point position, BuffType type) {
        super(id, position);
        this.buffType = type;
    }

    public BuffType getType() {
        return buffType;
    }

    @Override
    public void tick(long elapsed) {
        List<Pawn> instersetPawns = modelsManager.getIntersectPawns(getBar());
        if (!instersetPawns.isEmpty()) {
            buffType.affect(instersetPawns.get(0));
            destroy();
        }
    }

    public enum BuffType {
        SPEED {
            @Override
            public void affect(Pawn pawn) {
                pawn.setSpeedModifier(pawn.getSpeedModifier() * 1.01f);
            }
        },
        POWER {
            @Override
            public void affect(Pawn pawn) {
                pawn.setBombPower(pawn.getBombPower() + 1);
            }
        },
        CAPACITY {
            @Override
            public void affect(Pawn pawn) {
                pawn.setMaxBombs(pawn.getMaxBombs() + 1);
            }
        }; // max numbers of bombs

        public abstract void affect(Pawn pawn);
    }
}
