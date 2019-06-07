package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for getting connected blocks from a specific location.
 *
 * Idea by https://gitlab.com/Moderocky
 */
public class AsyncConnectedGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    private int maxCount;
    private Location pos;
    private Consumer<List<Block>> consumer;

    public AsyncConnectedGetter(Location pos, int maxCount, Consumer<List<Block>> consumer) {
        this.maxCount = maxCount;
        this.pos = pos;
        this.consumer = consumer;

        Tasks.async(this);
    }

    @Override
    public void run() {
        List<Block> blocks = new ArrayList<>();
        Location loc = pos.clone();
        for (int i = 1; i < maxCount; i++) {
            boolean con = true;

            for (int x = loc.getBlockX(); x <= 1; x++) {
                for (int y = loc.getBlockY(); y <= 1; y++) {
                    for (int z = loc.getBlockZ(); z <= 1; z++) {
                        loc.setX(x);
                        loc.setY(y);
                        loc.setZ(z);

                        if (loc.getBlock().getType() == pos.getBlock().getType()) {
                            blocks.add(loc.getBlock());
                            con = false;
                        }
                    }
                }
            }
            if (con) {
                asyncDone(blocks);
                return;
            }
        }
    }

    @Override
    public void asyncDone(List<Block> value) {
        consumer.accept(value);
    }
}