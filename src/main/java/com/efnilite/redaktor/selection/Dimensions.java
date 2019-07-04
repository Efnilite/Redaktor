package com.efnilite.redaktor.selection;

import com.google.gson.annotations.Expose;
import org.bukkit.Location;

/**
 * The dimensions of a CuboidSelection.
 *
 * @see CuboidSelection
 */
public class Dimensions {

    @Expose
    private int width;
    @Expose
    private int height;
    @Expose
    private int length;
    @Expose
    private Location maximum;
    @Expose
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
