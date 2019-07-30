package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.HashMap;
import java.util.Random;

public class ChancePattern implements Pattern {

    private int sum;
    private Random random;
    private HashMap<Integer, BlockData> chances;

    public ChancePattern() {
        this.sum = 0;
        this.random = new Random();
        this.chances = new HashMap<>();
    }

    @Override
    public BlockData apply(Block block) {
        return chances.get(random.nextInt(sum));
    }

    /**
     * Adds a chanced BlockData
     *
     * @param   data
     *          The data
     *
     * @param   chance
     *          The chance
     */
    public void add(BlockData data, int chance) {
        for (int x = 0; x < chance; x++) {
            chances.put(sum, data);
            sum++;
        }
    }

    /**
     * Gets the sum
     *
     * @return the sum
     */
    public int getSum() {
        return sum;
    }
}