package ru.atom.model.listners;

import ru.atom.model.Bomb;

public interface BombExplosListener {
    void handleBombExplodeEvent(Bomb bomb);
}
