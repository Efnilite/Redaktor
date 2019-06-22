package com.efnilite.redaktor.schematic;

import com.google.gson.annotations.Expose;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class WritableBlock {

    @Expose
    private Material material;
    @Expose
    private String data;
    @Expose
    private int offsetX;
    @Expose
    private int offsetY;
    @Expose
    private int offsetZ;

    public WritableBlock(Block block, int offsetX, int offsetY, int offsetZ) {
        this.material = block.getType();
        this.data = block.getBlockData().getAsString();
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public Material getMaterial() {
        return material;
    }

    public String getData() {
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
