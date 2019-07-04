package com.efnilite.redaktor.pattern;

import com.efnilite.redaktor.pattern.types.BlockPattern;
import com.efnilite.redaktor.pattern.types.MorePattern;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
                    if (element.contains("#")) {
                        String parsed = pattern.replaceAll("#", "");
                        BlockTypes type = BlockTypes.getType(parsed);
                        more.addAll(type.getMaterials());
                        continue;
                    } if (element.contains("&")) {
                        String parsed = pattern.replaceAll("&", "");
                        BlockData data = Bukkit.createBlockData(Util.randomizeBooleans(parsed));
                        more.add(data);
                        continue;
                    }

                    Material material = Material.matchMaterial(pattern);
                    if (material == null) {
                        return null;
                    }
                    more.add(material.createBlockData());
                }
                return more;
            }

            Pattern back = null;
            if (pattern.contains("#")) {
                String parsed = pattern.replaceAll("#", "");
                BlockTypes type = BlockTypes.getType(parsed);
                back = new MorePattern(type.getMaterials());
            } if (pattern.contains("&")) {
                String parsed = pattern.replaceAll("&", "");
                BlockData data = Bukkit.createBlockData(Util.randomizeBooleans(parsed));
                back = new BlockPattern(data);
            }

            if (back == null) {
                Material material = Material.matchMaterial(pattern);
                if (material == null) {
                    return null;
                }
                back = new BlockPattern(material.createBlockData());
            }
            return back;
        }
    }
}