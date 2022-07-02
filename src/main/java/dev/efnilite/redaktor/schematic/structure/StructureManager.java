package dev.efnilite.redaktor.schematic.structure;

import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.io.File;

public interface StructureManager {

    Vector paste(File file, Location to);

    void save(File file, CuboidSelection selection);

    void showBounds(RedaktorPlayer<?> player, Selection selection);

}
