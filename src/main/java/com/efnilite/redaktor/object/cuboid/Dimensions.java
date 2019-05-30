package com.efnilite.redaktor.object.cuboid;

import com.google.gson.annotations.Expose;
import org.bukkit.Location;

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

    public Dimensions(Cuboid cuboid) {
        this.maximum = cuboid.getMaximumPoint();
        this.minumum = cuboid.getMinimumPoint();

        Location max = this.maximum;
        Location min = this.minumum;

        this.width = max.getBlockX() - min.getBlockX();
        this.height = max.getBlockY() - min.getBlockY();
        this.length = max.getBlockZ() - min.getBlockZ();
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
