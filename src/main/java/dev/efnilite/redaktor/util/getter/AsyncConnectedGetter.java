package dev.efnilite.redaktor.util.getter;

import dev.efnilite.redaktor.util.thread.Tasks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for getting connected blocks from a specific location.
 *
 * Idea by https://gitlab.com/Moderocky
 */
public class AsyncConnectedGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    /**
     * The original position
     */
    private Location pos;

    /**
     * A list of all block directions, used for getting the relative blocks.
     */
    private BlockFace[] directions;

    /**
     * The consumer -> what to do when the blocks have been collected
     */
    private Consumer<List<Block>> consumer;

    /**
     * Creates a new instance
     *
     * @param   pos
     *          The position from where the connected blocks will be counted
     *
     * @param   consumer
     *          What to do when all the blocks have been gathered
     */
    public AsyncConnectedGetter(Location pos, Consumer<List<Block>> consumer) {
        this.pos = pos;
        this.consumer = consumer;
        this.directions = new BlockFace[] {
                BlockFace.NORTH,
                BlockFace.SOUTH,
                BlockFace.WEST,
                BlockFace.EAST,
                BlockFace.DOWN,
                BlockFace.UP
        };

        Tasks.async(this);
    }

    @Override
    public void run() {
        HashSet<Block> blocks = new HashSet<>();
        HashSet<Block> queue = new HashSet<>();
        Material material = pos.getBlock().getType();
        blocks.add(pos.getBlock());
        queue.add(pos.getBlock());

        Block current;
        while (!queue.isEmpty()) {
            current = (Block) queue.toArray()[0];
            queue.remove(current);
            for (BlockFace face : directions) {
                Block relative = current.getRelative(face);
                if (relative.getType() == material) {
                    if (!blocks.contains(relative)) {
                        queue.add(relative);
                        blocks.add(relative);
                    }
                }
            }
        }
        asyncDone(new ArrayList<>(blocks));
    }

    @Override
    public void asyncDone(List<Block> value) {
        consumer.accept(value);
    }
}