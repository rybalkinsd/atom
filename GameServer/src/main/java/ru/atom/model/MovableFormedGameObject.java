package ru.atom.model;

import ru.atom.geometry.GeomObject;
import ru.atom.geometry.Point;
import ru.atom.geometry.Rectangle;
import ru.atom.model.listners.MoveEventListener;


public abstract class MovableFormedGameObject extends FormedGameObject implements Movable {

    protected float velocity;
    private MoveEventListener moveEventListener = null;

    public boolean addMoveEventListener(MoveEventListener moveEventLisetner) {
        if (this.moveEventListener == null) {
            this.moveEventListener = moveEventLisetner;
            return true;
        }
        return false;
    }

    public void removeMoveEventLstener() {
        moveEventListener = null;
    }


    MovableFormedGameObject(GeomObject geomObj, float velocity) {
        super(geomObj);
        this.velocity = velocity;
    }

    public float getVelocity() {
        return this.velocity;
    }

    @Override
    public Point move(Direction direction, long time) {

        if (super.geomObject instanceof Rectangle) {
            int kx = 0;
            int ky = 0;

            switch (direction) {
                case RIGHT: { kx = 1; }
                break;
                case LEFT:  { kx = -1; }
                break;
                case UP:    { ky = -1; }
                break;
                case DOWN:  { ky = 1; }
                break;
                case IDLE:  { }
                break;
                default:    { }
            }
            Rectangle newForm = new Rectangle(
                    new Point(super.getPosition().getX() + kx * velocity * time,
                              super.getPosition().getY() + ky * velocity * time),
                    ((Rectangle)super.getForm()).getWidth(),
                    ((Rectangle)super.getForm()).getHeight());

            Rectangle oldForm = new Rectangle(
                    new Point(super.getPosition().getX(),
                            super.getPosition().getY()),
                    ((Rectangle)super.getForm()).getWidth(),
                    ((Rectangle)super.getForm()).getHeight());

            FormedGameObject newform = new FormedGameObject(newForm, this.getId());
            FormedGameObject oldform = new FormedGameObject(getForm(), this.getId());
            Point point = null;
            if (moveEventListener != null) {
                point = moveEventListener.getMoveRecom(newform, this);
            }
            if (point == null) {
                return super.getForm().getPosition();
            } else {
                super.geomObject = new Rectangle(
                        new Point(point.getX(),
                                point.getY()),
                        newForm.getWidth(),
                        newForm.getHeight());
            }

            moveEventListener.handleMoveEvent(oldform, this);
        }
        return super.getForm().getPosition();
    }

}
