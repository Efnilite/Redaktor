package com.efnilite.redaktor.event;

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

    /**
     * Creates a new instance
     *
     * @param   schematic
     *          The schematic
     *
     * @param   to
     *          The path
     */
    public SchematicSaveEvent(Schematic schematic, String to) {
        this.schematic = schematic;
        this.to = to;
    }

    /**
     * Gets the schematic
     *
     * @return the schematic
     */
    public Schematic getSchematic() {
        return schematic;
    }

    /**
     * Gets the path
     *
     * @return the path
     */
    public String getTo() {
        return to;
    }
}