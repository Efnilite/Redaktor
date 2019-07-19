package com.efnilite.redaktor.util;

import org.bukkit.Material;

/**
 * A class for storing stuff
 * Will be documented soon
 */
public class QuickUtil {

    public static boolean shouldUpdateOnPlace(Material material) {
        switch (material) {
            case BLACK_STAINED_GLASS_PANE:
            case BLUE_STAINED_GLASS_PANE:
            case BROWN_STAINED_GLASS_PANE:
            case CYAN_STAINED_GLASS_PANE:
            case GRAY_STAINED_GLASS_PANE:
            case GREEN_STAINED_GLASS_PANE:
            case LIGHT_BLUE_STAINED_GLASS_PANE:
            case LIGHT_GRAY_STAINED_GLASS_PANE:
            case LIME_STAINED_GLASS_PANE:
            case MAGENTA_STAINED_GLASS_PANE:
            case ORANGE_STAINED_GLASS_PANE:
            case PINK_STAINED_GLASS_PANE:
            case PURPLE_STAINED_GLASS_PANE:
            case RED_STAINED_GLASS_PANE:
            case WHITE_STAINED_GLASS_PANE:
            case YELLOW_STAINED_GLASS_PANE:
            case GLASS_PANE:
            case IRON_BARS:
            case COBBLESTONE_WALL:
            case MOSSY_COBBLESTONE_WALL:
                return true;
            default:
                return false;
        }
    }
}