package com.efnilite.redaktor.pattern;

import com.efnilite.redaktor.pattern.types.BlockPattern;
import com.efnilite.redaktor.pattern.types.MorePattern;
import com.efnilite.redaktor.pattern.types.MultiplePattern;
import com.efnilite.redaktor.pattern.types.RandomPattern;
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

        public Parser() {

        }

        /**
         * Parse a {@link Pattern} from a string.
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
                for (String element : elements) {
                    if (element.contains("&")) {
                        String parsed = pattern.replaceAll("&", "");
                        BlockData data = Pattern.parseData(parsed);

                        if (data == null) {
                            return null;
                        }

                        more.add(new RandomPattern(data));
                        continue;
                    } else if (element.contains("#")) {
                        String parsed = pattern.replaceAll("#", "");
                        BlockTypes type = BlockTypes.getType(parsed);

                        if (type == null) {
                            return null;
                        }

                        more.add(new MultiplePattern(type.getMaterials()));
                        continue;
                    }

                    more.add(new BlockPattern(Bukkit.createBlockData(element)));
                }
                return more;
            }

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