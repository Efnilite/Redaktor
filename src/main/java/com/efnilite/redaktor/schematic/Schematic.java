package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.event.SchematicPasteEvent;
import com.efnilite.redaktor.queue.BlockMap;
import com.efnilite.redaktor.queue.types.CopyQueue;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class that handles everything with schematics
 */
public class Schematic {

    /**
     * The gson instance used for reading/writing to JSON files
     */
    private Gson gson;

    /**
     * The file
     */
    private String file;

    /**
     * The CuboidSelection
     */
    private CuboidSelection cuboid;

    /**
     * The dimensions
     */
    private WritableDimensions dimensions;

    /**
     * Creates a new instance from a file
     *
     * @param   file
     *          The file
     */
    public Schematic(String file) {
        this.cuboid = null;
        this.file = file.endsWith(".json") ? file : file + ".json";
        this.dimensions = null;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Creates a new instance from a {@link CuboidSelection}
     *
     * @param   cuboid
     *          The CuboidSelection
     */
    public Schematic(CuboidSelection cuboid) {
        this.file = null;
        this.cuboid = cuboid;
        this.dimensions = this.fromStandardDimensions(cuboid.getDimensions());
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
        /*if (cuboid != null) {
            Bukkit.getPluginManager().callEvent(new SchematicSaveEvent(this, file));

            FileWriter writer = new FileWriter(file.endsWith(".json") ? file : file + ".json");
            new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<WritableBlock> blocks = new ArrayList<>();
                for (Block block : l.keySet()) {
                    BlockIndex index = l.get(block);
                    blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                }
                WritableSchematic schematic = new WritableSchematic(blocks, dimensions);
                gson.toJson(schematic, writer);
                try {
                    writer.close();
                } catch (IOException | StackOverflowError e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Cuboid can't be null to save!");
        }*/
    }


    /**
     * Saves a Schematic to a file using a String.
     *
     * @param   file
     *          The file path where the Schematic should be saved.
     *
     * @param   options
     *          The options for saving.
     *
     * @throws  IOException
     *          For any exceptions during the saving,
     *          this is targeted to the user so they can choose what to do
     *          with the error.
     */
    public void save(String file, SaveOptions... options) throws IOException {
        /*if (cuboid != null) {
            Bukkit.getPluginManager().callEvent(new SchematicSaveEvent(this, file));

            List<SaveOptions> saveOptions = Arrays.asList(options);
            FileWriter writer = new FileWriter(file.endsWith(".json") ? file : file + ".json");
            new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<WritableBlock> blocks = new ArrayList<>();
                for (Block block : l.keySet()) {
                    BlockIndex index = l.get(block);
                    if (saveOptions.contains(SaveOptions.SKIP_AIR)) {
                        if (block.getType() != Material.AIR) {
                            blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                        }
                    } else {
                        blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                    }
                }

                WritableSchematic schematic = new WritableSchematic(blocks, dimensions);
                gson.toJson(schematic, writer);
                try {
                    writer.close();
                } catch (IOException | StackOverflowError e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Cuboid can't be null to save!");
        }*/
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
            Bukkit.getPluginManager().callEvent(new SchematicPasteEvent(this, at, file));

            JsonReader reader = new JsonReader(new FileReader(file.endsWith(".json") ? file : file + ".json"));
            WritableSchematic schematic = gson.fromJson(reader, WritableSchematic.class);
            Dimensions dimensions = toStandardDimensions(schematic.getDimensions());
            List<WritableBlock> writableBlocks = schematic.getBlocks();
            List<BlockMap> blocks = new ArrayList<>();
            for (WritableBlock block : writableBlocks) {
                Location current = at.clone();
                current.add(block.getX(), block.getY(), block.getZ()); // to offset from original point
                blocks.add(new BlockMap(current.getBlock(), Bukkit.createBlockData(block.getData())));
            }
            CopyQueue blockQueue = new CopyQueue();
            blockQueue.build(blocks);
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
                Bukkit.getPluginManager().callEvent(new SchematicPasteEvent(this, at, file));

                JsonReader reader = new JsonReader(new FileReader(file));
                WritableSchematic schematic = gson.fromJson(reader, WritableSchematic.class);
                Dimensions dimensions = toStandardDimensions(schematic.getDimensions());
                List<WritableBlock> writableBlocks = schematic.getBlocks();
                List<BlockMap> blocks = new ArrayList<>();
                for (WritableBlock block : writableBlocks) {
                    Location current = at.clone();
                    current.add(block.getX(), block.getY(), block.getZ()); // to offset from original point

                    String data = block.getData();
                    Pattern pattern = Pattern.compile("facing=(\\w+)");

                    Facing facing;
                    Matcher matcher = pattern.matcher(data);
                    if (!matcher.find()) {
                        blocks.add(new BlockMap(current.getBlock(), Bukkit.createBlockData(data)));
                        continue;
                    }
                    String group = matcher.group().replaceAll("facing=", "");
                    facing = Facing.getFromBlockFace(BlockFace.valueOf(group.toUpperCase())).getFaceFromAngle(angle);

                    data = data.replaceAll(pattern.toString(), "facing=" + facing.getString());

                    blocks.add(new BlockMap(current.getBlock(), Bukkit.createBlockData(data)));
                }
                CopyQueue blockQueue = new CopyQueue();
                blockQueue.build(blocks);
                return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
            } else {
                throw new IllegalArgumentException("Angle must be divisible by 90!");
            }
        } else {
            throw new IllegalArgumentException("File can't be null to save!");
        }
    }

    /**
     * Returns the CuboidSelection of a Schematic
     *
     * @see     CuboidSelection
     *
     * @return  The CuboidSelection of a Schematic
     *
     * @throws  IOException
     *          For any exceptions during the reading,
     *          this is targeted to the user so they can choose what to do
     *          with the error.
     */
    public CuboidSelection getCuboidSelection() throws IOException {
        if (file != null) {
            FileReader reader = new FileReader(file);
            Dimensions dimensions = toStandardDimensions(gson.fromJson(reader, WritableSchematic.class).getDimensions());
            return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
        } else {
            throw new IllegalArgumentException("File can't be null!");
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
            return toStandardDimensions(gson.fromJson(reader, WritableSchematic.class).getDimensions());
        } else {
            throw new IllegalArgumentException("File can't be null!");
        }
    }

    /**
     * Gets the file
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * Turns WritableDimensions to normal Dimensions
     *
     * @param   dimensions
     *          The {@link WritableDimensions}
     *
     * @return  a standard {@link Dimensions} instance
     */
    private Dimensions toStandardDimensions(WritableDimensions dimensions) {
        return new Dimensions(new CuboidSelection(Util.fromDeserializableString(dimensions.getMaximum()),
                Util.fromDeserializableString(dimensions.getMinumum())));
    }

    /**
     * Turns Dimensions into WritableDimensions usable for saving
     *
     * @param   dimensions
     *          The {@link Dimensions}
     *
     * @return  a new {@link WritableDimensions} instance
     */
    private WritableDimensions fromStandardDimensions(Dimensions dimensions) {
        return new WritableDimensions(Util.toDeserializableString(dimensions.getMaximumPoint()),
                Util.toDeserializableString(dimensions.getMinimumPoint()), dimensions.getWidth(),
                dimensions.getHeight(), dimensions.getLength());
    }

    /**
     * An enum for saving options
     */
    public enum SaveOptions {

        /**
         * If the schematic should not save air.
         * <p>
         * This means that only solid blocks will be placed in the area where the schematic will be placed,
         * so if there's an air block in the schematic and there's a normal block in the current world the
         * block in the current world will not be set to air.
         * <p>
         * This option also saves a lot of storage space, since in a lot of schematics, most blocks are
         * air.
         */
        SKIP_AIR,

    }

    /**
     * A class for calculating faces and angles
     *
     * All classes below this one are used for writing data to json files using gson, and they have
     * no user value.
     */
    private enum Facing {

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

    private class WritableDimensions {

        @Expose
        private int width;
        @Expose
        private int height;
        @Expose
        private int length;
        @Expose
        private String maximum;
        @Expose
        private String minumum;

        public WritableDimensions(String maximum, String minumum, int width, int height, int length) {
            this.width = width;
            this.height = height;
            this.length = length;
            this.maximum = maximum;
            this.minumum = minumum;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getLength() {
            return length;
        }

        public String getMaximum() {
            return maximum;
        }

        public String getMinumum() {
            return minumum;
        }
    }

    private class WritableBlock {

        @Expose
        private String data;
        @Expose
        private int x;
        @Expose
        private int y;
        @Expose
        private int z;

        public WritableBlock(String data, int x, int y, int z) {
            this.data = data.replaceAll("minecraft:", "");
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public String getData() {
            return data;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }
    }

    private class WritableEntity {

        @Expose
        private EntityType type;
        @Expose
        private double x;
        @Expose
        private double y;
        @Expose
        private double z;

        public WritableEntity(EntityType type, double x, double y, double z) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public EntityType getType() {
            return type;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }
    }

    private class WritableSchematic {

        @Expose
        private WritableDimensions dimensions;
        @Expose
        private List<WritableBlock> blocks;

        public WritableSchematic(List<WritableBlock> blocks, WritableDimensions dimensions) {
            this.dimensions = dimensions;
            this.blocks = blocks;
        }

        public WritableDimensions getDimensions() {
            return dimensions;
        }

        public List<WritableBlock> getBlocks() {
            return blocks;
        }
    }
}