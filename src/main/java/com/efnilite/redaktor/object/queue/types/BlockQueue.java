package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.AbstractBlockQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for setting a lot of blocks to the same material.
 */
public class BlockQueue extends AbstractBlockQueue implements EditQueue<Cuboid> {

    public BlockQueue(Material material) {
        super(material);
    }

    @Override
    public void build(Cuboid cuboid) {
        new AsyncBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), t -> {
            Queue<Block> queue = new LinkedList<>(t);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                        if (queue.peek() == null) {
                            this.cancel();
                            return;
                        }

                        Block block = queue.poll();
                        if (block.getType() != material) {
                            block.setType(material);
                        }
                    }
                }
            };
            Tasks.repeat(runnable, 1);
        });
    }
}