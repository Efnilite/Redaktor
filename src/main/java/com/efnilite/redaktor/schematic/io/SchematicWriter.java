package com.efnilite.redaktor.schematic.io;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.selection.Dimensions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * A class for writing schematics (i/o)
 */
public class SchematicWriter {

    /**
     * The file
     */
    private String file;

    /**
     * Creates a new instance
     *
     * @param   file
     *          The file
     */
    public SchematicWriter(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
    }

    /**
     * Writes a {@link Schematic} to a file
     *
     * @param   blocks
     *          The blocks of the schematic
     *
     * @param   options
     *          The saving options
     *
     * @throws  IOException
     *          If something goes wrong
     */
    public void write(List<Block> blocks, Dimensions dimensions, Schematic.SaveOptions... options) throws IOException {
        List<Schematic.SaveOptions> saveOptions = Arrays.asList(options);
        FileWriter writer = new FileWriter(file);
        HashSet<String> filter = new HashSet<>();
        HashMap<String, Integer> palette = new HashMap<>();
        String separator = System.lineSeparator();

        writer.write(Redaktor.SCHEMATIC_VERSION + separator);
        writer.write(dimensions.toString() + separator);

        writer.write("*");
        if (saveOptions.contains(Schematic.SaveOptions.SKIP_AIR)) {
            writer.write("#" + separator);
        }
        writer.write(separator);

        blocks.forEach(block -> filter.add(block.getBlockData().getAsString()));

        int index = 0;
        for (String data : filter) {
            palette.put(data, index);
            writer.write(index + " > " + data + separator);
            index++;
        }

        writer.write("~" + separator);

        int counter = 1;
        BlockData previous = null;
        List<Block> mutable = new ArrayList<>(blocks);
        for (Block block : blocks) {
            mutable.remove(block);
            BlockData data = block.getBlockData();

            String id = Integer.toString(palette.get(block.getBlockData().getAsString()));
            String blockData = data.getAsString();
            String parsed = blockData.replaceAll(".+", id);

            if (blockData.contains("[")) {
                parsed = blockData.replaceAll(".+\\[.+]", id);
            }

            if (previous != null) {
                if (data.getAsString().equals(previous.getAsString())) {
                    counter++;
                    if (mutable.size() == 0) {
                        parsed += "x" + counter;
                        writer.write(parsed);
                    }
                } else {
                    if (counter != 1) {
                        parsed += "x" + counter + ",";
                    }
                    writer.write(parsed);
                    counter = 1;
                }
            }

            previous = data;
        }

        writer.close();
    }

    /**
     * Gets the file
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }
}
