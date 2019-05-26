package com.efnilite.redaktor;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class RedaktorAPI {

    private static Plugin plugin;

    static {
        plugin = Redaktor.getInstance();
    }

    private RedaktorAPI() {

    }

    /**
     * Save a cuboid to a file
     * @param cuboid the cuboid to be saved
     * @param file the location where it will be saved
     */
    public static void saveCuboid(Cuboid cuboid, String file) {
        newBlockGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
            try {
                new Schematic(cuboid).save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates a new Schematic instance from a .json file
     * @param file the file path
     * @return a new Schematic
     */
    public static Schematic load(String file) {
        return new Schematic(file);
    }

    /**
     * Creates a new AsyncBlockGetter
     * @param pos1 the minimum position
     * @param pos2 the maximum position
     * @param consumer what to do when the blocks have been retrieved
     * @return a new AsyncBlockGetter
     */
    public static AsyncBlockGetter newBlockGetter(Location pos1, Location pos2, Consumer<List<Block>> consumer) {
        return new AsyncBlockGetter(pos1, pos2, consumer);
    }
}
