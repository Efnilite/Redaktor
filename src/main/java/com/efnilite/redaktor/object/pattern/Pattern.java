package com.efnilite.redaktor.object.pattern;

import com.efnilite.redaktor.object.pattern.types.BlockPattern;
import com.efnilite.redaktor.object.pattern.types.MorePattern;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
     * @return  the Material the block needs to be set to.
     */
    Material apply(Block block);

    /**
     * A class for parsing patterns
     */
    class Parser {

        public Parser() {

        }

        public Pattern parse(String pattern) {
            if (pattern.contains(",")) {
                String[] elements = pattern.split(",");
                MorePattern more = new MorePattern();
                for (String element : elements) {
                    if (element.contains("#")) {
                        String parsed = pattern.replaceAll("#", "");
                        BlockTypes type = BlockTypes.getType(parsed);
                        more.addAll(type.getMaterials());
                    } else {
                        Material material = Material.matchMaterial(pattern);
                        if (material == null) {
                            return null;
                        }
                        more.add(material);
                    }
                }
                return more;
            }

            if (pattern.contains("#")) {
                String parsed = pattern.replaceAll("#", "");
                BlockTypes type = BlockTypes.getType(parsed);
                return new MorePattern(type.getMaterials());
            } else {
                Material material = Material.matchMaterial(pattern);
                if (material == null) {
                    return null;
                }
                return new BlockPattern(material);
            }
        }
    }
}