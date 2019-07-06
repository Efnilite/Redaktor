package com.efnilite.redaktor.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
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
        BlockFactory factory = Redaktor.getBlockFactory();
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
                        BlockData data = pattern.apply(block);
                        if (!block.getBlockData().getAsString(true).equals(data.getAsString(true))) {
                            factory.setBlock(block.getLocation(), data);
                        }
                    }
                }
            };
            Tasks.repeat(runnable, 1);
        });
    }
}