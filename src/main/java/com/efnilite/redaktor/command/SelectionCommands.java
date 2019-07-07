package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.pattern.Pattern;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;

public class SelectionCommands implements Commandable {

    private Pattern.Parser parser;

    public SelectionCommands() {
        this.parser = new Pattern.Parser();
    }

    @Command(permission = "redaktor.pos1", description = "Set position 1 of your selection to a location.",
            usage = "/pos1 [location] (example: /pos1 43,5,76)", aliases = { "position1" })
    public void pos1(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] == null) {
            sender.setPos1(sender.getLocation());
        } else {
            sender.setPos1(Util.fromString(args[0]));
        }
    }

    @Command(permission = "redaktor.pos2", description = "Set position 2 of your selection to a location.",
            usage = "/pos2 [location] (example: /pos1 65,54,-32)", aliases = { "position2" })
    public void pos2(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] == null) {
            sender.setPos2(sender.getLocation());
        } else {
            sender.setPos2(Util.fromString(args[0]));
        }
    }

    @Command(permission = "redaktor.set", usage = "/set <pattern>")
    public void set(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            sender.getEditor().setBlocks(selection, parser.parse(args[0]));
        }
    }

    @Command(permission = "redaktor.slow", usage = "/slow <pattern> <amount of blocks per tick (1/20th of a second)>")
    public void slow(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] != null && args[1] != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            sender.getEditor().setSlowBlocks(selection, parser.parse(args[0]), Integer.parseInt(args[1]));
        }
    }

    @Command(permission = "redaktor.copy", usage = "/copy <x> [y] <z>")
    public void copy(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] != null && args[1] != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            if (args[2] != null) {
                sender.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            } else {
                sender.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            }
        }
    }
}