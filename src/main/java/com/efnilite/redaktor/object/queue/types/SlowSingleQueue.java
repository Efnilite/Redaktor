package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.object.queue.AbstractSlowQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.object.queue.SettableBlockMap;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * A queue for dramatic effect.
 * @see SingleBlockQueue
 */
public class SlowSingleQueue extends AbstractSlowQueue implements EditQueue<SettableBlockMap> {

    public SlowSingleQueue(int perTick) {
        super(perTick);
    }

    public void build(List<SettableBlockMap> blocks) {
        Queue<SettableBlockMap> queue = new LinkedList<>(blocks);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < perTick; i++) {
                    if (queue.peek() == null) {
                        this.cancel();
                        return;
                    }

                    SettableBlockMap map = queue.poll();
                    map.getBlock().setType(map.getMaterial());
                    if (map.getData() != null) {
                        map.getBlock().setBlockData(map.getData());
                    }
                }
            }
        };
        Tasks.repeat(runnable, 1);
    }
}

