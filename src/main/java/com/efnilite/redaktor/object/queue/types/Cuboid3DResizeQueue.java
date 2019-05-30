package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.AbstractResizeQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for copying cuboids in a 3D way
 */
public class Cuboid3DResizeQueue extends AbstractResizeQueue implements EditQueue<Cuboid> {

    public Cuboid3DResizeQueue(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public void build(Cuboid cuboid) {
        int x = cuboid.getDimensions().getWidth();
        int z = cuboid.getDimensions().getLength();

        new AsyncBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), t -> {
            Queue<Block> queue = new LinkedList<>();
            Queue<Block> original = new LinkedList<>();
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Cuboid3DResizeQueue.this.x; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(x, 0, 0).getBlock());
                        }
                    }
                    for (int i = 0; i < Cuboid3DResizeQueue.this.y; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(0, y, 0).getBlock());
                        }
                    }
                    for (int i = 0; i < Cuboid3DResizeQueue.this.z; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(0, 0, z).getBlock());
                        }
                    }
                }
            };
            Tasks.async(runnable);

            BukkitRunnable runnable1 = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                        if (queue.peek() == null || original.peek() == null) {
                            this.cancel();
                            return;
                        }

                        Block block = queue.poll();
                        Block or = original.poll();
                        if (block.getType() != or.getType()) {
                            block.getLocation().getBlock().setType(or.getType());
                            if (block.getBlockData() != or.getBlockData()) {
                                block.getLocation().getBlock().setBlockData(or.getBlockData());
                            }
                        }
                    }
                }
            };
            Tasks.repeat(runnable1, 1);
        });
    }
}