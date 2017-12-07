package  ru.atom.model;

import ru.atom.geometry.Collider;
import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Point;

public class FormedGameObject implements Positionable, Colliding {

    protected GeomObject geomObject;
    private long id;

    public FormedGameObject(GeomObject geomObject) {
        this.geomObject = geomObject;
        id = GameModel.generateGameObjectId();
    }

    public GeomObject getForm() {
        return  geomObject;
    }

    @Override
    public Point getPosition() {
        return geomObject.getPosition();
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Collider getCollider() {
        return geomObject;
    }
}
