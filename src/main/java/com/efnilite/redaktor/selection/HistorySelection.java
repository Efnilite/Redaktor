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
     * The selection
     */
    private Selection selection;

    /**
     * The map
     */
    private List<BlockMap> map;

    /**
     * Creates a new instance
     *
     * @param   selection
     *          The first position
     */
    public HistorySelection(Selection selection) {
        this.world = selection.getWorld();
        this.selection = selection;
        this.map = new ArrayList<>();

        new AsyncBlockGetter(selection, t -> {
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
        return new CuboidSelection(selection.getPos1(), selection.getPos2(), world);
    }

    /**
     * Gets the BlockMaps
     *
     * @return the list of BlockMaps
     */
    public List<BlockMap> getBlockMap() {
        return map;
    }

    @Override
    public HistorySelection toHistory() {
        return this;
    }

    @Override
    public Location getPos1() {
        return selection.getPos1();
    }

    @Override
    public Location getPos2() {
        return selection.getPos2();
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Dimensions getDimensions() {
        return this.toCuboid().getDimensions();
    }
}