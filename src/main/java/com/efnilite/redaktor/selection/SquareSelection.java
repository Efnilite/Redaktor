package com.efnilite.redaktor.selection;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Arrays;
import java.util.List;

/**
 * A collections of CuboidSelections.
 * Used for abstract shapes.
 *
 * @see CuboidSelection
 */
public class SquareSelection implements Selection {

    /**
     * The list of cuboids
     */
    private List<CuboidSelection> cuboids;

    /**
     * Creates a new instance
     *
     * @param   cuboids
     *          The CuboidSelections
     */
    public SquareSelection(CuboidSelection... cuboids) {
        this.cuboids = Arrays.asList(cuboids);
    }

    /**
     * Gets the CuboidSelections
     *
     * @return the CuboidSelections
     */
    public List<CuboidSelection> getCuboids() {
        return cuboids;
    }

    @Override
    public HistorySelection toHistory() {
        return new CuboidSelection(this.getPos1(), this.getPos2(), this.getWorld()).toHistory();
    }

    @Override
    public Location getPos1() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        for (CuboidSelection cuboid : cuboids) {
            minX = Math.min(cuboid.getPos1().getBlockX(), minX);
            minY = Math.min(cuboid.getPos1().getBlockX(), minY);
            minZ = Math.min(cuboid.getPos1().getBlockX(), minZ);
        }
        return new Location(cuboids.get(0).getWorld(), minX, minY, minZ);
    }

    @Override
    public Location getPos2() {
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        for (CuboidSelection cuboid : cuboids) {
            maxX = Math.max(cuboid.getPos1().getBlockX(), maxX);
            maxY = Math.max(cuboid.getPos1().getBlockX(), maxY);
            maxZ = Math.max(cuboid.getPos1().getBlockX(), maxZ);
        }
        return new Location(cuboids.get(0).getWorld(), maxX, maxY, maxZ);
    }

    @Override
    public World getWorld() {
        return cuboids.get(0).getWorld();
    }

    @Override
    public Dimensions getDimensions() {
        Location pos1 = this.getPos1();
        Location pos2 = this.getPos2();
        CuboidSelection selection = new CuboidSelection(pos1, pos2, this.getWorld());
        return selection.getDimensions();
    }
}