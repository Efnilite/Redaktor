package com.efnilite.redaktor.wrapper;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * A class for removing unnecessary methods that are the same across all Event classes,
 * making the actual Event classes cleaner.
 */
public abstract class EventWrapper extends Event {

    /**
     * The handlerlist
     */
    private static HandlerList handlerList;

    /**
     * Creates a new instance
     */
    public EventWrapper() {
        handlerList = new HandlerList();
    }

    /**
     * Gets the handlers
     *
     * @return the handlers
     */
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    /**
     * The method that's required for every event (for some reason)
     *
     * @return a HandlerList
     */
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}