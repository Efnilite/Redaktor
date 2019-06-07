package com.efnilite.redaktor.object.pattern;

import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Random;

/**
 * A patten consisting of multiple blocks,
 * with per-block chances of being placed.
 */
public class ChancePattern implements Pattern {

    private int sum;
    private HashMap<Material, Integer> chances;

    public ChancePattern() {
        this.chances = new HashMap<>();
    }

    @Override
    public Material apply(Block block) {
        HashMap<Material, Integer> chances = new HashMap<>();
        for (Material material : this.chances.keySet()) {
            chances.put(material, (int) ((double) this.chances.get(material) / (double) sum));
        }
        int random = new Random().nextInt(sum);
        int current = 1;
        for (Material material : chances.keySet()) {
            int c = chances.get(material);
            for (int i = 0; i < c; i++) {
                if (current == random) {
                    return material;
                }
                current++;
            }
        }
        return Material.AIR;
    }

    public void add(Material material, int chance) {
        sum += chance;
        chances.put(material, chance);
    }
}