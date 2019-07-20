package com.efnilite.redaktor.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.queue.BlockMap;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A queue for dramatic effect.
 *
 * @see CopyQueue
 */
public class SlowCopyQueue implements EditQueue<List<BlockMap>> {

    /**
     * The blocks/tick
     */
    private int tick;

    /**
     * Creates a new instance
     *
     * @param   tick
     *          The blocks/tick
     */
    public SlowCopyQueue(int tick) {
        this.tick = tick;
    }

    @Override
    public void build(List<BlockMap> blocks) {
        BlockFactory factory = Redaktor.getBlockFactory();
        Queue<BlockMap> queue = new LinkedList<>(blocks);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < tick; i++) {
                    if (queue.peek() == null) {
                        this.cancel();
                        return;
                    }

                    BlockMap map = queue.poll();
                    if (!map.getData().getAsString(true).equals(map.getBlock().getBlockData().getAsString(true))) {
                        factory.setBlock(map.getBlock().getLocation(), map.getData());
                    }
                }
            }
        };
        Tasks.repeat(runnable, 1);
    }
}