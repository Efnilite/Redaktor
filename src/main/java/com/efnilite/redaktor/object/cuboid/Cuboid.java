package com.efnilite.redaktor.object.cuboid;

import com.efnilite.redaktor.util.bukkit.Locations;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
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
    private Location maximum;
    private Location minimum;
    private Dimensions dimensions;

    public Cuboid(Location pos1, Location pos2, World world) {
        this.minimum = Locations.getMinimum(pos1, pos2);
        this.maximum = Locations.getMaximum(pos1, pos2);
        this.world = world;
        this.dimensions = new Dimensions(this);
    }

    public Cuboid(Location pos1, Location pos2) {
        this.minimum = Locations.getMinimum(pos1, pos2);
        this.maximum = Locations.getMaximum(pos1, pos2);
        this.world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        this.dimensions = new Dimensions(this);
    }


    public void setMinimum(Location minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(Location maximum) {
        this.maximum = maximum;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Location getMaximumPoint() {
        return maximum;
    }

    public Location getMinimumPoint() {
        return minimum;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public World getWorld() {
        return world;
    }

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        new AsyncBlockGetter(maximum, minimum, blocks::addAll);
        return blocks;
    }
}
