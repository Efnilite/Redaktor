package com.efnilite.redaktor;

import com.efnilite.redaktor.object.pattern.Pattern;
import com.efnilite.redaktor.object.queue.types.*;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import com.efnilite.redaktor.object.selection.internal.HistorySelection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
    private List<HistorySelection> history;

    public Editor(T sender) {
        this.change = 0;
        this.maxChange = -1;
        this.sendUpdates = false;
        this.sender = sender;
        this.history = new ArrayList<>();
        if (sender instanceof Player) {
            this.world = ((Player) sender).getWorld();
        } else {
            this.world = Bukkit.getWorlds().get(0);
        }
    }

    public Editor(T sender, World world) {
        this.change = 0;
        this.maxChange = -1;
        this.sendUpdates = false;
        this.sender = sender;
        this.world = world;
        this.history = new ArrayList<>();
    }

    public Editor(T sender, World world, int maxChange) {
        this.change = 0;
        this.sendUpdates = false;
        this.world = world;
        this.sender = sender;
        this.maxChange = maxChange;
        this.history = new ArrayList<>();
    }

    public Editor(T sender, World world, int maxChange, boolean sendUpdates) {
        this.change = 0;
        this.world = world;
        this.sender = sender;
        this.maxChange = maxChange;
        this.sendUpdates = sendUpdates;
        this.history = new ArrayList<>();
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
            BlockQueue queue = new BlockQueue(pattern);
            queue.build(cuboid);

            this.change += cuboid.getDimensions().getVolume();
            this.history.add(cuboid.toHistory());

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
            SlowBlockQueue queue = new SlowBlockQueue(pattern, perTick);
            queue.build(cuboid);

            this.change += cuboid.getDimensions().getVolume();
            this.history.add(cuboid.toHistory());

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
                queue.build(cuboid);

                this.change += cuboid.getDimensions().getVolume();
                this.history.add(cuboid.toHistory());

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
                queue.build(cuboid);

                this.change += cuboid.getDimensions().getVolume();
                this.history.add(cuboid.toHistory());

                send("You successfully set " + change + " blocks.");
            } else {
                throw new IllegalArgumentException("x, y and z need to be above 0");
            }
        }
    }

    /**
     * Undo an amount of actions.
     *
     * @param   amount
     *          The amount of undos.
     */
    public void undo(int amount) {
        for (int i = 0; i < amount; i++) {
            if (this.history.size() >= 1) {
                HistorySelection selection = this.history.get(0);

                CopyQueue queue = new CopyQueue();
                queue.build(selection.getBlockMap());

                this.history.remove(0);
            } else {
                return;
            }
        }
    }

    /**
     * Undo the latest action
     */
    public void undo() {
        this.undo(1);
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