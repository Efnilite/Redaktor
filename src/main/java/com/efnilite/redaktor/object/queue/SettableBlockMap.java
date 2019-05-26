package com.efnilite.redaktor.object.queue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class SettableBlockMap {

    private Block block;
    private Material material;
    private BlockData data;

    public SettableBlockMap(Block block) {
        this.block = block;
        this.material = block.getType();
        this.data = block.getBlockData();
    }

    public SettableBlockMap(Block block, Material material, BlockData data) {
        this.block = block;
        this.material = material;
        this.data = data;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setData(BlockData data) {
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
