package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * A pattern consisting of one block.
 */
public class BlockPattern implements Pattern {

    private BlockData material;

    public BlockPattern(BlockData material) {
        this.material = material;
    }

    @Override
    public BlockData apply(Block block) {
        return material;
    }
}
