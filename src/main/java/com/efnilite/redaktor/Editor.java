package com.efnilite.redaktor;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.queue.types.*;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.HistorySelection;
import com.efnilite.redaktor.selection.Selection;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.efnilite.redaktor.util.thread.QueueThread;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * The main editor for APIs and Players.
 *
 * @param   <T>
 *          The CommandSender to which changes need to be sent.
 */
public class Editor<T extends CommandSender> {

    /**
     * The instance of who to send messages to if {@link #sendUpdates} is true
     */
    private T sender;

    /**
     * The amount of blocks changed
     */
    private int change;

    /**
     * The maximal change.
     */
    private int maxChange;

    /**
     * If this Editor instance should send updates when actions are completed.
     */
    private boolean sendUpdates;

    /**
     * The list of actions taken so far so that they can be undone.
     */
    private List<HistorySelection> history;

    /**
     * A list so for redoing actions
     */
    private List<HistorySelection> undos;

    /**
     * The fast block editor
     */
    private BlockFactory factory;

    /**
     * The private thread
     */
    private QueueThread thread;

    public Editor(T sender) {
        this(sender, -1, true);
    }

    public Editor(T sender, int maxChange) {
        this(sender, maxChange, true);
    }

    /**
     * The recommended constructor if you want to specify every option.
     *
     * @param   sender
     *          The instance to who to send messages if {@link #sendUpdates} is true
     *
     * @param   maxChange
     *          The max amount of blocks that can be changed.
     *
     * @param   sendUpdates
     *          If there should be updates sent to the {@link #sender} instance if an action is completed.
     *
     *
     */
    public Editor(T sender, int maxChange, boolean sendUpdates) {
        this.sender = sender;
        this.change = 0;
        this.maxChange = maxChange;
        this.sendUpdates = sendUpdates;
        this.history = new ArrayList<>();
        this.undos = new ArrayList<>();
        this.factory = Redaktor.getBlockFactory();
        this.thread = new QueueThread();
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
    public void setBlocks(Selection cuboid, Pattern pattern) {
        if (checkLimit()) {
            BlockQueue queue = new BlockQueue(pattern);

            store(cuboid.toHistory());

            queue.build(cuboid);

            this.change += cuboid.getDimensions().getVolume();

            send("You successfully set " + cuboid.getDimensions().getVolume() + " blocks");
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

            store(cuboid.toHistory());

            queue.build(cuboid);

            this.change += cuboid.getDimensions().getVolume();

            send("You successfully set " + cuboid.getDimensions().getVolume() + " blocks");
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

                store(cuboid.toHistory());

                queue.build(cuboid);

                this.change += cuboid.getDimensions().getVolume() * x * z;

                send("You successfully set " + (cuboid.getDimensions().getVolume() * x * z) + " blocks");
            } else {
                send("You need to set the x and z above 0");
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

                store(cuboid.toHistory());

                queue.build(cuboid);

                this.change += cuboid.getDimensions().getVolume() * x * y * z;

                send("You successfully set " + (cuboid.getDimensions().getVolume() * x * y * z) + " blocks");
            } else {
                send("You need to set the x, y and z above 0");
            }
        }
    }

    /**
     * Replaces all blocks that match the 'find' {@link Pattern} to the 'replace' {@link Pattern}
     * in a {@link CuboidSelection}
     *
     * @param   selection
     *          The selection of where all blocks will be replaced.
     *
     * @param   find
     *          The BlockDatas that will be checked to match blocks.
     *
     * @param   replace
     *          The {@link Pattern} that it will be replaced by.
     */
    public void replace(CuboidSelection selection, List<BlockData> find, Pattern replace) {
        if (checkLimit()) {
            new AsyncBlockGetter(selection, t -> {
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block block : t) {
                            for (BlockData data : find) {
                                if (block.getBlockData().getAsString().equals(data.getAsString())) {
                                    block.setBlockData(replace.apply(block));
                                    break;
                                }
                            }
                        }
                    }
                };
                Tasks.task(runnable);
            });
        }
    }

    /**
     * Replaces all blocks in a certain radius.
     *
     * @param   location
     *          The center of where it will try to find blocks matching the first pattern.
     *
     * @param   radius
     *          The radius in which the code will search for blocks matching the first pattern.
     *
     * @param   find
     *          The BlockDatas it will check to see if it matches.
     *
     * @param   replace
     *          The {@link Pattern} the found blocks will be replaced by.
     */
    public void replaceAll(Location location, int radius, List<BlockData> find, Pattern replace) {
        if (checkLimit()) {
            double half = radius / 2.0;
            CuboidSelection selection = new CuboidSelection(location.clone().subtract(half, half, half), location.clone().add(half, half, half));
            new AsyncBlockGetter(selection, t -> {
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        for (Block block : t) {
                            for (BlockData data : find) {
                                if (block.getBlockData().getAsString().equals(data.getAsString())) {
                                    block.setBlockData(replace.apply(block));
                                    break;
                                }
                            }
                        }
                    }
                };
                Tasks.task(runnable);
            });
        }
    }

    /**
     * Adds a {@link HistorySelection} to the history.
     * You can transform {@link CuboidSelection} to a {@link HistorySelection} with {@link CuboidSelection#toHistory()}
     *
     * @param   selection
     *          The {@link HistorySelection} to be added to the history.
     */
    public void store(HistorySelection selection) {
        this.history.add(0, selection);
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

                this.undos.add(0, new HistorySelection(selection));

                queue.build(selection.getBlockMap());

                this.history.remove(0);
            } else {
                return;
            }
        }
        send("Undone " + amount + " action(s)");
    }

    /**
     * Redo a certain amount of undos
     *
     * @param   amount
     *          The amount of undos to be undone
     */
    public void redo(int amount) {
        for (int i = 0; i < amount; i++) {
            if (this.undos.size() >= 1) {
                HistorySelection selection = this.undos.get(0);

                CopyQueue queue = new CopyQueue();
                queue.build(selection.getBlockMap());

                this.undos.remove(0);
            } else {
                return;
            }
        }
        send("Redone " + amount + " action(s)");
    }

    /**
     * Undo the latest action
     */
    public void undo() {
        this.undo(1);
    }

    /**
     * Redo the last undo
     */
    public void redo() {
        this.redo(1);
    }

    /**
     * Clear the history of this Editor.
     */
    public void clearHistory() {
        this.history.clear();
        this.undos.clear();
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
            this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Redaktor.PREFIX + " &7" + message));
        }
    }

    /**
     * Sets a block at a location using the {@link BlockFactory}
     *
     * @param   location
     *          The location.
     *
     * @param   pattern
     *          The pattern.
     */
    public void setBlock(Location location, Pattern pattern) {
        this.factory.setBlock(location, pattern.apply(location.getBlock()));
    }

    /**
     * Gets the instance of the sender to who to send messages if {@link #sendUpdates} is ture.
     *
     * @return the sender instance
     */
    public T getSender() {
        return sender;
    }

    /**
     * The amount of blocks that have been changed so far by this instance.
     *
     * @return the amount of blocks changed.
     */
    public int getChange() {
        return change;
    }

    /**
     * Get the max amount of change specified in the constructor.
     *
     * @return the max amount of change.
     */
    public int getMaxChange() {
        return maxChange;
    }

    /**
     * All the edits that have been executed so far.
     *
     * @return the history
     */
    public List<HistorySelection> getHistory() {
        return history;
    }

    /**
     * Gets the list of all actions that were undone.
     *
     * @return the actions that were undone
     */
    public List<HistorySelection> getUndos() {
        return undos;
    }
}