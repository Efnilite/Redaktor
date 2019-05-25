package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.object.queue.BlockQueue;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BlockGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    private BlockQueue queue;
    private Location pos1;
    private Location pos2;

    public BlockGetter(Location pos1, Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;

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
    public void asyncDone(List<Block> done) {
        queue.build(done);
        queue.run(Material.AIR);
    }

    public void setQueue(BlockQueue queue) {
        this.queue = queue;
    }
}
