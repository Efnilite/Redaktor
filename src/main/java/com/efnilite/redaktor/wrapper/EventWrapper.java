package com.efnilite.redaktor.wrapper;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class EventWrapper extends Event {

    private static HandlerList handlerList;

    public EventWrapper() {
        handlerList = new HandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}