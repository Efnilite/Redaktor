package com.efnilite.redaktor.object.schematic;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.cuboid.Dimensions;
import com.efnilite.redaktor.object.queue.SettableBlockMap;
import com.efnilite.redaktor.object.queue.types.SingleBlockQueue;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Schematic {

    private Gson gson;
    private Cuboid cuboid;
    private String file;
    private Dimensions dimensions;

    public Schematic(String file) {
        this.cuboid = null;
        this.file = file;
        this.dimensions = null;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Schematic(Cuboid cuboid) {
        this.cuboid = cuboid;
        this.file = null;
        this.dimensions = cuboid.getDimensions();
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Saves a Schematic to a file using a String.
     *
     * @param   file
     *          The file path where the Schematic should be saved.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose what to do
     *          with the error.
     */
    public void save(String file) throws IOException {
        if (cuboid != null) {
            FileWriter writer = new FileWriter(file);
            new AsyncBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), l -> {
                List<SettableBlockMap> map = new ArrayList<>();
                for (Block block : l) {
                    map.add(new SettableBlockMap(block));
                }
                gson.toJson(dimensions, writer);
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

    /**
     * Saves a Schematic using an OutputStreamWriter.
     *
     * For saving from a String, {@link #save(String)}
     *
     * @param   file
     *          The file stream where the file should be saved.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose
     *          what to do with the error.
     */
    public void save(OutputStreamWriter file) throws IOException {
        if (cuboid != null) {
            new AsyncBlockGetter(cuboid.getMaximumPoint(), cuboid.getMinimumPoint(), l -> {
                List<SettableBlockMap> map = new ArrayList<>();
                for (Block block : l) {
                    map.add(new SettableBlockMap(block));
                }
                gson.toJson(dimensions, file);
                gson.toJson(map, file);
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalStateException("Cuboid can't be null to save!");
        }
    }

    /**
     * Pastes a Schematic at a location.
     *
     * @param   at
     *          Where the location should be placed.
     *
     * @return  The Cuboid version of the pasted Schematic.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose
     *          what to do with the error.
     */
    public Cuboid paste(Location at) throws IOException {
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
            Dimensions dimensions = gson.fromJson(reader, Dimensions.class);
            return new Cuboid(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
        } else {
            throw new IllegalStateException("File can't be null to save!");
        }
    }
}
