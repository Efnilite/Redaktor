package com.efnilite.redaktor.selection;

import org.bukkit.Location;

/**
 * The dimensions of a CuboidSelection.
 *
 * @see CuboidSelection
 */
public class Dimensions {

    private int width;
    private int height;
    private int length;
    private Location maximum;
    private Location minumum;

    public Dimensions(CuboidSelection cuboid) {
        this.maximum = cuboid.getMaximumPoint();
        this.minumum = cuboid.getMinimumPoint();

        Location max = this.maximum;
        Location min = this.minumum;

        this.width = max.getBlockX() - min.getBlockX() + 1;
        this.height = max.getBlockY() - min.getBlockY() + 1;
        this.length = max.getBlockZ() - min.getBlockZ() + 1;
    }

    public int getVolume() {
        return width * height * length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public Location getMaximumPoint() {
        return maximum;
    }

    public Location getMinimumPoint() {
        return minumum;
    }
}
