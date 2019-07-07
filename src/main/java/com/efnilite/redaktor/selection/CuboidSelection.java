package com.efnilite.redaktor.selection;

import com.efnilite.redaktor.selection.internal.HistorySelection;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * A 3D cuboid selection.
 *
 * @see Dimensions
 */
public class CuboidSelection implements Selection {

    private World world;
    private Location pos1;
    private Location pos2;
    private Dimensions dimensions;

    public CuboidSelection(Location pos1, Location pos2, World world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        this.dimensions = new Dimensions(this);
    }

    public CuboidSelection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        this.dimensions = new Dimensions(this);
    }

    /**
     * Gets the walls
     *
     * @return the collection of cuboids containing the walls.
     */
    public SquareSelection getWalls() {
        Location max = this.getMaximumPoint();
        Location min = this.getMinimumPoint();

        return new SquareSelection(

                // y to z
                new CuboidSelection(new Location(world, max.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()), new Location(world, min.getBlockX(), pos2.getBlockY(), pos2.getBlockZ())),
                new CuboidSelection(new Location(world, min.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()), new Location(world, max.getBlockX(), pos2.getBlockY(), pos2.getBlockZ())),

                // x to y
                new CuboidSelection(new Location(world, pos1.getBlockX(), pos1.getBlockY(), max.getBlockZ()), new Location(world, pos2.getBlockX(), pos2.getBlockY(), min.getBlockZ())),
                new CuboidSelection(new Location(world, pos1.getBlockX(), pos1.getBlockY(), min.getBlockZ()), new Location(world, pos2.getBlockX(), pos2.getBlockY(), max.getBlockZ()))
        );
    }

    /**
     * Gets all edges (including the top and bottom, excluded in {@link #getWalls()}
     *
     * @return the collection of cuboids containing the edges
     */
    public SquareSelection getEdges() {
        Location max = this.getMaximumPoint();
        Location min = this.getMinimumPoint();

        return new SquareSelection(

                // y to z
                new CuboidSelection(new Location(world, max.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()), new Location(world, min.getBlockX(), pos2.getBlockY(), pos2.getBlockZ())),
                new CuboidSelection(new Location(world, min.getBlockX(), pos1.getBlockY(), pos1.getBlockZ()), new Location(world, max.getBlockX(), pos2.getBlockY(), pos2.getBlockZ())),

                // x to z
                new CuboidSelection(new Location(world, pos1.getBlockX(), max.getBlockY(), pos1.getBlockZ()), new Location(world, pos2.getBlockX(), min.getBlockY(), pos2.getBlockZ())),
                new CuboidSelection(new Location(world, pos1.getBlockX(), min.getBlockY(), pos1.getBlockZ()), new Location(world, pos2.getBlockX(), max.getBlockY(), pos2.getBlockZ())),

                // x to y
                new CuboidSelection(new Location(world, pos1.getBlockX(), pos1.getBlockY(), max.getBlockZ()), new Location(world, pos2.getBlockX(), pos2.getBlockY(), min.getBlockZ())),
                new CuboidSelection(new Location(world, pos1.getBlockX(), pos1.getBlockY(), min.getBlockZ()), new Location(world, pos2.getBlockX(), pos2.getBlockY(), max.getBlockZ()))
        );
    }

    /**
     * Creates a new {@link HistorySelection}
     *
     * @return a new HistorySelection
     */
    public HistorySelection toHistory() {
        return new HistorySelection(pos1, pos2, world);
    }

    /**
     * Calculate the maximal point
     *
     * @return the maximal point
     */
    public Location getMaximumPoint() {
        return new Location(world, Math.max(pos1.getBlockX(), pos2.getBlockX()), Math.max(pos1.getBlockY(), pos2.getBlockY()), Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    /**
     * Calculate the minimal point
     *
     * @return the minimal point
     */
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
