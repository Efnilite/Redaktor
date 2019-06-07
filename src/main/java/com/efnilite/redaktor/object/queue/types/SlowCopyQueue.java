package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.queue.AbstractSlowQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.object.queue.internal.BlockMap;
import com.efnilite.redaktor.util.AsyncFuture;
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
public class SlowCopyQueue extends AbstractSlowQueue implements EditQueue<List<BlockMap>> {

    public SlowCopyQueue(int perTick) {
        super(perTick);
    }

    @Override
    public int build(List<BlockMap> blocks) {
        IBlockFactory factory = Redaktor.getBlockFactory();
        Queue<BlockMap> queue = new LinkedList<>(blocks);
        AsyncFuture<Integer> future = new AsyncFuture<>();
        int count = queue.size();

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < perTick; i++) {
                    if (queue.peek() == null) {
                        future.complete(count);
                        this.cancel();
                        return;
                    }

                    BlockMap map = queue.poll();
                    if (map.getMaterial() != map.getBlock().getType()) {
                        factory.setBlock(map.getBlock(), map.getMaterial());
                    }
                }
            }
        };
        Tasks.repeat(runnable, 1);
        return future.get();
    }
}

