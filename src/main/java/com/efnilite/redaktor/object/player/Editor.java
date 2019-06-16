package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.pattern.Pattern;
import com.efnilite.redaktor.object.queue.types.BlockQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid2DResizeQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid3DResizeQueue;
import com.efnilite.redaktor.object.queue.types.SlowBlockQueue;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * The main editor for APIs and Players.
 *
 * @param   <T>
 *          The CommandSender to which changes need to be sent.
 */
public class Editor<T extends CommandSender> {

    private T sender;
    private int change;
    private int maxChange;
    private boolean sendUpdates;
    private World world;
    private IBlockFactory tools;

    public Editor(T sender) {
        this.change = 0;
        this.maxChange = -1;
        this.sendUpdates = false;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        if (sender instanceof Player) {
            this.world = ((Player) sender).getWorld();
        }
    }

    public Editor(T sender, World world) {
        this.change = 0;
        this.maxChange = -1;
        this.sendUpdates = false;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        this.world = world;
    }

    public Editor(T sender, World world, int maxChange) {
        this.change = 0;
        this.sendUpdates = false;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        this.world = world;
        this.maxChange = maxChange;
    }

    public Editor(T sender, World world, int maxChange, boolean sendUpdates) {
        this.change = 0;
        this.sender = sender;
        this.maxChange = maxChange;
        this.sendUpdates = sendUpdates;
        this.world = world;
        this.tools = Redaktor.getBlockFactory();
    }

    /**
     * Set all blocks in a CuboidSelection to a certain material.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   pattern
     *          The pattern the blocks need to be changed to.
     */
    public void setBlocks(CuboidSelection cuboid, Pattern pattern) {
        BlockQueue queue = new BlockQueue(pattern);
        int change = queue.build(cuboid);
        this.change += change;
        send("You successfully set " + change + " blocks.");
    }

    /**
     * Set all blocks slowly in a CuboidSelection to a certain material.
     *
     * @param   pattern
     *          The pattern the blocks need to be changed to.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   perTick
     *          The amount of blocks that need to be changed per tick.
     */
    public void setSlowBlocks(CuboidSelection cuboid, Pattern pattern, int perTick) {
        SlowBlockQueue queue = new SlowBlockQueue(perTick, pattern);
        int change = queue.build(cuboid);
        this.change += change;
        send("You successfully set " + change + " blocks.");
    }

    /**
     * Copies a CuboidSelection in a 2-dimensional way.
     *
     * @see #copyCuboid(CuboidSelection, int, int, int) for a 3D way of doing this.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   x
     *          The amount of times the cuboid needs to be copied in the x-value.
     *
     * @param   z
     *          The amount of times the cuboid needs to be copied in the z-value.
     */
    public void copyCuboid(CuboidSelection cuboid, int x, int z) {
        if (x > 0 && z > 0) {
            Cuboid2DResizeQueue queue = new Cuboid2DResizeQueue(x, z);
            int change = queue.build(cuboid);
            this.change += change;
            send("You successfully set " + change + " blocks.");
        } else {
            throw new IllegalStateException("x and z need to be above 0");
        }
    }

    /**
     * Copies a CuboidSelection in a 3-dimensional way.
     *
     * @see #copyCuboid(CuboidSelection, int, int) for a 2D way of doing this.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   x
     *          The amount of times the cuboid needs to be copied in the x-value.
     *
     * @param   y
     *          The amount of times the cuboid needs to be copied in the y-value.
     *
     * @param   z
     *          The amount of times the cuboid needs to be copied in the z-value.
     */
    public void copyCuboid(CuboidSelection cuboid, int x, int y, int z) {
        if (x > 0 && y > 0 && z > 0) {
            Cuboid3DResizeQueue queue = new Cuboid3DResizeQueue(x, y, z);
            int change = queue.build(cuboid);
            this.change += change;
            send("You successfully set " + change + " blocks.");
        } else {
            throw new IllegalStateException("x, y and z need to be above 0");
        }
    }


    /**
     * Sends a message to the Editor owner, if {@link #sendUpdates} is true.
     *
     * @param   message
     *          The message to be sent.
     */
    public void send(String message) {
        if (this.sendUpdates) {
            this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&a&cRedaktor&r&8) &7" + message));
        }
    }

    /**
     * Get a block at a location.
     *
     * @see #getBlock(Location)
     *
     * @param   x
     *          The x-value.
     *
     * @param   y
     *          The y-value.
     *
     * @param   z
     *          The z-value.
     *
     * @return  The block at the location.
     */
    public Block getBlock(int x, int y, int z) {
        return this.getBlock(new Location(world, x, y, z));
    }

    /**
     * Get a block from a location.
     *
     * @param   location
     *          The location.
     *
     * @return  The block at the location.
     */
    public Block getBlock(Location location) {
        return world.getBlockAt(location);
    }
}
