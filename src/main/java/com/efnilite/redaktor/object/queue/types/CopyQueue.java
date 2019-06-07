package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.object.queue.internal.BlockMap;
import com.efnilite.redaktor.util.AsyncFuture;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A queue for setting a lot of blocks to specific materials.
 */
public class CopyQueue implements EditQueue<List<BlockMap>> {

    @Override
    public int build(List<BlockMap> map) {
        IBlockFactory factory = Redaktor.getBlockFactory();
        AsyncFuture<Integer> future = new AsyncFuture<>();
        Queue<BlockMap> queue = new LinkedList<>(map);
        int count = queue.size();

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
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

