package com.efnilite.redaktor.schematic;

import com.google.gson.annotations.Expose;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

public class WritableBlock {

    @Expose
    private Material material;
    @Expose
    private BlockData data;
    @Expose
    private int offsetX;
    @Expose
    private int offsetY;
    @Expose
    private int offsetZ;

    public WritableBlock(Block block) {
        this.material = block.getType();
        this.data = block.getBlockData();
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
    }

    public WritableBlock(Block block, int offsetX, int offsetY, int offsetZ) {
        this.material = block.getType();
        this.data = block.getBlockData();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public WritableBlock(Material material, BlockData data, int offX, int offY, int offZ) {
        this.material = material;
        this.data = data;
        this.offsetX = offX;
        this.offsetY = offY;
        this.offsetZ = offZ;
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
