package ru.atom.geometry;

public class IntersectionParams {
    private float dxImpos = 0;
    private float dyImpos = 0;
    private boolean collidFlag = false;

    public IntersectionParams(float dxImpos, float dyImpos, boolean collidFlag) {
        this.dxImpos = dxImpos;
        this.dyImpos= dyImpos;
        this.collidFlag = collidFlag;
    }

    public float getDxImpos() {
        return this.dxImpos;
    }

    public float getDyImpos() {
        return dyImpos;
    }

    public boolean getCollideFlag() {
        return collidFlag;
    }
}
