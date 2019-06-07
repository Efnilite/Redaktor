package com.efnilite.redaktor.object.pattern;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A patten consisting of multiple blocks,
 * that are evenly distributed.
 */
public class MorePattern implements Pattern {

    private List<Material> materials;

    public MorePattern() {
        this.materials = new ArrayList<>();
    }

    @Override
    public Material apply(Block block) {
        int index = new Random().nextInt(materials.size() - 1);
        return materials.get(index);
    }

    public void add(Material material) {
        this.materials.add(material);
    }
}