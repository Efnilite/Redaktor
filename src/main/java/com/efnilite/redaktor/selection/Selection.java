package com.efnilite.redaktor.selection;

import org.bukkit.Location;
import org.bukkit.World;

/**
 * An interface for all selections
 */
public interface Selection {

    /**
     * Transfers this selection from a normal selection to a {@link HistorySelection}
     *
     * @return a new {@link HistorySelection} instance
     */
    HistorySelection toHistory();

    /**
     * Gets the first position of this selection
     *
     * @return the first position
     */
    Location getPos1();

    /**
     * Gets the second position of this selection
     *
     * @return the second position
     */
    Location getPos2();

    /**
     * Gets the world
     *
     * @return the world
     */
    World getWorld();

    /**
     * Gets the dimensions of the selection
     *
     * @return the dimensions
     */
    Dimensions getDimensions();
}