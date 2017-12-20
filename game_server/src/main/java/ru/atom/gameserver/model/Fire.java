package ru.atom.gameserver.model;

import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.tick.Tickable;

import java.util.List;

public class Fire extends SaneGameObject implements Tickable {

    private long lifetime;

    public Fire(int id, Point position, long lifetime) {
        super(id, position);
        this.lifetime = lifetime;
    }

    public long getLifetime() {
        return lifetime;
    }

    @Override
    public void tick(long elapsed) {
        lifetime -= elapsed;
        if (lifetime <= 0) {
            destroy();
        }
        //проверяем пересечения с персонажами
        List<Pawn> vergeOfDeath = modelsManager.getIntersectPawns(getBar());
        for (Pawn pawn : vergeOfDeath) {
            pawn.destroy();
        }
    }
}
