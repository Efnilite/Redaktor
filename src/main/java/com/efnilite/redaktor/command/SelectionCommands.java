package com.efnilite.redaktor.command;

import com.efnilite.connotations.Command;
import com.efnilite.connotations.Commandable;
import com.efnilite.redaktor.RedaktorAPI;
import com.efnilite.redaktor.object.player.RedaktorPlayer;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SelectionCommands implements Commandable {

    @Command(
            permission = "redaktor.set",
            usage = "/set <pattern>"
    )
    public void set(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args[0] != null) {
                RedaktorPlayer player = RedaktorPlayer.wrap((Player) sender);
                CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                player.getEditor().setBlocks(selection, RedaktorAPI.parsePattern(args[0]));
            }
        }
    }

    @Command(
            permission = "redaktor.slow",
            usage = "/slow <pattern> <amount of blocks per tick (1/20th of a second)>"
    )
    public void slow(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args[0] != null && args[1] != null) {
                RedaktorPlayer player = RedaktorPlayer.wrap((Player) sender);
                CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                player.getEditor().setSlowBlocks(selection, RedaktorAPI.parsePattern(args[0]), Integer.parseInt(args[1]));
            }
        }
    }

    @Command(
            permission = "redaktor.copy",
            usage = "/copy <x> [y] <z>"
    )
    public void copy(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args[0] != null && args[1] != null) {
                RedaktorPlayer player = RedaktorPlayer.wrap((Player) sender);
                CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                if (args[2] != null) {
                    player.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                } else {
                    player.getEditor().copyCuboid(selection, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                }
            }
        }
    }
}