package com.efnilite.redaktor.queue;

import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * A class for saving blocks and and blockdata
 */
public class BlockMap {

    /**
     * The block
     */
    private Block block;

    /**
     * The BlockData
     */
    private BlockData data;

    /**
     * Creates a new instance
     *
     * @param   block
     *          The block
     */
    public BlockMap(Block block) {
        this.block = block;
        this.data = block.getBlockData();
    }

    /**
     * Creates a new instance
     *
     * @param   block
     *          The block
     *
     * @param   data
     *          The BlockData
     */
    public BlockMap(Block block, BlockData data) {
        this.block = block;
        this.data = data;
    }

    /**
     * Gets the BlockData
     *
     * @return the data
     */
    public BlockData getData() {
        return data;
    }

    /**
     * Gets the block
     *
     * @return the block
     */
    public Block getBlock() {
        return block;
    }
}
