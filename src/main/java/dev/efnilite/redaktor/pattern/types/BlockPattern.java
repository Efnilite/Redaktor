package dev.efnilite.redaktor.pattern.types;

import dev.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * A pattern consisting of one block.
 */
public class BlockPattern implements Pattern {

    /**
     * The data
     */
    private BlockData data;

    /**
     * Creates a new instance
     *
     * @param   data
     *          The data
     */
    public BlockPattern(BlockData data) {
        this.data = data;
    }

    @Override
    public BlockData apply(Block block) {
        return data;
    }
}
