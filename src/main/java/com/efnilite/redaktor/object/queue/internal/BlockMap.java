package com.efnilite.redaktor.object.queue.internal;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockMap {

    private Block block;
    private Material material;

    public BlockMap(Block block) {
        this.block = block;
        this.material = block.getType();
    }

    public Block getBlock() {
        return block;
    }

    public Material getMaterial() {
        return material;
    }
}
