package com.efnilite.redaktor.object.queue;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockQueue implements EditQueue<Block> {

    private Material material;

    public void build(List<Block> blocks) {
        Queue<Block> queue = new LinkedList<>(blocks);

        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
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

    public BlockQueue setMaterial(Material material) {
        this.material = material;
        return this;
    }
}