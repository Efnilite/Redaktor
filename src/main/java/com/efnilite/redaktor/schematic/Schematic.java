package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.queue.internal.BlockMap;
import com.efnilite.redaktor.queue.types.CopyQueue;
import com.efnilite.redaktor.schematic.internal.BlockIndex;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    public Schematic(CuboidSelection cuboid) {
        this.cuboid = cuboid;
        this.file = null;
        this.dimensions = cuboid.getDimensions();
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
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
                WritableSchematic schematic = new WritableSchematic(map);
                gson.toJson(dimensions, writer);
                gson.toJson(schematic, writer);
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
            WritableSchematic schematic = gson.fromJson(reader, WritableSchematic.class);
            List<WritableBlock> writableBlocks = schematic.getBlocks();
            List<BlockMap> blocks = new ArrayList<>();
            for (WritableBlock block : writableBlocks) {
                Location current = at.clone();
                current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point
                blocks.add(new BlockMap(current.getBlock(), Bukkit.createBlockData(block.getData())));
            }
            CopyQueue blockQueue = new CopyQueue();
            blockQueue.build(blocks);
            Dimensions dimensions = gson.fromJson(reader, Dimensions.class);
            return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
        } else {
            throw new IllegalArgumentException("File can't be null to save!");
        }
    }

    /**
     * Pastes a Schematic at a location.
     *
     * @param   at
     *          Where the location should be placed.
     *
     * @param   angle
     *          The angle the schematic will be pasted at.
     *          Must be divisible by 90, since you know.. Minecraft is square..
     *
     * @return  The CuboidSelection version of the pasted Schematic.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose
     *          what to do with the error.
     */
    public CuboidSelection paste(Location at, int angle) throws IOException {
        if (file != null) {
            if (angle % 90 == 0) {
                JsonReader reader = new JsonReader(new FileReader(file));
                WritableSchematic schematic = gson.fromJson(reader, WritableSchematic.class);
                List<WritableBlock> writableBlocks = schematic.getBlocks();
                List<BlockMap> blocks = new ArrayList<>();
                for (WritableBlock block : writableBlocks) {
                    Location current = at.clone();
                    current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point

                    String data = block.getData();
                    Pattern pattern = Pattern.compile("facing=(\\w+)");

                    Facing facing;
                    Matcher matcher = pattern.matcher(data);
                    matcher.find();
                    String group = matcher.group().replaceAll("facing=", "");
                    facing = Facing.getFromBlockFace(BlockFace.valueOf(group.toUpperCase())).getFaceFromAngle(angle);

                    data = data.replaceAll(pattern.toString(), "facing=" + facing.getString());

                    blocks.add(new BlockMap(current.getBlock(), Bukkit.createBlockData(data)));
                }
                CopyQueue blockQueue = new CopyQueue();
                blockQueue.build(blocks);
                Dimensions dimensions = gson.fromJson(reader, Dimensions.class);
                return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
            } else {
                throw new IllegalArgumentException("Angle must be divisible by 90!");
            }
        } else {
            throw new IllegalArgumentException("File can't be null to save!");
        }
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

    public enum Facing {

        NORTH {
            @Override
            public String getString() {
                return "north";
            }
        },
        WEST {
            @Override
            public String getString() {
                return "west";
            }
        },
        SOUTH {
            @Override
            public String getString() {
                return "south";
            }
        },
        EAST {
            @Override
            public String getString() {
                return "east";
            }
        };

        public abstract String getString();

        public static Facing getFromBlockFace(BlockFace face) {
            switch (face) {
                case WEST:
                    return WEST;
                case SOUTH:
                    return SOUTH;
                case EAST:
                    return EAST;
                default:
                    return NORTH;
            }
        }

        public Facing getFaceFromAngle(int angle) {
            switch (this) {
                case NORTH:
                    if (angle % 270 == 0) {
                        return EAST;
                    } else if (angle % 180 == 0) {
                        return SOUTH;
                    } else if (angle % 90 == 0) {
                        return WEST;
                    } else {
                        return NORTH;
                    }
                case WEST:
                    if (angle % 270 == 0) {
                        return NORTH;
                    } else if (angle % 180 == 0) {
                        return EAST;
                    } else if (angle % 90 == 0) {
                        return SOUTH;
                    } else {
                        return WEST;
                    }
                case SOUTH:
                    if (angle % 270 == 0) {
                        return WEST;
                    } else if (angle % 180 == 0) {
                        return NORTH;
                    } else if (angle % 90 == 0) {
                        return EAST;
                    } else {
                        return SOUTH;
                    }
                case EAST:
                    if (angle % 270 == 0) {
                        return SOUTH;
                    } else if (angle % 180 == 0) {
                        return WEST;
                    } else if (angle % 90 == 0) {
                        return NORTH;
                    } else {
                        return EAST;
                    }
                default:
                    return NORTH;
            }
        }
    }

    public class WritableSchematic {

        @Expose
        private List<WritableBlock> blocks;

        public WritableSchematic(List<WritableBlock> blocks) {
            this.blocks = blocks;
        }

        public List<WritableBlock> getBlocks() {
            return blocks;
        }
    }
}