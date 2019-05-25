package com.efnilite.redaktor;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.BlockQueue;
import com.efnilite.redaktor.util.ChangeAllocator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Editor {

    private Plugin plugin;
    private ChangeAllocator allocator;

    Editor() {
        this.plugin = Redaktor.getInstance();
        this.allocator = Redaktor.getAllocator();
    }

    public int setBlocks(Material material, List<Block> locations) {
        BlockQueue blockQueue = new BlockQueue();
        blockQueue.build(locations);
        return locations.size();
    }

    public int setBlocks(Material material, Cuboid cuboid) {
        List<Block> locations = new ArrayList<>();
        cuboid.iterator().forEachRemaining(locations::add);
        return this.setBlocks(material, locations);
    }
}
