package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Random;

/**
 * Main utils
 * Will be documented soon
 */
public class Util {

    private static Random random;
    private static String[] halfs;
    private static String[] facings;

    static {
        random = new Random();
        halfs = new String[] { "bottom", "top" };
        facings = new String[] { "north", "east", "south", "west" };
    }

    public static boolean isInArea(Location pos, Location max, Location location2) {
        boolean x = pos.getBlockX() > Math.min(max.getBlockX(), location2.getBlockX()) && pos.getBlockX() < Math.max(max.getBlockX(), location2.getBlockX());
        boolean y = pos.getBlockY() > Math.min(max.getBlockY(), location2.getBlockY()) && pos.getBlockY() < Math.max(max.getBlockY(), location2.getBlockY());
        boolean z = pos.getBlockZ() > Math.min(max.getBlockZ(), location2.getBlockZ()) && pos.getBlockZ() < Math.max(max.getBlockZ(), location2.getBlockZ());
        return x && y && z;
    }

    public static String toDeserializableString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getWorld().getName()).replace(".0", "");
    }

    public static Location fromDeserializableString(String string) {
        String[] elements = string.replaceAll(" ", "").split(",");
        return new Location(Bukkit.getWorld(elements[3]), Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
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
        return (location.getX() + ", " + location.getY() + ", " + location.getZ()).replaceAll("(\\.\\d+)", "");
    }

    public static String randomizeData(String string) {
        return randomizeHalfs(randomizeFacings(randomizeBooleans(string)));
    }

    public static String randomizeHalfs(String string) {
        return string.toLowerCase().replaceAll("(bottom|top)", halfs[random.nextInt(halfs.length - 1)]);
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