package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.object.queue.internal.BlockMap;
import com.efnilite.redaktor.object.queue.types.CopyQueue;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import com.efnilite.redaktor.object.selection.Dimensions;
import com.efnilite.redaktor.schematic.internal.BlockIndex;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Schematic {

    private Gson gson;
    private CuboidSelection cuboid;
    private String file;
    @Expose
    private Dimensions dimensions;

    public Schematic(String file) {
        this.cuboid = null;
        this.file = file;
        this.dimensions = null;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    public Schematic(CuboidSelection cuboid) {
        this.cuboid = cuboid;
        this.file = null;
        this.dimensions = cuboid.getDimensions();
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Returns the Dimensions of a Schematic
     *
     * @see     Dimensions
     *
     * @return  The Dimensions of a Schematic
     *
     * @throws  IOException
     *          For any exceptions during the reading,
     *          this is targeted to the user so they can choose what to do
     *          with the error.
     */
    public Dimensions getDimensions() throws IOException {
        if (file != null) {
            FileReader reader = new FileReader(file);
            return gson.fromJson(reader, Dimensions.class);
        } else {
            throw new IllegalArgumentException("File can't be null!");
        }
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
            new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<WritableBlock> map = new ArrayList<>();
                for (Block block : l.keySet()) {
                    BlockIndex index = l.get(block
                    );
                    map.add(new WritableBlock(block, index.getX(), index.getY(), index.getZ()));
                }
                gson.toJson(dimensions, writer);
                gson.toJson(map, writer);
                try {
                    writer.close();
                } catch (IOException | StackOverflowError e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("CuboidSelection can't be null to save!");
        }
    }

    /**
     * Pastes a Schematic at a location.
     *
     * @param   at
     *          Where the location should be placed.
     *
     * @return  The CuboidSelection version of the pasted Schematic.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose
     *          what to do with the error.
     */
    public CuboidSelection paste(Location at) throws IOException {
        if (file != null) {
            JsonReader reader = new JsonReader(new FileReader(file));
            Type type = new TypeToken<List<WritableBlock>>() {}.getType();
            List<WritableBlock> writableBlocks = gson.fromJson(reader, type);
            List<BlockMap> blocks = new ArrayList<>();
            for (WritableBlock block : writableBlocks) {
                Location current = at.clone();
                current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point
                blocks.add(new BlockMap(current.getBlock()));
            }
            CopyQueue blockQueue = new CopyQueue();
            blockQueue.build(blocks);
            Dimensions dimensions = gson.fromJson(reader, Dimensions.class);
            return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
        } else {
            throw new IllegalArgumentException("File can't be null to save!");
        }
    }
}