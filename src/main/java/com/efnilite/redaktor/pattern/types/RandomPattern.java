package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.util.Util;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * A pattern for random application in BlockData
 */
public class RandomPattern implements Pattern {

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
    public RandomPattern(BlockData data) {
        this.data = data;
    }

    @Override
    public BlockData apply(Block block) {
        return Pattern.parseData(Util.randomizeData(data.getAsString()));
    }
}