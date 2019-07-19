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

    private Random random;
    private List<BlockData> datas;

    public MultiplePattern() {
        this.random = new Random();
        this.datas = new ArrayList<>();
    }

    public MultiplePattern(List<BlockData> materials) {
        this.random = new Random();
        this.datas = materials;
    }

    @Override
    public BlockData apply(Block block) {
        return datas.get(random.nextInt(datas.size()));
    }

    public void add(BlockData material) {
        this.datas.add(material);
    }
}