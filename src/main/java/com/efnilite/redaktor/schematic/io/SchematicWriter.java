package com.efnilite.redaktor.schematic.io;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.schematic.Schematic;
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
    public void write(List<Block> blocks, Schematic.SaveOptions... options) throws IOException {
        StringJoiner joiner = new StringJoiner(",");
        List<Schematic.SaveOptions> saveOptions = Arrays.asList(options);
        FileWriter writer = new FileWriter(file);
        HashSet<String> filter = new HashSet<>();
        HashMap<String, Integer> palette = new HashMap<>();

        writer.write(Redaktor.SCHEMATIC_VERSION + System.lineSeparator());

        if (saveOptions.contains(Schematic.SaveOptions.SKIP_AIR)) {
            writer.write("#" + System.lineSeparator());
        }

        blocks.forEach(block -> filter.add(block.getBlockData().getAsString()));

        int index = 0;
        for (String data : filter) {
            palette.put(data, index);
            writer.write(index + " > " + data + System.lineSeparator());
            index++;
        }

        writer.write("-" + System.lineSeparator());

        int counter = 1;
        BlockData previous = null;
        List<Block> mutable = new ArrayList<>(blocks);
        for (Block block : blocks) {
            mutable.remove(block);
            BlockData data = block.getBlockData();

            String id = palette.get(block.getBlockData().getAsString()).toString();
            String blockData = data.getAsString().replaceAll("minecraft:", "");
            String parsed = blockData.replaceAll("\\w+", id);

            if (blockData.contains("[")) {
                parsed = blockData.replaceAll("\\w+\\[.*?]", id);
            }

            if (previous != null) {
                if (data.getAsString().equals(previous.getAsString())) {
                    counter++;
                    if (mutable.size() == 0) {
                        parsed += "x" + counter;
                        joiner.add(parsed);
                    }
                } else {
                    if (counter != 1) {
                        parsed += "x" + counter;
                    }
                    joiner.add(parsed);
                    counter = 1;
                }
            }

            previous = data;
        }

        writer.write(joiner.toString());
        writer.close();
    }

    // This is still work in progress
    /*public void write(List<Block> blocks, Schematic.SaveOptions... options) throws IOException {
        HashMap<String, Integer> palette = new HashMap<>();
        HashSet<String> filter = new HashSet<>();
        List<Schematic.SaveOptions> saveOptions = Arrays.asList(options);
        FileOutputStream output = new FileOutputStream(file);
        DataOutputStream stream = new DataOutputStream(new GZIPOutputStream(output));

        stream.writeInt(Redaktor.SCHEMATIC_VERSION);

        if (saveOptions.contains(Schematic.SaveOptions.SKIP_AIR)) {
            stream.writeUTF("#");
        }

        blocks.forEach(block -> filter.add(block.getBlockData().getAsString()));

        List<Integer> ints = new ArrayList<>();

        for (String data : filter) {
            palette.computeIfAbsent(data, k -> palette.size());
            ints.add(palette.get(data));
        }

        stream.writeInt(palette.size());
        IntWriter writer = this.getWriteFunction(stream, palette.size());

        for (String data : palette.keySet()) {
            stream.writeUTF(data);
        }
        for (int i : ints) {
            writer.write(i);
        }

        stream.flush();
        stream.close();
    }

    private interface IntWriter {
        void write(int i) throws IOException;
    }

    private IntWriter getWriteFunction(DataOutputStream stream, int numElements) {
        int bits = log2(numElements);
        switch (bits / 8 + 1) {
            case 1:
                return stream::writeByte;
            case 2:
                return stream::writeShort;
            default:
                throw new IllegalArgumentException("Too much blocks");
        }
    }

    private int log2(int bits) {
        if (bits == 0) {
            return 0;
        }
        return 31 - Integer.numberOfLeadingZeros(bits);
    }*/

    /**
     * Gets the file
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }
}
