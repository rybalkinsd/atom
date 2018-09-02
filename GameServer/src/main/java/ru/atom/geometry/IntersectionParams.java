package ru.atom.geometry;

public class IntersectionParams {
    private final float dxImpos;
    private final float dyImpos;

    public IntersectionParams(float dxImpos, float dyImpos) {
        this.dxImpos = dxImpos;
        this.dyImpos = dyImpos;
    }

    public float getDxImpos() {
        return this.dxImpos;
    }

    public float getDyImpos() {
        return dyImpos;
    }

}
