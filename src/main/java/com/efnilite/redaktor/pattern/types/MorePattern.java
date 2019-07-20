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

    /**
     * A random instance for getting a random element
     */
    private Random random;

    /**
     * The patterns
     */
    private List<Pattern> patterns;

    /**
     * Creates a new instance
     */
    public MorePattern() {
        this.random = new Random();
        this.patterns = new ArrayList<>();
    }

    /**
     * Creates a new instance
     *
     * @param   patterns
     *          The list of patterns
     */
    public MorePattern(List<Pattern> patterns) {
        this.random = new Random();
        this.patterns = patterns;
    }

    @Override
    public BlockData apply(Block block) {
        return patterns.get(random.nextInt(patterns.size())).apply(block);
    }

    /**
     * Add a pattern
     *
     * @param   pattern
     *          The pattern
     */
    public void add(Pattern pattern) {
        this.patterns.add(pattern);
    }
}