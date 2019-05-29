package com.efnilite.redaktor.util.bukkit;

import org.bukkit.Location;

public class Locations {

    public static Location getMinimum(Location one, Location two) {
        return new Location(one.getWorld() == null ? two.getWorld() : one.getWorld(),
                Math.min(one.getBlockX(), two.getBlockX()), Math.min(one.getBlockY(), two.getBlockY()), Math.min(one.getBlockZ(), two.getBlockZ()));
    }

    public static Location getMaximum(Location one, Location two) {
        return new Location(one.getWorld() == null ? two.getWorld() : one.getWorld(),
                Math.max(one.getBlockX(), two.getBlockX()), Math.max(one.getBlockY(), two.getBlockY()), Math.max(one.getBlockZ(), two.getBlockZ()));
    }
}
