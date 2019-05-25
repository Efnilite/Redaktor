package com.efnilite.redaktor;

import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.util.getter.BlockGetter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Consumer;

public class RedaktorAPI {

    private static Plugin plugin;

    static {
        plugin = Redaktor.getInstance();
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
     * Creates a new BlockGetter
     * @param pos1 the minimum position
     * @param pos2 the maximum position
     * @param consumer what to do when the blocks have been retrieved
     * @return a new BlockGetter
     */
    public static BlockGetter newBlockGetter(Location pos1, Location pos2, Consumer<List<Block>> consumer) {
        return new BlockGetter(pos1, pos2, consumer);
    }
}
