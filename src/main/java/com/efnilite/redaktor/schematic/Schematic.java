package com.efnilite.redaktor.schematic;

import com.efnilite.redaktor.event.SchematicPasteEvent;
import com.efnilite.redaktor.event.SchematicSaveEvent;
import com.efnilite.redaktor.schematic.io.SchematicReader;
import com.efnilite.redaktor.schematic.io.SchematicWriter;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.selection.Selection;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;

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
     * The selection
     */
    private Selection selection;

    /**
     * The dimensions
     */
    private Dimensions dimensions;

    /**
     * Creates a new instance from a file
     *
     * @param   file
     *          The file
     */
    public Schematic(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
        this.selection = null;
        this.gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Creates a new instance from a {@link Selection}
     *
     * @param   selection
     *          The selection
     */
    public Schematic(Selection selection) {
        this.file = null;
        this.selection = selection;
        this.dimensions = selection.getDimensions();
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
     * @param   options
     *          The options for saving.
     */
    public void save(String file, SaveOptions... options) {
        if (selection != null) {
            Bukkit.getPluginManager().callEvent(new SchematicSaveEvent(this, file));

            SchematicWriter writer = new SchematicWriter(file);
            new AsyncBlockGetter(selection, l -> {
                try {
                    writer.write(l, selection.getDimensions(), options);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            throw new IllegalArgumentException("Cuboid can't be null to save!");
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
            Bukkit.getPluginManager().callEvent(new SchematicPasteEvent(this, at, file));

            SchematicReader reader = new SchematicReader(file);
            SchematicReader.ReaderReturn readerReturn = reader.read();
            Dimensions dimensions = readerReturn.getDimensions();
            SchematicQueue queue = new SchematicQueue(at);
            queue.build(readerReturn);
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

                SchematicReader reader = new SchematicReader(file);
                SchematicReader.ReaderReturn readerReturn = reader.read();
                Dimensions dimensions = readerReturn.getDimensions();
                List<BlockData> update = new ArrayList<>();
                SchematicQueue queue = new SchematicQueue(at);

                for (BlockData block : readerReturn.getData()) {
                    String data = block.getAsString();
                    Pattern pattern = Pattern.compile("facing=(\\w+)");

                    Facing facing;
                    Matcher matcher = pattern.matcher(data);
                    if (!matcher.find()) {
                        update.add(Bukkit.createBlockData(data));
                        continue;
                    }
                    String group = matcher.group().replaceAll("facing=", "");
                    facing = Facing.getFromBlockFace(BlockFace.valueOf(group.toUpperCase())).getFaceFromAngle(angle);

                    data = data.replaceAll(pattern.toString(), "facing=" + facing.getString());

                    update.add(Bukkit.createBlockData(data));
                }

                readerReturn.setData(update);
                queue.build(readerReturn);
                return new CuboidSelection(dimensions.getMaximumPoint(), dimensions.getMinimumPoint());
            } else {
                throw new IllegalArgumentException("Angle must be divisible by 90!");
            }
        } else {
            throw new IllegalArgumentException("File can't be null to save!");
        }
    }

    /**
     * Returns the selection of a schematic
     *
     * @return  The selection of a schematic
     *
     * @throws  IOException
     *          When something goes wrong with the reading
     */
    public CuboidSelection getCuboidSelection() throws IOException {
        return getDimensions().getSelection();
    }

    /**
     * Returns the dimensions of a schematic
     *
     * @return  The dimensions of a schematic
     *
     * @throws  IOException
     *          When something goes wrong with the reading
     */
    public Dimensions getDimensions() throws IOException {
        if (file != null) {
            SchematicReader reader = new SchematicReader(file);
            return reader.getDimensions();
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
     * An enum for saving options
     */
    public enum SaveOptions {

        /**
         * If the schematic should not save air.
         * <p>
         * This means that only solid blocks will be placed in the area where the schematic will be placed,
         * so if there's an air block in the schematic and there's a normal block in the current world the
         * block in the current world will not be set to air.
         */
        SKIP_AIR,

        /**
         * If the schematic should save the biome the blocks are in
         */
        //SAVE_BIOMES

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
}