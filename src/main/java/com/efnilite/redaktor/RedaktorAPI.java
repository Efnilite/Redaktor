package com.efnilite.redaktor;

import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.pattern.types.BlockPattern;
import com.efnilite.redaktor.pattern.types.MorePattern;
import com.efnilite.redaktor.player.BukkitPlayer;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.schematic.internal.BlockIndex;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.AsyncFuture;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import com.efnilite.redaktor.util.getter.AsyncConnectedGetter;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class RedaktorAPI {

    private static Pattern.Parser parser;
    private static HashMap<World, Editor<ConsoleCommandSender>> worldEditors;

    RedaktorAPI() {
        parser = new Pattern.Parser();
        worldEditors = new HashMap<>();

        for (World world : Bukkit.getWorlds()) {
            worldEditors.put(world, new Editor<>(Bukkit.getConsoleSender()));
        }
    }

    /**
     * A shortcut for converting patterns.
     * <p>
     * Parses an array of Materials to a Pattern, usable
     * for most block setting methods in common classes.
     *
     * @param   materials
     *          The material(s) that need to be transferred
     *          to a pattern.
     *
     * @return  A pattern based on the given materials.
     */
    public static Pattern toPattern(Material... materials) {
        if (materials.length == 1) {
            return new BlockPattern(materials[0].createBlockData());
        } else {
            MorePattern pattern = new MorePattern();
            for (Material material : materials) {
                pattern.add(material.createBlockData());
            }
            return pattern;
        }
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

    /*/**
     * Convert an ItemStack to a SuperItem.
     *
     * @param   item
     *          The ItemStack to be converted.
     *
     * @param   whatToExecute
     *          The command to be executed.
     */
    /*public static void createSuperItem(ItemStack item, String whatToExecute) {
        SuperItemCommands.SuperItemBuilder.create(item, whatToExecute);
    }*/

    /**
     * Get a default Editor instance for a World.
     * This is a way to use Editor methods in an API
     * without using players.
     * <p>
     * The Editor returned has an unlimited max change and does not
     * log chances to the sender (in this case console)
     *
     * @param   world
     *          The world the Editor is based in.
     *
     * @return  The Editor for that world.
     */
    public static Editor<ConsoleCommandSender> getDefaultEditor(World world) {
        return worldEditors.get(world);
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
     * <p>
     * This is used for getting a lot of blocks asynchronously,
     * including the index from position 1.
     * <p>
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
     * A way to parse a Pattern from a string.
     * Note: this isn't tested yet
     *
     * @param   string
     *          The string of the Pattern.
     *
     * @return  the Pattern instance, made with the data available in string (the param).
     */
    public static Pattern parsePattern(String string) {
        return parser.parse(string);
    }

    /**
     * Returns the wrapper type of an org.bukkit.entity.Player
     * This has been created to store player-based data.
     *
     * @param   player
     *          The org.bukkit.entity.Player that needs to be converted.
     *
     * @return  a registered BukkitPlayer
     */
    public static BukkitPlayer wrap(Player player) {
        return BukkitPlayer.wrap(player);
    }

    /**
     * Returns a RedaktorPlayer from a CommandSender.
     *
     * @param   sender
     *          The sender
     *
     * @return  a RedaktorPlayer instance
     */
    public static RedaktorPlayer<?> wrap(CommandSender sender) {
        return RedaktorPlayer.wrap(sender);
    }
}