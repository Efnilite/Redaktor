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
 * A slower queue for dramatic effect.
 *
 * @see BlockQueue
 */
public class SlowBlockQueue implements EditQueue<CuboidSelection> {

    private int tick;
    private Pattern pattern;

    public SlowBlockQueue(Pattern pattern, int tick) {
        this.tick = tick;
        this.pattern = pattern;
    }

    @Override
    public void build(CuboidSelection cuboid) {
        IBlockFactory factory = Redaktor.getBlockFactory();
        new AsyncBlockGetter(cuboid.getPos1(), cuboid.getPos2(), t -> {
            Queue<Block> queue = new LinkedList<>(t);
            int count = queue.size();

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < tick; i++) {
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