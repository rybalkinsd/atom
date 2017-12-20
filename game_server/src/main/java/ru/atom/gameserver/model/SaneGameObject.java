package ru.atom.gameserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.atom.gameserver.geometry.Point;
import ru.atom.gameserver.gsession.GarbageCollector;
import ru.atom.gameserver.gsession.ModelsManager;

public abstract class SaneGameObject extends AbstractGameObject {

    @JsonIgnore
    protected GarbageCollector garbageCollector;
    @JsonIgnore
    protected ModelsManager modelsManager;

    public SaneGameObject(int id, Point position) {
        super(id, position);
    }

    public void setGarbageCollector(GarbageCollector garbageCollector) {
        this.garbageCollector = garbageCollector;
    }

    public void setModelsManager(ModelsManager modelsManager) {
        this.modelsManager = modelsManager;
    }

    protected void destroy() {
        garbageCollector.mark(this);
    }
}
