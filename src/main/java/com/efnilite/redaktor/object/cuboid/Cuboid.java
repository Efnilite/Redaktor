package com.efnilite.redaktor.object.cuboid;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.bukkit.Location;
import org.bukkit.Warning;
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

    public Iterator<Block> iterator() {
        return new Iterator<Block>() {
            private Location pos1 = Cuboid.this.getPos1();
            private Location pos2 = Cuboid.this.getPos2();
            private int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
            private int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
            private int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
            private int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
            private int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
            private int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
            private int x = minX;
            private int y = minY;
            private int z = minZ;
            private Location current = new Location(Cuboid.this.getWorld(), x, y, z);
            private boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return this.hasNext;
            }

            @Override
            public Block next() {
                if ((x + 1) <= maxX) {
                    current.setX(x++);
                    return current.getBlock();
                } else if ((y + 1) <= maxY) {
                    current.setY(y++);
                    return current.getBlock();
                } else if ((z + 1) <= maxZ) {
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
