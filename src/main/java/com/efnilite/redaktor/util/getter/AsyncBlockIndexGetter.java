package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.schematic.BlockIndex;
import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.function.Consumer;

/**
 * A class for getting blocks in a cube including the index it is in.
 *
 * Used in
 *
 * @see com.efnilite.redaktor.schematic.Schematic
 */
public class AsyncBlockIndexGetter extends BukkitRunnable implements AsyncGetter<HashMap<Block, BlockIndex>> {

    /**
     * The first position
     */
    private Location pos1;

    /**
     * The second position
     */
    private Location pos2;

    /**
     * The consumer -> what to do when the blocks have been gathered
     */
    private Consumer<HashMap<Block, BlockIndex>> consumer;

    /**
     * Creates a new instance
     *
     * @param   pos1
     *          The first position
     *
     * @param   pos2
     *          The second position
     *
     * @param   consumer
     *          What to do when the blocks have been gathered
     */
    public AsyncBlockIndexGetter(Location pos1, Location pos2, Consumer<HashMap<Block, BlockIndex>> consumer) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.consumer = consumer;

        Tasks.async(this);
    }

    @Override
    public void run() {
        World w = pos1.getWorld();
        HashMap<Block, BlockIndex> blocks = new HashMap<>();
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

                    blocks.put(loc.getBlock(), new BlockIndex(x - minX, y - minY, z - minZ));
                }
            }
        }
        asyncDone(blocks);
    }

    @Override
    public void asyncDone(HashMap<Block, BlockIndex> value) {
        consumer.accept(value);
    }
}