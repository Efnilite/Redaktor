package com.efnilite.redaktor.object.cuboid;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.Iterator;

public class Cuboid {

    private World world;
    private Location pos1;
    private Location pos2;

    public Cuboid() {
        this(null, null, null);
    }

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

    @Deprecated
    public Iterator<Block> iterator() {
        return new Iterator<Block>() {
            private Location pos1 = Cuboid.this.getPos1();
            private Location pos2 = Cuboid.this.getPos2();
            private int x = pos1.getBlockX();
            private int y = pos1.getBlockY();
            private int z = pos1.getBlockZ();
            private Location current = new Location(Cuboid.this.getWorld(), x, y, z);
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public Block next() {
                if ((x + 1) <= pos2.getBlockX()) {
                    current.setX(x++);
                    return current.getBlock();
                } else if ((y + 1) <= pos2.getBlockY()) {
                    current.setY(y++);
                    return current.getBlock();
                } else if ((z + 1) <= pos2.getBlockZ()) {
                    current.setZ(x++);
                    return current.getBlock();
                } else {
                    hasNext = false;
                    return null;
                }
            }
        };
    }
}
