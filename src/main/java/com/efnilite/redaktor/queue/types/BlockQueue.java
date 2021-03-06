package com.efnilite.redaktor.queue.types;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.selection.Selection;
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
public class BlockQueue implements EditQueue<Selection> {

    /**
     * The pattern
     */
    private Pattern pattern;

    /**
     * Creates a new instance
     *
     * @param   pattern
     *          The pattern the blocks are going to be set to
     */
    public BlockQueue(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public void build(Selection selection) {
        BlockFactory factory = Redaktor.getBlockFactory();
        new AsyncBlockGetter(selection, t -> {
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

                        if (data == null) {
                            return;
                        }

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