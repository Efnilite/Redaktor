package com.efnilite.redaktor.util;

import org.bukkit.Location;

public class Strings {

    public static String toString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ()).replace(".0", "");
    }
}
