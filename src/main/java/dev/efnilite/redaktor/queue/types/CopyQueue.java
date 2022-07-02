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
 * A queue for setting a lot of blocks to specific materials.
 */
public class CopyQueue implements EditQueue<List<BlockMap>> {

    @Override
    public int build(List<BlockMap> map) {
        BlockFactory factory = Redaktor.getBlockFactory();
        Queue<BlockMap> queue = new LinkedList<>(map);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                    if (queue.peek() == null) {
                        this.cancel();
                        return;
                    }

                    BlockMap map = queue.poll();
                    if (!map.getData().getAsString().equals(map.getBlock().getBlockData().getAsString())) {
                        factory.setBlock(map.getBlock().getLocation(), map.getData());
                    }
                }
            }
        };
        Tasks.repeat(runnable, 1);
        return 1;
    }
}