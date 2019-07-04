package com.efnilite.redaktor.object.pattern.types;

import com.efnilite.redaktor.object.pattern.Pattern;
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

    private Random random;
    private List<Material> materials;

    public MorePattern() {
        this.random = new Random();
        this.materials = new ArrayList<>();
    }

    public MorePattern(List<Material> materials) {
        this.random = new Random();
        this.materials = materials;
    }

    @Override
    public Material apply(Block block) {
        int index = random.nextInt(materials.size() - 1);
        return materials.get(index);
    }

    public void add(Material material) {
        this.materials.add(material);
    }

    public void addAll(List<Material> materials) {
        this.materials.addAll(materials);
    }
}