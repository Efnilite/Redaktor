package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.pattern.Pattern;
import com.efnilite.redaktor.object.queue.AbstractBlockQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.util.AsyncFuture;
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

    public BlockQueue(Pattern pattern) {
        super(pattern);
    }

    @Override
    public int build(Cuboid cuboid) {
        IBlockFactory factory = Redaktor.getBlockFactory();
        AsyncFuture<Integer> future = new AsyncFuture<>();
        new AsyncBlockGetter(cuboid.getPos1(), cuboid.getPos2(), t -> {
            Queue<Block> queue = new LinkedList<>(t);
            int count = queue.size();

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                        future.complete(count);
                        if (queue.peek() == null) {
                            this.cancel();
                            return;
                        }

                        Block block = queue.poll();
                        Material material = pattern.apply(block);
                        if (block.getType() != material) {
                            factory.setBlock(block, material);
                        }
                    }
                }
            };
            Tasks.repeat(runnable, 1);
        });
        return future.get();
    }
}