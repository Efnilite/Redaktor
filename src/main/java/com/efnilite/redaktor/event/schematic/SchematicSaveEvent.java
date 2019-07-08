package com.efnilite.redaktor.event.schematic;

import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.wrapper.EventWrapper;

/**
 * An event for when a {@link Schematic} gets saved.
 */
public class SchematicSaveEvent extends EventWrapper {

    /**
     * The schematic that's saved.
     */
    private Schematic schematic;
    /**
     * The directory it's saved to.
     */
    private String to;

    public SchematicSaveEvent(Schematic schematic, String to) {
        this.schematic = schematic;
        this.to = to;
    }

    public Schematic getSchematic() {
        return schematic;
    }

    public String getTo() {
        return to;
    }
}