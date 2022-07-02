package dev.efnilite.redaktor.schematic;

import dev.efnilite.redaktor.queue.BlockMap;
import dev.efnilite.redaktor.queue.EditQueue;
import dev.efnilite.redaktor.queue.types.CopyQueue;
import dev.efnilite.redaktor.schematic.io.SchematicReader;
import dev.efnilite.redaktor.selection.Dimensions;
import dev.efnilite.redaktor.util.Util;
import dev.efnilite.redaktor.util.getter.AsyncGetter;
import dev.efnilite.redaktor.util.thread.Tasks;
import org.bukkit.Location;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * A queue for pasting schematics
 */
public class SchematicQueue implements EditQueue<SchematicReader.ReaderReturn>, AsyncGetter<List<BlockMap>> {

    /**
     * The first position
     */
    private Location pos;

    /**
     * Creates a new instance
     *
     * @param   pos
     *          The first position
     */
    public SchematicQueue(Location pos) {
        this.pos = pos;
    }

    @Override
    public int build(SchematicReader.ReaderReturn readerReturn) {
        Dimensions dimensions = readerReturn.getDimensions();
        List<BlockData> data = readerReturn.getData();

        List<BlockMap> blocks = new ArrayList<>(); // Temp use of BlockMap
        Location max = Util.max(dimensions.getSelection().move(pos).getMaximumPoint(), pos);
        Location min = Util.min(dimensions.getSelection().move(pos).getMaximumPoint(), pos);
        Location loc = min.clone();
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

                            if (index == data.size()) {
                                continue;
                            }
                            blocks.add(new BlockMap(loc.getBlock(), data.get(index)));
                            index++;
                        }
                    }
                }
                asyncDone(blocks);
            }
        };
        Tasks.async(runnable);
        return 1;
    }

    @Override
    public void asyncDone(List<BlockMap> map) {
        CopyQueue queue = new CopyQueue();
        queue.build(map);
    }
}