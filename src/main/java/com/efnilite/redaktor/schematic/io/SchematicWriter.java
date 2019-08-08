package com.efnilite.redaktor.schematic.io;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SchematicWriter {

    private String file;

    public SchematicWriter(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
    }

    public void write(List<Block> blocks, Schematic.SaveOptions... options) throws IOException {
        List<Schematic.SaveOptions> saveOptions = Arrays.asList(options);
        FileWriter writer = new FileWriter(file);
        HashSet<Material> filter = new HashSet<>();
        HashMap<Material, Integer> palette = new HashMap<>();
        for (Block block : blocks) {
            filter.add(block.getType());
        }

        if (saveOptions.contains(Schematic.SaveOptions.SKIP_AIR)) {
            writer.write("#");
        }

        int index = 0;
        for (Material material : filter) {
            palette.put(material, index);
            writer.write(index + " > " + material.name() + System.lineSeparator());
            index++;
        }

        StringJoiner joiner = new StringJoiner(",");

        for (Block block : blocks) {
            BlockData data = block.getBlockData();

            String id = palette.get(block.getType()).toString();
            String blockData = data.getAsString().replaceAll("minecraft:", "");
            String parsed = blockData.replaceAll("\\w+", id);

            if (blockData.contains("[")) {
                parsed = blockData.replaceAll("\\w+\\[", id + "[");
            }

            joiner.add(parsed);
        }

        writer.write("-" + System.lineSeparator());
        writer.write(joiner.toString());

        writer.close();
    }

    public String getFile() {
        return file;
    }
}
