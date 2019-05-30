package com.efnilite.redaktor;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.player.RedaktorPlayer;
import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.object.schematic.internal.BlockIndex;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
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
     *
     * @param   file
     *          The location where it will be saved.
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
     * Creates a new Schematic instance from a Cuboid.
     *
     * @param   cuboid
     *          The cuboid that needs to be saved.
     *
     * @return  a new Schematic
     */
    public static Schematic toSchematic(Cuboid cuboid) {
        return new Schematic(cuboid);
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
     * Creates a new AsyncIndexBlockGetter
     * This is used for getting a lot of blocks asynchronously,
     * including the index from position 1.
     *
     * Example:
     * If the second block of the list of blocks is returned,
     * the BlockIndex is 1, 0, 0. If it's the 10th block, it's 10, 0, 0.
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
     * @return  a new AsyncBlockIndexGetter
     */
    public static AsyncBlockIndexGetter newBlockIndexGetter(Location pos1, Location pos2, Consumer<HashMap<Block, BlockIndex>> consumer) {
        return new AsyncBlockIndexGetter(pos1, pos2, consumer);
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