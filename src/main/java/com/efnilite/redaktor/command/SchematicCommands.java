package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;

import java.io.IOException;

public class SchematicCommands implements Commandable {

    @Command(permission = "redaktor.schematic", usage = "/schematic (save|paste) <file> [angle]")
    public void schematic(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 2 || args.length == 3) {
            if (args[0].equalsIgnoreCase("save")) {
                if (sender.getPos1() != null && sender.getPos2() != null) {
                    CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
                    Schematic schematic = new Schematic(selection);
                    try {
                        if (args[0].contains("/")) {
                            schematic.save(args[1]);
                        } else {
                            schematic.save("plugins/Redaktor/schematics/" + args[1]);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        sender.send("There was an error trying to save your schematic! Maybe the file doesn't exist?");
                    }
                } else {
                    sender.send("You need to set position 1 and 2 to be able to do that!");
                }
            } else if (args[0].equalsIgnoreCase("paste")) {
                Schematic schematic;
                if (args[1].contains("/")) {
                    schematic = new Schematic(args[1]);
                } else {
                    schematic = new Schematic("plugins/Redaktor/schematics/" + args[1]);
                }
                try {
                    sender.getEditor().store(schematic.getCuboidSelection().toHistory());
                    if (args.length == 3) {
                        schematic.paste(sender.getLocation(), Integer.parseInt(args[2]));
                    } else {
                        schematic.paste(sender.getLocation());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    sender.send("There was an error trying to save your schematic! Maybe the file doesn't exist?");
                }
            } else {
                sender.send("You need to set a schematic name!");
            }
        } else {
            sender.send("You need to set if you want to paste or save the schematic (/schematic (save|paste) <file> [angle]) and the schematic name itself!");
        }
    }
}