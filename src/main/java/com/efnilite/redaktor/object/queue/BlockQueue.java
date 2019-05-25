package com.efnilite.redaktor.object.queue;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BlockQueue implements EditQueue {

    private long time;
    private Queue<Block> blocks;

    public Queue<Block> build(List<Block> blocks) {
        this.time = System.currentTimeMillis();

        Queue<Block> queue = new LinkedList<>(blocks);

        this.blocks = queue;
        return queue;
    }

    public void run(Material material) {
        int size = blocks.size();
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                    if (blocks.peek() == null) {
                        Redaktor.getInstance().getLogger().info("Set " + size + " blocks in " + ((System.currentTimeMillis() - time) / 1000) + "s.");
                        this.cancel();
                        return;
                    }

                    blocks.poll().setType(material);
                }
            }
        };
        Tasks.repeat(runnable, 1);
    }
}