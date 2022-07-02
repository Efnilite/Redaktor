package dev.efnilite.redaktor.command;

import dev.efnilite.redaktor.command.util.Command;
import dev.efnilite.redaktor.command.util.Commandable;
import dev.efnilite.redaktor.player.BukkitPlayer;
import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;

public class CuboidCommands implements Commandable {

    @Command(permission = "redaktor.chunk", usage = "/chunk")
    public void chunk(RedaktorPlayer<?> sender, String[] args) {
        if (sender.isPlayer()) {
            BukkitPlayer player = (BukkitPlayer) sender;
            player.setSelection(new CuboidSelection(player.getLocation(), player.getLocation()).toChunk());
            sender.sendLang("selected-chunk");
        }
    }

    @Command(permission = "redaktor.walls", usage = "/walls")
    public void walls(RedaktorPlayer<?> sender, String[] args) {
        CuboidSelection selection = (CuboidSelection) sender.getSelection();
        sender.setSelection(selection.getWalls());
        sender.sendLang("selected-walls");
    }

    @Command(permission = "redaktor.edges", usage = "/edges")
    public void edges(RedaktorPlayer<?> sender, String[] args) {
        CuboidSelection selection = (CuboidSelection) sender.getSelection();
        sender.setSelection(selection.getEdges());
        sender.sendLang("selected-edges");
    }

    @Command(permission = "redaktor.deselect", usage = "/deselect", aliases = {"/desel"})
    public void deselect(RedaktorPlayer<?> sender, String[] args) {
        sender.setSelection(null);
        sender.sendLang("selected-edges");
    }

    @Command(permission = "redaktor.duplicate", usage = "/duplicate <x> [y] <z>")
    public void duplicate(RedaktorPlayer<?> sender, String[] args) {
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