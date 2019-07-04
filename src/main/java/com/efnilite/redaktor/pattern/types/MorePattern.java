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
public class MorePattern implements Pattern {

    private Random random;
    private List<BlockData> materials;

    public MorePattern() {
        this.random = new Random();
        this.materials = new ArrayList<>();
    }

    public MorePattern(List<BlockData> materials) {
        this.random = new Random();
        this.materials = materials;
    }

    @Override
    public BlockData apply(Block block) {
        int index = random.nextInt(materials.size() - 1);
        return materials.get(index);
    }

    public void add(BlockData material) {
        this.materials.add(material);
    }

    public void addAll(List<BlockData> materials) {
        this.materials.addAll(materials);
    }
}