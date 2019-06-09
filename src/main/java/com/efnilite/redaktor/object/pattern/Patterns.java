package com.efnilite.redaktor.object.pattern;

import org.bukkit.Material;

/**
 * An util class for Patterns
 *
 * @see Pattern
 */
public class Patterns {

    /**
     * A way to parse a Pattern from a string.
     *
     * @param   string
     *          The string of the Pattern.
     *
     * @return  the Pattern instance, made with the data available in string (the param).
     */
    public static Pattern parsePattern(String string) {
        if (!string.contains(",")) {
            Material material = Material.getMaterial(string.toUpperCase());
            return new BlockPattern(material);
        } else if (!string.contains("%")) {
            String[] mats = string.split(",");
            MorePattern pattern = new MorePattern();
            for (String mat : mats) {
                Material material = Material.getMaterial(mat.toUpperCase());
                pattern.add(material);
            }
            return pattern;
        } else {
            String[] mats = string.split(",");
            ChancePattern pattern = new ChancePattern();
            for (String mat : mats) {
                Material material = Material.getMaterial(mat.split("%")[0]);
                int chance = Integer.parseInt(mat.split("%")[1]);
                pattern.add(material, chance);
            }
            return pattern;
        }
    }

}
