package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;

import java.io.IOException;

public class SchematicCommands implements Commandable {

    @Command(permission = "redaktor.schematic.save", usage = "/save <file>")
    public void save(RedaktorPlayer<?> sender, String[] args) {
        if (sender.getPos1() != null && sender.getPos2() != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            Schematic schematic = new Schematic(selection);
            if (args.length == 1) {
                try {
                    if (args[0].contains("/")) {
                        schematic.save(args[0]);
                    } else {
                        schematic.save("plugins/Redaktor/schematics/" + args[0]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.send("There was an error trying to save your schematic!");
                }
            } else {
                sender.send("You need to set a name or a directory to save your schematic to!");
            }
        } else {
            sender.send("You need to set position 1 and 2 to be able to do that!");
        }
    }

    @Command(permission = "redaktor.schematic.paste", usage = "/paste <schematic> [angle]")
    public void paste(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 1 || args.length == 2) {
            Schematic schematic = new Schematic(args[0]);
            try {
                if (args.length == 2) {
                    schematic.paste(sender.getLocation(), Integer.parseInt(args[1]));
                } else {
                    schematic.paste(sender.getLocation());
                }
            } catch (IOException e) {
                e.printStackTrace();
                sender.send("There was an error trying to save your schematic!");
            }
        } else {
            sender.send("You need to set a schematic name!");
        }
    }
}