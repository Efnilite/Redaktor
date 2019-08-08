package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.player.BukkitPlayer;
import com.efnilite.redaktor.schematic.Schematic;
import com.efnilite.redaktor.schematic.io.SchematicReader;
import com.efnilite.redaktor.schematic.io.SchematicWriter;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;

import java.io.IOException;

public class CuboidCommands implements Commandable {

    @Command
    public void test(RedaktorPlayer<?> sender, String[] args) {
        SchematicWriter writer = new SchematicWriter("plugins/lol");
        new AsyncBlockGetter(sender.getSelection(), t -> {
            try {
                writer.write(t, Schematic.SaveOptions.SKIP_AIR);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Command
    public void test1(RedaktorPlayer<?> sender, String[] args) {
        SchematicReader reader = new SchematicReader("plugins/lol");
        try {
            reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Command(permission = "redaktor.chunk")
    public void chunk(RedaktorPlayer<?> sender, String[] args) {
        if (sender.isPlayer()) {
            BukkitPlayer player = (BukkitPlayer) sender;
            player.setSelection(new CuboidSelection(player.getLocation(), player.getLocation()).toChunk());
        }
    }

    @Command(permission = "redaktor.walls", usage = "/walls")
    public void walls(RedaktorPlayer<?> sender, String[] args) {
        sender.setSelection(((CuboidSelection) sender.getSelection()).getWalls());
        sender.sendLang("selected-walls");
    }

    @Command(permission = "redaktor.edges", usage = "/edges")
    public void edges(RedaktorPlayer<?> sender, String[] args) {
        sender.setSelection(((CuboidSelection) sender.getSelection()).getEdges());
        sender.sendLang("selected-edges");
    }

    @Command(permission = "redaktor.copy", usage = "/copy <x> [y] <z>")
    public void copy(RedaktorPlayer<?> sender, String[] args) {
        if (args.length == 3 || args.length == 2) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            if (args.length == 3) {
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);

                sender.sendLang("set-blocks", Integer.toString(selection.getDimensions().getVolume() * x * y * z));
                sender.getEditor().copyCuboid(selection, x, y, z);
            } else {
                int x = Integer.parseInt(args[0]);
                int z = Integer.parseInt(args[1]);

                sender.sendLang("set-blocks", Integer.toString(selection.getDimensions().getVolume() * x * z));
                sender.getEditor().copyCuboid(selection, x, z);
            }
        }
    }
}