package com.efnilite.redaktor.command;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.player.BukkitPlayer;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.item.ItemBuilder;
import com.efnilite.redaktor.util.web.UpdateChecker;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Material;

public class RedaktorCommands implements Commandable {

    @Command
    public void redaktor(RedaktorPlayer<?> sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("version")) {
                sender.send("&7Redaktor is currently version " + Redaktor.getInstance().getDescription().getVersion());
            } else if (args[0].equalsIgnoreCase("update")) {
                UpdateChecker checker = Redaktor.getChecker();
                if (checker.check()) {
                    sender.send("&7Redaktor can be updated to version " + checker.getLatestVersion() + "!");
                } else {
                    sender.send("&7Redaktor is currently up-to-date!");
                }
            } else {
                sender.send("&7------ &8(&c&lRedaktor&8) &7------");
                sender.send("&c/redaktor &7- &fPrints this message");
                sender.send("&c/redaktor version &7- &fPrints the version");
                sender.send("&c/redaktor update &7- &fCheck to see if there's an update");
                sender.send("&c/wand &7- &fGet the Wand");
            }
        } else {
            sender.send("&7------ &8(&c&lRedaktor&8) &7------");
            sender.send("&c/redaktor &7- &fPrints this message");
            sender.send("&c/redaktor version &7- &fPrints the version");
            sender.send("&c/redaktor update &7- &fCheck to see if there's an update");
            sender.send("&c/wand &7- &fGet the Wand");
        }
    }

    @Command(permission = "redaktor.wand")
    public void wand(RedaktorPlayer<?> sender, String[] args) {
        if (sender.isPlayer()) {
            BukkitPlayer player = (BukkitPlayer) sender;
            player.getPlayer().getInventory().addItem(new ItemBuilder(Material.WOODEN_AXE, "&cWand").build());
        }
    }

    @Command
    public void copy(RedaktorPlayer<?> sender, String[] args) {
        if (sender.getPos1() != null || sender.getPos2() != null) {
            CuboidSelection selection = new CuboidSelection(sender.getPos1(), sender.getPos2());
            sender.setSelection(selection);
        }
    }

    @Command
    public void paste(RedaktorPlayer<?> sender, String[] args) {
        if (sender.getSelection() != null) {

        }
    }
}