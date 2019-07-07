package com.efnilite.redaktor.selection.internal;

import com.efnilite.redaktor.queue.internal.BlockMap;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Selection;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;

/**
 * A selection that stores which types of blocks it contains.
 *
 * @see com.efnilite.redaktor.Editor
 */
public class HistorySelection implements Selection {

    private World world;
    private Location pos1;
    private Location pos2;
    private List<BlockMap> map;

    public HistorySelection(Location pos1, Location pos2, World world) {
        this.world = world;
        this.pos1 = pos1;
        this.pos2 = pos2;

        new AsyncBlockGetter(pos1, pos2, t -> {
            for (Block block : t) {
                map.add(new BlockMap(block, block.getBlockData()));
            }
        });
    }

    public CuboidSelection toCuboid() {
        return new CuboidSelection(pos1, pos2, world);
    }

    public List<BlockMap> getBlockMap() {
        return map;
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public World getWorld() {
        return world;
    }
}
