package com.efnilite.redaktor.object.schematic;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class WritableBlock {

    private Material material;
    private BlockData data;
    private int offsetX;
    private int offsetY;
    private int offsetZ;

    public WritableBlock(Block block) {
        this.material = block.getType();
        this.data = block.getBlockData();
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
    }

    public WritableBlock(Material material, BlockData data, int offX, int offY, int offZ) {
        this.material = material;
        this.data = data;
        this.offsetX = offX;
        this.offsetY = offY;
        this.offsetZ = offZ;
    }

    public Material getMaterial() {
        return material;
    }

    public BlockData getData() {
        return data;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public int getOffsetZ() {
        return offsetZ;
    }
}
