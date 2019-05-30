package com.efnilite.redaktor.object.queue.internal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class BlockMap {

    private Block block;
    private Material material;
    private BlockData data;

    public BlockMap(Block block) {
        this.block = block;
        this.material = block.getType();
        this.data = block.getBlockData();
    }

    public BlockMap(Block block, Material material, BlockData data) {
        this.block = block;
        this.material = material;
        this.data = data;
    }

    public Block getBlock() {
        return block;
    }

    public Material getMaterial() {
        return material;
    }

    public BlockData getData() {
        return data;
    }
}
