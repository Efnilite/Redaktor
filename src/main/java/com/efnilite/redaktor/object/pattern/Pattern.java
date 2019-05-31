package com.efnilite.redaktor.object.pattern;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * An interface for Patterns.
 *
 * Patterns are used for determining how blocks
 * should be set.
 */
public interface Pattern {

    /**
     * A method for getting the right Material for a specific Block.
     *
     * @param   block
     *          The block where the pattern needs
     *          to be calculated.
     *
     * @return  the Material the block needs to be set to.
     */
    Material apply(Block block);

}
