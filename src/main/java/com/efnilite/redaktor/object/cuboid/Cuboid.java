package com.efnilite.redaktor.object.cuboid;

import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class Cuboid {

    private World world;
    private Location pos1;
    private Location pos2;

    public Cuboid(Location pos1, Location pos2, World world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public void setWorld(World world) {
        this.world = world;
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

    public List<Block> getBlocks() {
        List<Block> blocks = new ArrayList<>();
        new AsyncBlockGetter(pos1, pos2, blocks::addAll);
        return blocks;
    }
}
