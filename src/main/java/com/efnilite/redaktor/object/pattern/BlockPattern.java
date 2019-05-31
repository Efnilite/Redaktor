package com.efnilite.redaktor.object.pattern;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockPattern implements Pattern {

    private Material material;

    public BlockPattern(Material material) {
        this.material = material;
    }

    @Override
    public Material apply(Block block) {
        return material;
    }
}
