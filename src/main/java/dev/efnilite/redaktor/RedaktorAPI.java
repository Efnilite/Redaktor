package dev.efnilite.redaktor;

import dev.efnilite.redaktor.pattern.Pattern;
import dev.efnilite.redaktor.pattern.types.BlockPattern;
import dev.efnilite.redaktor.pattern.types.MultiplePattern;
import dev.efnilite.redaktor.player.BukkitPlayer;
import dev.efnilite.redaktor.schematic.Schematic;
import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.util.ChangeAllocator;
import dev.efnilite.redaktor.util.getter.AsyncBlockGetter;
import dev.efnilite.redaktor.util.getter.AsyncConnectedGetter;
import dev.efnilite.redaktor.util.item.SuperUtil;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

/**
 * The main API class.
 * The goal of this class is to make all of Redaktor's features easily usable.
 * This class will supply the basic methods, if you don't want to dig deep in how to do specific stuff.
 *
 * @see Selection
 * @see Schematic
 * @see Pattern
 * @see Editor
 *
 * @see com.efnilite.redaktor.event
 */
public class RedaktorAPI {

    /**
     * The pattern parser
     */
    private static Pattern.Parser parser;

    /**
     * The default editor instance
     */
    private static Editor<ConsoleCommandSender> defaultEditor;

    static  {
        parser = new Pattern.Parser();
        defaultEditor = new Editor<>(Bukkit.getConsoleSender(), -1, false);
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
            MultiplePattern pattern = new MultiplePattern();
            for (Material material : materials) {
                pattern.add(material.createBlockData());
            }
            return pattern;
        }
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
            new Schematic(cuboid).save(file);
        });
    }

    /**
     * Convert an ItemStack to a SuperItem.
     *
     * @param   item
     *          The instance of ItemStack to be converted.
     *
     * @param   command
     *          The command to be executed.
     */
    public static void createSuperItem(ItemStack item, String command) {
        SuperUtil.create(item, command);
    }

    /**
     * Get a default Editor instance.
     * This is a way to use Editor methods in an API
     * without using players.
     * <p>
     * The Editor returned has an unlimited max change and does not
     * log chances to the sender (in this case console)
     * <p>
     * Please note that using this method can cause conflict issues with other
     * plugins using this method, as it will return one specific {@link Editor} instance.
     * For example: If you make a lot of changes another plugin can undo them even if you
     * don't want to.
     * That's why you can also just create a new instance of an {@link Editor} to avoid
     * conflicts.
     *
     * @return  The main Editor.
     */
    public static Editor<ConsoleCommandSender> getDefaultEditor() {
        return defaultEditor;
    }

    /**
     * Loads a {@link Schematic} from a file.
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
     * Turns a {@link CuboidSelection} into a {@link Schematic}
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
     * @param   selection
     *          The selection.
     *
     * @param   consumer
     *          What to do when the blocks have been retrieved.
     *          Since this is an async thread, this is needed.
     */
    public static void newBlockGetter(Selection selection, Consumer<List<Block>> consumer) {
        new AsyncBlockGetter(selection, consumer);
    }

    /**
     * Creates a new AsyncConnectedGetter.
     * <p>
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
     * Get the tps of the server calculated by {@link ChangeAllocator}
     *
     * @return the tps
     */
    public static double getTps() {
        return Redaktor.getAllocator().getTps();
    }

    /**
     * Checks if the server is running the latest version (1.14.x)
     *
     * @return true if the server is using 1.14.x
     */
    public static boolean isLastest() {
        return Redaktor.isLatest();
    }

    /**
     * A way to parse a Pattern from a String.
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