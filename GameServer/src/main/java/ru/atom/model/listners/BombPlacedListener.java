package ru.atom.model.listners;

import ru.atom.model.Girl;

public interface BombPlacedListener {
    void handleBombPlaceEvent(Girl girl);
}
