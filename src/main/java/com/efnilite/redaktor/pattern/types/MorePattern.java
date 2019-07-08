package com.efnilite.redaktor.pattern.types;

import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A {@link MultiplePattern} for patterns.
 */
public class MorePattern implements Pattern {

    private Random random;
    private List<Pattern> patterns;

    public MorePattern() {
        this.random = new Random();
        this.patterns = new ArrayList<>();
    }

    public MorePattern(List<Pattern> patterns) {
        this.random = new Random();
        this.patterns = patterns;
    }

    @Override
    public BlockData apply(Block block) {
        return patterns.get(random.nextInt(patterns.size() - 1)).apply(block);
    }

    public void add(Pattern pattern) {
        this.patterns.add(pattern);
    }
}