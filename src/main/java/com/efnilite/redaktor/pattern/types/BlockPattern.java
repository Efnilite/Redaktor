package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * A pattern consisting of one block.
 */
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
