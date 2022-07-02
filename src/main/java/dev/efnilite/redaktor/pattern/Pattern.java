package dev.efnilite.redaktor.pattern;

import dev.efnilite.redaktor.pattern.types.*;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

/**
 * An interface for Patterns.
 *
 * Patterns are used for determining how blocks
 * should be set.
 */
public interface Pattern {

    /**
     * A method for getting the right Material for a specific Block.
     *
     * @param   block
     *          The block where the pattern needs
     *          to be calculated.
     *
     * @return  the BlockData the block needs to be set to.
     */
    BlockData apply(Block block);

    /**
     * A method for parsing data without causing errors, returning null if a pattern
     * is invalid.
     *
     * @param   data
     *          The string of data to be parsed
     *
     * @return  a BlockData instance or null
     */
    static BlockData parseData(String data) {
        try {
            return Bukkit.createBlockData(data);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * A class for parsing patterns
     */
    class Parser {

        /**
         * Creates a new instance
         */
        public Parser() {

        }

        /**
         * Creates a new {@link Pattern} instance from a String
         *
         * @param   pattern
         *          The string of the pattern.
         *
         * @return  a new {@link Pattern} instance.
         */
        public Pattern parse(String pattern) {
            if (pattern.contains(",")) {
                String[] elements = pattern.split(",");
                MorePattern more = new MorePattern();
                ChancePattern chance = new ChancePattern();
                for (String element : elements) {
                    if (element.contains("&")) {
                        String parsed = element.replaceAll("&", "");
                        BlockData data = Pattern.parseData(parsed);

                        if (data == null) {
                            return null;
                        }

                        more.add(new RandomPattern(data));
                        continue;
                    } else if (element.contains("#")) {
                        String parsed = element.replaceAll("#", "");
                        BlockTypes type = BlockTypes.getType(parsed);

                        if (type == null) {
                            return null;
                        }

                        more.add(new MultiplePattern(type.getMaterials()));
                        continue;
                    } else if (element.contains("%")) {
                        String[] split = element.split("%");
                        int parsedChance = Integer.parseInt(split[0]);
                        String parsed = split[1];

                        BlockData data = Pattern.parseData(parsed);

                        if (data == null) {
                            return null;
                        }

                        chance.add(data, parsedChance);
                    }

                    more.add(new BlockPattern(Pattern.parseData(element)));
                }
                if (chance.getSum() != 0) {
                    more.add(chance);
                }
                return more;
            }

            // TODO - Single chances
            Pattern back = null;
            if (pattern.contains("&")) {
                String parsed = pattern.replaceAll("&", "");
                BlockData data = Pattern.parseData(parsed);

                if (data == null) {
                    return null;
                }

                back = new RandomPattern(data);
            } else if (pattern.contains("#")) {
                String parsed = pattern.replaceAll("#", "");
                BlockTypes type = BlockTypes.getType(parsed);

                if (type == null) {
                    return null;
                }

                back = new MultiplePattern(type.getMaterials());
            }

            if (back == null) {
                BlockData parsed = Pattern.parseData(pattern);

                if (parsed == null) {
                    return null;
                }

                back = new BlockPattern(parsed);
            }
            return back;
        }
    }
}