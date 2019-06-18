package com.efnilite.redaktor.schematic.internal;

public class BlockIndex {

    private int x;
    private int y;
    private int z;

    public BlockIndex(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}