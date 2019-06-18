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
import org.bukkit.World;
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
        if (checkLimit()) {
            Redaktor.getInstance().getLogger().info("BlockQueue");
            BlockQueue queue = new BlockQueue(pattern);
            Redaktor.getInstance().getLogger().info("Build");
            int change = queue.build(cuboid);
            this.change += change;
            send("You successfully set " + change + " blocks.");
        }
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
        if (checkLimit()) {
            SlowBlockQueue queue = new SlowBlockQueue(perTick, pattern);
            int change = queue.build(cuboid);
            this.change += change;
            send("You successfully set " + change + " blocks.");
        }
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
        if (checkLimit()) {
            if (x > 0 && z > 0) {
                Cuboid2DResizeQueue queue = new Cuboid2DResizeQueue(x, z);
                int change = queue.build(cuboid);
                this.change += change;
                send("You successfully set " + change + " blocks.");
            } else {
                throw new IllegalArgumentException("x and z need to be above 0");
            }
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
        if (checkLimit()) {
            if (x > 0 && y > 0 && z > 0) {
                Cuboid3DResizeQueue queue = new Cuboid3DResizeQueue(x, y, z);
                int change = queue.build(cuboid);
                this.change += change;
                send("You successfully set " + change + " blocks.");
            } else {
                throw new IllegalArgumentException("x, y and z need to be above 0");
            }
        }
    }

    /**
     * Checks the change limit.
     * <p>
     * The change limit can be used for restricting how many blocks a player
     * (or an api) can edit.
     *
     * @return false if it is over the limit
     */
    public boolean checkLimit() {
        boolean con = this.change > this.maxChange;
        if (!con) {
            send("Your Editor has reached the maximal change count!");
        }
        return con;
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
}