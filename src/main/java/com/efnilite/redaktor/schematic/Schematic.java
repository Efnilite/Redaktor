package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.event.SchematicPasteEvent;
import com.efnilite.redaktor.event.SchematicSaveEvent;
import com.efnilite.redaktor.queue.internal.BlockMap;
import com.efnilite.redaktor.queue.types.CopyQueue;
import com.efnilite.redaktor.schematic.internal.BlockIndex;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.util.getter.AsyncBlockIndexGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.stream.JsonReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;

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
    private WritableDimensions dimensions;

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

    public Schematic(CuboidSelection cuboid) {
        this.cuboid = cuboid;
        this.file = null;
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
        if (cuboid != null) {
            Bukkit.getPluginManager().callEvent(new SchematicSaveEvent(this, file));

            FileWriter writer = new FileWriter(file.endsWith(".json") ? file : file + ".json");
            new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<WritableBlock> blocks = new ArrayList<>();
                for (Block block : l.keySet()) {
                    BlockIndex index = l.get(block);
                    blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                }
                WritableSchematic schematic = new WritableSchematic(blocks, null, dimensions);
                gson.toJson(schematic, writer);
                try {
                    writer.close();
                } catch (IOException | StackOverflowError e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Cuboid can't be null to save!");
        }
    }

    /*
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
    /*
    public void save(String file, SaveOptions... options) throws IOException {
        if (cuboid != null) {
            Bukkit.getPluginManager().callEvent(new SchematicSaveEvent(this, file));

            List<SaveOptions> saveOptions = Arrays.asList(options);
            FileWriter writer = new FileWriter(file.endsWith(".json") ? file : file + ".json");
            new AsyncBlockIndexGetter(cuboid.getPos1(), cuboid.getPos2(), l -> {
                List<WritableBlock> blocks = new ArrayList<>();
                for (Block block : l.keySet()) {
                    BlockIndex index = l.get(block);
                    if (saveOptions.contains(SaveOptions.DONT_SAVE_AIR)) {
                        if (block.getType() != Material.AIR) {
                            blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                        }
                    } else {
                        blocks.add(new WritableBlock(block.getBlockData().getAsString(), index.getX(), index.getY(), index.getZ()));
                    }
                }

                List<WritableEntity> entities = new ArrayList<>();
                for (Entity entity : cuboid.getWorld().getEntities()) {
                    if (Util.isInArea(entity.getLocation(), cuboid.getPos1(), cuboid.getPos2())) {
                        Location location = entity.getLocation().subtract(cuboid.getMaximumPoint());
                        entities.add(new WritableEntity(entity.getType(), location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    }
                }

                WritableSchematic schematic = new WritableSchematic(blocks, entities, dimensions);
                gson.toJson(schematic, writer);
                try {
                    writer.close();
                } catch (IOException | StackOverflowError e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Cuboid can't be null to save!");
        }
    }*/

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
                current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point
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
                    current.add(block.getOffsetX(), block.getOffsetY(), block.getOffsetZ()); // to offset from original point

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

    private Dimensions toStandardDimensions(WritableDimensions dimensions) {
        return new Dimensions(new CuboidSelection(Util.fromDeserializableString(dimensions.getMaximum()),
                Util.fromDeserializableString(dimensions.getMinumum())));
    }

    private WritableDimensions fromStandardDimensions(Dimensions dimensions) {
        return new WritableDimensions(Util.toDeserializableString(dimensions.getMaximumPoint()),
                Util.toDeserializableString(dimensions.getMinimumPoint()), dimensions.getWidth(),
                dimensions.getHeight(), dimensions.getLength());
    }

    public enum SaveOptions {

        /**
         * If the schematic should save entities.
         */
        SAVE_ENTITIES,
        /**
         * If the schematic should not save air.
         */
        DONT_SAVE_AIR,

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
        private int offsetX;
        @Expose
        private int offsetY;
        @Expose
        private int offsetZ;

        public WritableBlock(String data, int offsetX, int offsetY, int offsetZ) {
            this.data = data;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
        }

        public String getData() {
            return data;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }

        public int getOffsetZ() {
            return offsetZ;
        }
    }

    private class WritableEntity {

        @Expose
        private EntityType type;
        @Expose
        private int offsetX;
        @Expose
        private int offsetY;
        @Expose
        private int offsetZ;

        public WritableEntity(EntityType type, int offsetX, int offsetY, int offsetZ) {
            this.type = type;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
        }

        public EntityType getType() {
            return type;
        }

        public int getOffsetX() {
            return offsetX;
        }

        public int getOffsetY() {
            return offsetY;
        }

        public int getOffsetZ() {
            return offsetZ;
        }
    }

    private class WritableSchematic {

        @Expose
        private WritableDimensions dimensions;
        @Expose
        private List<WritableBlock> blocks;
        @Expose
        private List<WritableEntity> entities;

        public WritableSchematic(List<WritableBlock> blocks, List<WritableEntity> entities, WritableDimensions dimensions) {
            this.dimensions = dimensions;
            this.blocks = blocks;
            this.entities = entities;
        }

        public List<WritableEntity> getEntities() {
            return entities;
        }

        public WritableDimensions getDimensions() {
            return dimensions;
        }

        public List<WritableBlock> getBlocks() {
            return blocks;
        }
    }
}