package com.efnilite.redaktor.object.schematic;

import com.efnilite.redaktor.object.queue.SettableBlockMap;
import com.efnilite.redaktor.object.queue.SingleBlockQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Schematic {

    private Gson gson;
    private String file;

    public Schematic() {
        this.file = "";
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public Schematic(String file) {
        this.file = file;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public void save(WritableBlock[] blocks) throws IOException {
        FileWriter writer = new FileWriter(file);
        gson.toJson(blocks, writer);
        writer.close();
    }

    public void save(String file, WritableBlock[] blocks) throws IOException {
        FileWriter writer = new FileWriter(file);
        gson.toJson(blocks, writer);
        writer.close();
    }

    public void paste(String file, Location at) throws IOException {
        JsonReader reader = new JsonReader(new FileReader(file));
        WritableBlock[] writableBlocks = gson.fromJson(reader, WritableBlock[].class);
        List<SettableBlockMap> blocks = new ArrayList<>();
        for (WritableBlock block : writableBlocks) {
            Location current = at.clone();
            current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point
            blocks.add(new SettableBlockMap(current.getBlock(), block.getMaterial(), block.getData()));
        }
        SingleBlockQueue blockQueue = new SingleBlockQueue(blocks);
        blockQueue.build(blocks);
        blockQueue.run();
    }
}
