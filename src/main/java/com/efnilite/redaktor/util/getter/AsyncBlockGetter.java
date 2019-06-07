package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for getting blocks async in a cube.
 */
public class AsyncBlockGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    private Location pos1;
    private Location pos2;
    private Consumer<List<Block>> consumer;

    public AsyncBlockGetter(Location pos1, Location pos2, Consumer<List<Block>> consumer) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.consumer = consumer;

        Tasks.async(this);
    }

    @Override
    public void run() {
        World w = pos1.getWorld();
        List<Block> blocks = new ArrayList<>();
        int maxX = Math.max(pos1.getBlockX(), pos2.getBlockX());
        int minX = Math.min(pos1.getBlockX(), pos2.getBlockX());
        int maxY = Math.max(pos1.getBlockY(), pos2.getBlockY());
        int minY = Math.min(pos1.getBlockY(), pos2.getBlockY());
        int maxZ = Math.max(pos1.getBlockZ(), pos2.getBlockZ());
        int minZ = Math.min(pos1.getBlockZ(), pos2.getBlockZ());
        Location loc = new Location(w, 0, 0, 0);
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    loc.setX(x);
                    loc.setY(y);
                    loc.setZ(z);

                    blocks.add(loc.getBlock());
                }
            }
        }
        asyncDone(blocks);
    }

    @Override
    public void asyncDone(List<Block> value) {
        consumer.accept(value);
    }
}