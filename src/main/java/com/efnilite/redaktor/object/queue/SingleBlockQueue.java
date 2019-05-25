package com.efnilite.redaktor.object.queue;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

public class SingleBlockQueue implements EditQueue {

    private Queue<List<SettableBlockMap>> blocks;

    public Queue<List<SettableBlockMap>> build(List<SettableBlockMap> blocks) {
        Queue<List<SettableBlockMap>> queue = new LinkedList<>();

        for (int i = 0; i < (blocks.size() / Redaktor.getAllocator().getChanger()); i++) {
            List<SettableBlockMap> current = blocks.stream()
                    .limit(Redaktor.getAllocator().getChanger())
                    .collect(Collectors.toList());
            blocks.removeAll(current);
            queue.add(blocks);
        }
        this.blocks = queue;
        return queue;
    }

    public void run() {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                List<SettableBlockMap> current = blocks.poll();

                if (current == null) {
                    this.cancel();
                    return;
                }

                for (SettableBlockMap map : current) {
                    map.getBlock().setType(map.getMaterial());
                    map.getBlock().setBlockData(map.getData());
                }
            }
        };
        Tasks.repeat(runnable, 1);
    }

}
