package com.efnilite.redaktor.selection;

import com.efnilite.redaktor.queue.BlockMap;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * A selection that stores which types of blocks it contains.
 *
 * @see com.efnilite.redaktor.Editor
 */
public class HistorySelection implements Selection {

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
     * The map
     */
    private List<BlockMap> map;

    /**
     * Creates a new instance
     *
     * @param   pos1
     *          The first position
     *
     * @param   pos2
     *          The second position
     *
     * @param   world
     *          The world
     */
    public HistorySelection(Location pos1, Location pos2, World world) {
        this.world = world;
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.map = new ArrayList<>();

        new AsyncBlockGetter(pos1, pos2, t -> {
            for (Block block : t) {
                map.add(new BlockMap(block, block.getBlockData()));
            }
        });
    }

    /**
     * Turns this instance into a {@link CuboidSelection}
     *
     * @return a new CuboidSelection instance
     */
    public CuboidSelection toCuboid() {
        return new CuboidSelection(pos1, pos2, world);
    }

    /**
     * Gets the BlockMaps
     *
     * @return the list of BlockMaps
     */
    public List<BlockMap> getBlockMap() {
        return map;
    }

    /**
     * Gets the first position
     *
     * @return the first position
     */
    public Location getPos1() {
        return pos1;
    }

    /**
     * Gets the second position
     *
     * @return the second position
     */
    public Location getPos2() {
        return pos2;
    }

    /**
     * Gets the world
     *
     * @return the world
     */
    public World getWorld() {
        return world;
    }
}