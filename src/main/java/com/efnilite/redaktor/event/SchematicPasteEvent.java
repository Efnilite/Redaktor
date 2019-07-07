package com.efnilite.redaktor.event;

import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.wrapper.EventWrapper;
import org.bukkit.Location;

/**
 * An event for when a {@link Schematic} gets pasted.
 */
public class SchematicPasteEvent extends EventWrapper {

    private Schematic schematic;
    private Location location;
    private String file;

    public SchematicPasteEvent(Schematic schematic, Location location, String file) {
        this.schematic = schematic;
        this.location = location;
        this.file = file;

    }

    public Schematic getSchematic() {
        return schematic;
    }

    public Location getLocation() {
        return location;
    }

    public String getFile() {
        return file;
    }
}