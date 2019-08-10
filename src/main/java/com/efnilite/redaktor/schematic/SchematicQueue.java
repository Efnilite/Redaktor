package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.queue.BlockMap;
import com.efnilite.redaktor.queue.EditQueue;
import com.efnilite.redaktor.queue.types.CopyQueue;
import com.efnilite.redaktor.schematic.io.SchematicReader;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.Tasks;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SchematicQueue implements EditQueue<SchematicReader.ReaderReturn> {

    @Override
    public void build(SchematicReader.ReaderReturn readerReturn) {
        Dimensions dimensions = readerReturn.getDimensions();
        List<BlockData> data = readerReturn.getData();

        CuboidSelection cuboid = new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
        List<BlockMap> blocks = new ArrayList<>(); // Temp use of BlockMap
        Location max = cuboid.getMaximumPoint();
        Location min = cuboid.getMinimumPoint();
        Location loc = cuboid.getMinimumPoint();
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                int index = 0;
                for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
                    for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                        for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                            loc.setX(x);
                            loc.setY(y);
                            loc.setZ(z);

                            blocks.add(new BlockMap(loc.getBlock(), data.get(index)));
                            index++;
                        }
                    }
                }
                done(blocks);
            }
        };
        Tasks.async(runnable);

        /*BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                    if (queue.peek() == null) {
                        this.cancel();
                        return;
                    }

                    BlockMap map = queue.poll();
                    if (!map.getData().getAsString().equals(map.getBlock().getBlockData().getAsString())) {
                        factory.setBlock(map.getBlock().getLocation(), map.getData());
                    }
                }
            }
        };
        Tasks.repeat(runnable, 1);*/
    }

    private void done(List<BlockMap> map) {
        CopyQueue queue = new CopyQueue();
        queue.build(map);
    }
}