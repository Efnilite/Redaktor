package com.efnilite.redaktor.block;

import com.efnilite.redaktor.object.pattern.Pattern;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
     * @param   material
     *          The material it's going to be set to.
     */
    void setBlock(Location location, Material material);

    /**
     * Set a block without updating the surroudings or the block.
     *
     * @param   block
     *          The block.
     *
     * @param   material
     *          The material it's going to be set to.
     */
    void setBlock(Block block, Material material);

    /**
     * Set a block without updating the surroudings or the block.
     *
     * @see #setBlock(Location, Material)
     *
     * @param   block
     *          The block.
     *
     * @param   pattern
     *          The pattern it's going to be set to.
     */
    void setBlock(Block block, Pattern pattern);
}
