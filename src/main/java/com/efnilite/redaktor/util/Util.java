package com.efnilite.redaktor.util;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

public class Util {

    private static Random random;
    private static String[] halfs;
    private static String[] facings;

    static {
        random = new Random();
        halfs = new String[] { "bottom", "top" };
        facings = new String[] { "north", "east", "south", "west" };
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
        return material.name().toLowerCase().replaceAll("_", " ");
    }

    public static String toString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ()).replace(".0", "");
    }

    public static String randomizeData(String string) {
        return randomizeHalfs(randomizeFacings(randomizeBooleans(string)));
    }

    public static String randomizeHalfs(String string) {
        return string.toLowerCase().replaceAll("(bottom|top)", facings[random.nextInt(facings.length - 1)]);
    }

    public static String randomizeFacings(String string) {
        return string.toLowerCase().replaceAll("(north|east|south|west)", facings[random.nextInt(facings.length - 1)]);
    }

    public static String randomizeBooleans(String string) {
        return string.toLowerCase().replaceAll("(true|false)", Boolean.toString(Booleans.values()[random.nextInt(1)].getValue()).toLowerCase());
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