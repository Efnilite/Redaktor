package com.efnilite.redaktor;

import com.efnilite.redaktor.object.player.RedaktorPlayer;
import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.object.schematic.internal.BlockIndex;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import com.efnilite.redaktor.util.AsyncFuture;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import com.efnilite.redaktor.util.getter.AsyncConnectedGetter;
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
    public static void saveCuboid(CuboidSelection cuboid, String file) {
        newBlockGetter(cuboid, l -> {
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
     * Creates a new Schematic instance from a CuboidSelection.
     *
     * @param   cuboid
     *          The cuboid that needs to be saved.
     *
     * @return  a new Schematic
     */
    public static Schematic toSchematic(CuboidSelection cuboid) {
        return new Schematic(cuboid);
    }

    /**
     * Creates a new AsyncBlockGetter.
     * This is to get a lot of blocks asynchronously.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   consumer
     *          What to do when the blocks have been retrieved.
     *          Since this is an async thread, this is needed.
     */
    public static void newBlockGetter(CuboidSelection cuboid, Consumer<List<Block>> consumer) {
        new AsyncBlockGetter(cuboid.getPos1(), cuboid.getPos2(), consumer);
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
     * @param   cuboid
     *          The cuboid.
     *
     * @param   consumer
     *          What to do when the blocks have been retrieved.
     *          Since this is an async thread, this is needed.
     */
    public static void newBlockIndexGetter(CuboidSelection cuboid, Consumer<HashMap<Block, BlockIndex>> consumer) {
        new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), consumer);
    }

    /**
     * Creates a new AsyncConnectedGetter.
     * This is used for asynchronously getting connected
     * blocks from the same type at a location.
     *
     * @param   pos
     *          The beginning position.
     *
     * @param   consumer
     *          What to do when all the blocks have been found.
     */
    public static void newBlockConnectedGetter(Location pos, Consumer<List<Block>> consumer) {
        new AsyncConnectedGetter(pos, consumer);
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

    /**
     * Checks if 2 Cuboids are the same.
     *
     * Idea by https://gitlab.com/Moderocky
     *
     * @param   cuboid
     *          The original cuboid.
     *
     * @param   possibleCopy
     *          The cuboid which might be a copy.
     *
     * @return  true if the cuboids have the same blocks.
     *          false if not.
     */
    public static boolean isCopy(CuboidSelection cuboid, CuboidSelection possibleCopy) {
        AsyncFuture<Boolean> future = new AsyncFuture<>();
        newBlockGetter(cuboid, t -> {
            newBlockGetter(possibleCopy, b -> {
                future.complete(t.containsAll(b));
            });
        });
        return future.get();
    }
}