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
 * A queue for setting a lot of blocks to specific materials.
 */
public class CopyQueue implements EditQueue<List<BlockMap>> {

    @Override
    public void build(List<BlockMap> map) {
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
    }
}