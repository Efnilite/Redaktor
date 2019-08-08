package com.efnilite.redaktor.schematic.io;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.pattern.Pattern;
import org.bukkit.block.data.BlockData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SchematicReader {

    private String file;

    public SchematicReader(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
    }

    public List<BlockData> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        List<String> lines = reader.lines().collect(Collectors.toList());
        String version = lines.get(0);
        if (!version.equals(Redaktor.SCHEMATIC_VERSION)) {
            Redaktor.getInstance().getLogger().warning("Outdated schematic version found!");
            Redaktor.getInstance().getLogger().warning("Updating the outdated schematic to the latest format..");
            Redaktor.getInstance().getLogger().warning("This might take a while");

            // Currently impossible due to there only being one version
            // TODO - Updater
        }

        String blocks = "";
        List<String> filePalette = new ArrayList<>();

        System.out.println(blocks);
        HashMap<Integer, BlockData> palette = new HashMap<>();

        for (String local : filePalette) {
            String[] elements = local.split(" > ");
            palette.put(Integer.parseInt(elements[0]), Pattern.parseData(elements[1]));
        }

        return null;
    }

    public String getFile() {
        return file;
    }
}
