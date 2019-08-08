package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Selection;
import com.efnilite.redaktor.selection.SquareSelection;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for getting blocks async in a cube.
 */
public class AsyncBlockGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    /**
     * The selection
     */
    private Selection selection;

    /**
     * The consumer -> what to do when the blocks have been collected
     */
    private Consumer<List<Block>> consumer;

    /**
     * Creates a new instance
     *
     * @param   selection
     *          The selection
     *
     * @param   consumer
     *          What to do when the blocks have been gathered
     */
    public AsyncBlockGetter(Selection selection, Consumer<List<Block>> consumer) {
        this.selection = selection;
        this.consumer = consumer;

        Tasks.async(this);
    }

    @Override
    public void run() {
        if (selection instanceof CuboidSelection) {
            CuboidSelection cuboid = (CuboidSelection) selection;
            List<Block> blocks = new ArrayList<>();
            Location max = cuboid.getMaximumPoint();
            Location min = cuboid.getMinimumPoint();
            Location loc = Util.zero();
            loc.setWorld(selection.getWorld());
            for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                    for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                        loc.setX(x);
                        loc.setY(y);
                        loc.setZ(z);

                        blocks.add(loc.getBlock());
                    }
                }
            }
            asyncDone(blocks);
        } else if (selection instanceof SquareSelection) {
            SquareSelection square = (SquareSelection) selection;
            List<Block> blocks = new ArrayList<>();
            Location loc = Util.zero();
            loc.setWorld(selection.getWorld());
            for (CuboidSelection cuboid : square.getCuboids()) {
                Location max = cuboid.getMaximumPoint();
                Location min = cuboid.getMinimumPoint();
                for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                            loc.setX(x);
                            loc.setY(y);
                            loc.setZ(z);

                            blocks.add(loc.getBlock());
                        }
                    }
                }
            }
            asyncDone(blocks);
        } else {
            throw new IllegalArgumentException("Selection needs to be instance of CuboidSelection or SquareSelection");
        }
    }

    @Override
    public void asyncDone(List<Block> value) {
        consumer.accept(value);
    }
}