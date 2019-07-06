package com.efnilite.redaktor.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for copying cuboid in a 2D way (using x and z)
 */
public class Cuboid2DResizeQueue implements EditQueue<CuboidSelection> {

    private int x;
    private int z;

    public Cuboid2DResizeQueue(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public void build(CuboidSelection cuboid) {
        BlockFactory factory = Redaktor.getBlockFactory();
        int x = cuboid.getDimensions().getWidth();
        int z = cuboid.getDimensions().getLength();

        new AsyncBlockGetter(cuboid.getPos1(), cuboid.getPos2(), t -> {
            Queue<Block> queue = new LinkedList<>();
            Queue<Block> original = new LinkedList<>();
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Cuboid2DResizeQueue.this.x; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(x, 0, 0).getBlock());
                        }
                    }
                    for (int i = 0; i < Cuboid2DResizeQueue.this.z; i++) {
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
                        if (queue.peek() == null || original.peek() != null) {
                            this.cancel();
                            return;
                        }

                        Block block = queue.poll();
                        Block or = original.poll();
                        if (block.getType() != or.getType()) {
                            factory.setBlock(block.getLocation(), or.getBlockData());
                        }
                    }
                }
            };
            Tasks.repeat(runnable1, 1);
        });
    }
}