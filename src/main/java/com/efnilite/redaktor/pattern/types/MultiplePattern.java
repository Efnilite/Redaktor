package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A patten consisting of multiple blocks,
 * that are evenly distributed.
 */
public class MultiplePattern implements Pattern {

    /**
     * The random instance for getting a random element
     */
    private Random random;

    /**
     * The datas
     */
    private List<BlockData> datas;

    /**
     * Creates a new instance
     */
    public MultiplePattern() {
        this.random = new Random();
        this.datas = new ArrayList<>();
    }

    /**
     * Creates a new instance
     *
     * @param   datas
     *          The datas
     */
    public MultiplePattern(List<BlockData> datas) {
        this.random = new Random();
        this.datas = datas;
    }

    @Override
    public BlockData apply(Block block) {
        return datas.get(random.nextInt(datas.size()));
    }

    /**
     * Adds a BlockData
     *
     * @param   data
     *          The data
     */
    public void add(BlockData data) {
        this.datas.add(data);
    }
}