package ru.atom.gameserver.gsession;

import ru.atom.gameserver.model.GameObject;

public interface GarbageCollector {

    void mark(GameObject gameObject);

}
