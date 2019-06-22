package com.efnilite.redaktor.object.queue.internal;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockMap {

    private Block block;
    private String data;
    private Material material;

    public BlockMap(Block block) {
        this.block = block;
        this.material = block.getType();
    }

    public BlockMap(Block block, Material material) {
        this.block = block;
        this.material = material;
    }

    public BlockMap(Block block, String data) {
        this.block = block;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Block getBlock() {
        return block;
    }

    public Material getMaterial() {
        return material;
    }
}
