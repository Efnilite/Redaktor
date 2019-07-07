package com.efnilite.redaktor.util;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public class Util {

    private static Random random;

    static {
        random = new Random();
    }

    public static Location zero() {
        return new Location(null, 0, 0, 0);
    }

    public static Location fromString(String string) {
        String[] elements = string.split(",");
        if (elements.length == 3) {
            return new Location(null, Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
        } else {
            return zero();
        }
    }

    public static String format(Material material) {
        return material.name().toLowerCase().replaceAll("_", "");
    }

    public static String toString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ()).replace(".0", "");
    }

    public static String randomizeBooleans(String string) {
        return string.toLowerCase().replaceAll("(true|false)", Boolean.toString(Booleans.values()[random.nextInt(2) - 1].getValue()).toLowerCase());
    }

    private enum Booleans {
        TRUE(true),
        FALSE(false);

        private boolean value;

        Booleans(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    }
}