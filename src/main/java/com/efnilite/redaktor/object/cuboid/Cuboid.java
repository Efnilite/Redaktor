package com.efnilite.redaktor.object.cuboid;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * A 3D cuboid selection.
 *
 * @see Dimensions
 */
public class Cuboid {

    private World world;
    private Location pos1;
    private Location pos2;
    private Dimensions dimensions;

    public Cuboid(Location pos1, Location pos2, World world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        this.dimensions = new Dimensions(this);
    }

    public Cuboid(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        this.dimensions = new Dimensions(this);
    }

    public Location getMaximumPoint() {
        return new Location(world, Math.max(pos1.getBlockX(), pos2.getBlockX()), Math.max(pos1.getBlockY(), pos2.getBlockY()), Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    public Location getMinimumPoint() {
        return new Location(world, Math.min(pos1.getBlockX(), pos2.getBlockX()), Math.min(pos1.getBlockY(), pos2.getBlockY()), Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public World getWorld() {
        return world;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
