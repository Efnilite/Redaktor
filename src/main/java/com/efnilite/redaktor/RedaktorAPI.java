package com.efnilite.redaktor;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.player.RedaktorPlayer;
import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
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
     * Save a cuboid to a file.
     *
     * @param   cuboid
     *          The cuboid to be saved.
     * @param   file
     *          The location where it will be saved.
     */
    public static void saveCuboid(Cuboid cuboid, String file) {
        newBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), l -> {
            try {
                new Schematic(cuboid).save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Creates a new Schematic instance from a .json file.
     *
     * @param   file
     *          The file path of the Schematic.
     *
     * @return  a new Schematic
     */
    public static Schematic load(String file) {
        return new Schematic(file);
    }

    /**
     * Creates a new AsyncBlockGetter.
     * This is to get a lot of blocks asynchronously.
     *
     * @param   pos1
     *          The minimum position of the square.
     *
     * @param   pos2
     *          The maximum position of the square.
     *
     * @param   consumer
     *          What to do when the blocks have been retrieved.
     *          Since this is an async thread, this is needed.
     *
     * @return  a new AsyncBlockGetter
     */
    public static AsyncBlockGetter newBlockGetter(Location pos1, Location pos2, Consumer<List<Block>> consumer) {
        return new AsyncBlockGetter(pos1, pos2, consumer);
    }

    /**
     * Returns the wrapper type of an org.bukkit.entity.Player
     * This has been created to store player-based data.
     *
     * @param   player
     *          The org.bukkit.entity.Player that needs to be converted.
     *
     * @return  a registered RedaktorPlayer
     */
    public static RedaktorPlayer wrap(Player player) {
        return RedaktorPlayer.wrap(player);
    }
}
