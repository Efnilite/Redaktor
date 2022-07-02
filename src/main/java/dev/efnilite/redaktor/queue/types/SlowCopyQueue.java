package dev.efnilite.redaktor.queue.types;

import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.block.BlockFactory;
import dev.efnilite.redaktor.queue.BlockMap;
import dev.efnilite.redaktor.queue.EditQueue;
import dev.efnilite.redaktor.util.thread.Tasks;
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
    public int build(List<BlockMap> blocks) {
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
        return 1;
    }
}