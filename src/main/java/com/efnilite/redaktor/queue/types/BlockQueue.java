package com.efnilite.redaktor.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.selection.CuboidSelection;
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
public class BlockQueue implements EditQueue<CuboidSelection> {

    private Pattern pattern;

    public BlockQueue(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void build(CuboidSelection cuboid) {
        IBlockFactory factory = Redaktor.getBlockFactory();
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
                        Material material = pattern.apply(block);
                        if (block.getType() != material) {
                            factory.setBlock(block, material);
                        }
                    }
                }
            };
            Tasks.repeat(runnable, 1);
        });
    }
}