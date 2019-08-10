package com.efnilite.redaktor.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class for setting and getting blocks using net.minecraft.server
 *
 * This is because if this was using Bukkit's types it would
 * automatically update blocks that have been placed, resulting
 * in a lot of lag for block updates.
 */
public abstract class BlockFactory {

    protected int change;
    protected List<Location> locations;
    protected HashMap<Chunk, List<Location>> chunks;

    /**
     * Creates a new instance
     */
    public BlockFactory() {
        /*this.change = 0;
        this.chunks = new HashMap<>();
        this.locations = new ArrayList<>();*/
    }

    /**
     * Set a block without updating the surroundings or the block.
     *
     * @param   location
     *          The location.
     *
     * @param   data
     *          The BlockData it's going to be set to.
     */
    public abstract void setBlock(Location location, BlockData data);

    public abstract void flush();

}