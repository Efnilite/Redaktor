package com.efnilite.redaktor.object.schematic;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.SettableBlockMap;
import com.efnilite.redaktor.object.queue.types.SingleBlockQueue;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Schematic {

    private Gson gson;
    private Cuboid cuboid;
    private String file;

    public Schematic(String file) {
        this.cuboid = null;
        this.file = file;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Schematic(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.file = null;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save(String file) throws IOException {
        if (cuboid != null) {
            FileWriter writer = new FileWriter(file);
            new AsyncBlockGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<SettableBlockMap> map = new ArrayList<>();
                for (Block block : l) {
                    map.add(new SettableBlockMap(block));
                }
                gson.toJson(map, writer);
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalStateException("Cuboid can't be null to save!");
        }
    }

    public void paste(Location at) throws IOException {
        if (file != null) {
            JsonReader reader = new JsonReader(new FileReader(file));
            WritableBlock[] writableBlocks = gson.fromJson(reader, WritableBlock[].class);
            List<SettableBlockMap> blocks = new ArrayList<>();
            for (WritableBlock block : writableBlocks) {
                Location current = at.clone();
                current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point
                blocks.add(new SettableBlockMap(current.getBlock(), block.getMaterial(), block.getData()));
            }
            SingleBlockQueue blockQueue = new SingleBlockQueue();
            blockQueue.build(blocks);
        } else {
            throw new IllegalStateException("File can't be null to save!");
        }
    }
}
