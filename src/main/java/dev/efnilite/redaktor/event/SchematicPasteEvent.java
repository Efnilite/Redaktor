package dev.efnilite.redaktor.event;

import dev.efnilite.redaktor.schematic.Schematic;
import dev.efnilite.redaktor.wrapper.EventWrapper;
import org.bukkit.Location;

/**
 * An event for when a {@link Schematic} gets pasted.
 */
public class SchematicPasteEvent extends EventWrapper {

    /**
     * The schematic that is pasted
     */
    private Schematic schematic;

    /**
     * The location where it's pasted
     */
    private Location location;

    /**
     * The file directory of the pasted {@link Schematic}
     */
    private String file;

    /**
     * Creates a new instance
     *
     * @param   schematic
     *          The schematic
     *
     * @param   location
     *          The location
     *
     * @param   file
     *          The file
     */
    public SchematicPasteEvent(Schematic schematic, Location location, String file) {
        this.schematic = schematic;
        this.location = location;
        this.file = file;
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
     * Gets the location
     *
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Gets the file
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }
}