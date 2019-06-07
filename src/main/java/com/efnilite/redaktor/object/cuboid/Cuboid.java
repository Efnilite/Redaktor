package com.efnilite.redaktor.object.cuboid;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

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

    public List<Block> getWalls() {
        List<Block> blocks = new ArrayList<>();
        Location max = this.getMaximumPoint();
        Location min = this.getMinimumPoint();

        Location current = pos1.clone().zero();
        for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    if (min.getBlockX() == x || max.getBlockX() == x || min.getBlockZ() == z || max.getBlockZ() == z) {
                        current.setX(x);
                        current.setY(y);
                        current.setZ(z);

                        blocks.add(current.getBlock());
                    }
                }
            }
        }

        return blocks;
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
