package com.efnilite.redaktor.object.queue.types;

import com.efnilite.redaktor.object.queue.AbstractSlowQueue;
import com.efnilite.redaktor.object.queue.EditQueue;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SlowBlockQueue extends AbstractSlowQueue implements EditQueue<Block> {

    private Material material;

    public SlowBlockQueue(int perTick, Material material) {
        super(perTick);
        this.material = material;
    }

    public void build(List<Block> blocks) {
        Queue<Block> queue = new LinkedList<>(blocks);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < perTick; i++) {
                    if (queue.peek() == null) {
                        this.cancel();
                        return;
                    }

                    queue.poll().setType(material);
                }
            }
        };
        Tasks.repeat(runnable, 1);
    }
}

