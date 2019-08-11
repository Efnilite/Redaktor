package com.efnilite.redaktor.selection;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * A 3D cuboid selection.
 *
 * @see Dimensions
 */
public class CuboidSelection implements Selection {

    /**
     * The world
     */
    private World world;

    /**
     * The first position
     */
    private Location pos1;

    /**
     * The second position
     */
    private Location pos2;

    /**
     * The dimensions of this selection
     */
    private Dimensions dimensions;

    /**
     * Create a new CuboidSelection instance
     * <p>
     * Because this has no world, it will get the world of the locations.
     * If the world of position 1 is not set, it will get the world of the second position.
     *
     * @param   pos1
     *          The first position of the selection
     *
     * @param   pos2
     *          The second position of the selection
     */
    public CuboidSelection(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        this.dimensions = new Dimensions(this);
    }

    /**
     * The preferred CuboidSelection constructor
     *
     * @param   pos1
     *          The first position of the selection
     *
     * @param   pos2
     *          The second position of the selection
     *
     * @param   world
     *          The world the cuboid is in
     */
    public CuboidSelection(Location pos1, Location pos2, World world) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.world = world;
        this.dimensions = new Dimensions(this);
    }

    /**
     * Shifts the cuboid
     * <p>
     * Sets the minimum location and auto adjusts the max location
     * to the same as this selection instance
     *
     * @param   min
     *          The min location
     *
     * @return  the new cuboid instance
     */
    public CuboidSelection move(Location min) {
        return new CuboidSelection(min, this.getMaximumPoint().add(min));
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
     * Turns this CuboidSelection into a selection of the chunk the center block is in.
     *
     * @return the chunk of the center
     */
    public CuboidSelection toChunk() {
        return new CuboidSelection(new Location(world, this.getCenter().getChunk().getX() * 16, 0, this.getCenter().getChunk().getZ() * 16),
                new Location(world, (this.getCenter().getChunk().getX() * 16) + 16, world.getMaxHeight(), (this.getCenter().getChunk().getZ() * 16) + 16));
    }

    /**
     * Creates a new {@link HistorySelection}
     *
     * @return a new HistorySelection
     */
    public HistorySelection toHistory() {
        CuboidSelection selection = new CuboidSelection(pos1, pos2, world);
        return new HistorySelection(selection);
    }

    /**
     * Gets the center of this CuboidSelection
     *
     * @return the center location
     */
    public Location getCenter() {
        return this.getMaximumPoint().subtract(dimensions.getLength() / 2.0, dimensions.getHeight() / 2.0, dimensions.getWidth() / 2.0);
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

    @Override
    public Location getPos1() {
        return pos1;
    }

    @Override
    public Location getPos2() {
        return pos2;
    }

    @Override
    public World getWorld() {
        return world;
    }

    /**
     * Get the dimensions of this selection
     *
     * @return the dimensions
     */
    public Dimensions getDimensions() {
        return dimensions;
    }
}