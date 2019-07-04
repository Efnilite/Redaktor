package com.efnilite.redaktor.block;

import org.bukkit.Location;
import org.bukkit.block.data.BlockData;

/**
 * Class for setting & getting blocks using net.minecraft.server
 *
 * This is because if this was using Bukkit's types it would
 * automatically update blocks that have been placed, resulting
 * in a lot of lag for block updates.
 */
public interface IBlockFactory {

    /**
     * Set a block without updating the surroudings or the block.
     *
     * @param   location
     *          The location.
     *
     * @param   data
     *          The BlockData it's going to be set to.
     */
    void setBlock(Location location, BlockData data);

}