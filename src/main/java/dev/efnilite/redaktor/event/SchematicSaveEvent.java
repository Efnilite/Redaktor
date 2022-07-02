package dev.efnilite.redaktor.event;

import dev.efnilite.redaktor.schematic.Schematic;
import dev.efnilite.redaktor.schematic.SchematicType;
import dev.efnilite.redaktor.wrapper.EventWrapper;

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
     * The type of schematic
     */
    private SchematicType type;

    /**
     * Creates a new instance
     *
     * @param   schematic
     *          The schematic
     *
     * @param   to
     *          The path
     */
    public SchematicSaveEvent(Schematic schematic, SchematicType type, String to) {
        this.schematic = schematic;
        this.type = type;
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
     * Gets the type
     *
     * @return the type
     */
    public SchematicType getType() {
        return type;
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