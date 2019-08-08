package com.efnilite.redaktor.schematic.io;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class SchematicReader {

    private String file;

    public SchematicReader(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
    }

    public List<BlockData> read() {
        Scanner scanner = new Scanner(file);

        List<String> filePalette = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.contains("-")) {
                filePalette.add(line);
            } else {
                break;
            }
        }
        HashMap<Integer, Material> palette = new HashMap<>();

        for (String line : filePalette) {
            String[] elements = line.split(" > ");
            palette.put(Integer.parseInt(elements[0]), Material.getMaterial(elements[1]));
        }



        return null;
    }

    public String getFile() {
        return file;
    }
}
