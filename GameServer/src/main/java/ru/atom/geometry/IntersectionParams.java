package ru.atom.geometry;

public class IntersectionParams {
    private float dxImpos = 0;
    private float dyImpos = 0;

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
