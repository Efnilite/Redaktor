package com.efnilite.redaktor.schematic.io;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.selection.Dimensions;
import com.efnilite.redaktor.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class for reading Schematics
 */
public class SchematicReader {

    /**
     * The file
     */
    private String file;

    /**
     * Creates a new instance
     *
     * @param    file
     *           The file
     */
    public SchematicReader(String file) {
        this.file = file.endsWith(".rschem") ? file : file + ".rschem";
    }

    /**
     * Reads a schematic
     *
     * @return  a {@link ReaderReturn} instance
     *
     * @throws  IOException
     *          When something goes wrong
     */
    public ReaderReturn read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        HashMap<Integer, BlockData> palette = new HashMap<>();
        List<BlockData> data = new ArrayList<>();
        List<String> lines = reader.lines().collect(Collectors.toList());

        String version = lines.get(0);
        String dimension = lines.get(1);
        String properties = lines.get(2);
        String blocks = lines.get(lines.size() - 1);

        if (!version.equals(Redaktor.SCHEMATIC_VERSION)) {
            Redaktor.getInstance().getLogger().warning("Outdated schematic version found!");
            Redaktor.getInstance().getLogger().warning("Updating the outdated schematic to the latest format..");
            Redaktor.getInstance().getLogger().warning("This might take a while");

            // Currently impossible due to there only being one version
            // TODO - Updater
        }

        boolean readPalette = false;
        for (String string : lines) {
            if (string.contains("*")) {
                readPalette = true;
                continue;
            } else if (string.contains("-")) {
                break;
            }
            if (readPalette) {
                String[] elements = string.split(" > ");
                palette.put(Integer.parseInt(elements[0]), Pattern.parseData(elements[1]));
            }
        }

        for (String string : blocks.split(",")) {
            String[] elements = string.split("x");
            if (string.contains("x")) {
                int loop = Integer.parseInt(elements[1]);
                for (int i = 0; i < loop; i++) {
                    String element = elements[0];
                    data.add(palette.get(Integer.parseInt(element)));
                }
            } else {
                String element = elements[0];
                data.add(palette.get(Integer.parseInt(element)));
            }
        }

        Location[] locations = new Location[2];
        int i = 0;
        for (String string : dimension.split(",")) {
            locations[i] = Util.fromDeserializableString(string);
            i++;
        }

        World world = locations[0].getWorld() == null ? locations[1].getWorld() : locations[0].getWorld();
        return new ReaderReturn(new Dimensions(new CuboidSelection(locations[0], locations[1], world)), data);
    }

    /**
     * Gets the file
     *
     * @return the file
     */
    public String getFile() {
        return file;
    }

    public static class ReaderReturn {

        private Dimensions dimensions;
        private List<BlockData> data;

        public ReaderReturn(Dimensions dimensions, List<BlockData> data) {
            this.dimensions = dimensions;
            this.data = data;
        }

        public void setData(List<BlockData> data) {
            this.data = data;
        }

        public Dimensions getDimensions() {
            return dimensions;
        }

        public List<BlockData> getData() {
            return data;
        }
    }
}