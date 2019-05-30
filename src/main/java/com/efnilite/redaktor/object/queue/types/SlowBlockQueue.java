package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.AbstractSlowQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
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
public class SlowBlockQueue extends AbstractSlowQueue implements EditQueue<Cuboid> {

    private Material material;

    public SlowBlockQueue(int perTick, Material material) {
        super(perTick);
        this.material = material;
    }

    @Override
    public void build(Cuboid cuboid) {
        new AsyncBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), t -> {
            Queue<Block> queue = new LinkedList<>();

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < perTick; i++) {
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

