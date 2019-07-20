package com.efnilite.redaktor.schematic;

/**
 * A class for saving where from a certain point blocks are saved
 */
public class BlockIndex {

    /**
     * The x value
     */
    private int x;

    /**
     * The y value
     */
    private int y;

    /**
     * The z value
     */
    private int z;

    /**
     * Creates a new instance
     *
     * @param   x
     *          The x value
     *
     * @param   y
     *          The y value
     *
     * @param   z
     *          The z value
     */
    public BlockIndex(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Gets the x value
     *
     * @return the x value
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y value
     *
     * @return the y value
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the z value
     *
     * @return the z value
     */
    public int getZ() {
        return z;
    }
}