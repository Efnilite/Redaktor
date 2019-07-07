package com.efnilite.redaktor.command;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.util.item.ItemBuilder;
import com.efnilite.redaktor.util.web.UpdateChecker;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RedaktorCommands implements Commandable {

    @Command
    public void redaktor(RedaktorPlayer<?> sender, String[] args) {
        if (args[0] != null) {
            if (args[0].equalsIgnoreCase("version")) {
                sender.send("&7Redaktor is currently version " + Redaktor.getInstance().getDescription().getVersion());
            } else if (args[0].equalsIgnoreCase("update")) {
                UpdateChecker checker = Redaktor.getChecker();
                if (checker.check()) {
                    sender.send("&7Redaktor can be updated to" + "version " + checker.getLatestVersion() + "!");
                } else {
                    sender.send("&7Redaktor is currently" + "" + "up-to-date!");
                }
            } else {
                sender.send("&7------ &8(&cRedaktor&8) &7------");
                sender.send("&c/redaktor &7- &fPrints this message");
                sender.send("&c/redaktor version &7- &fPrints the version");
                sender.send("&c/redaktor update &7- &fCheck to see if there's an update");
                sender.send("&c/wand &7- &fGet the Wand");
            }
        } else {
            sender.send("&7------ &8(&cRedaktor&8) &7------");
            sender.send("&c/redaktor &7- &fPrints this message");
            sender.send("&c/redaktor version &7- &fPrints the version");
            sender.send("&c/redaktor update &7- &fCheck to see if there's an update");
            sender.send("&c/wand &7- &fGet the Wand");
        }
    }

    @Command(permission = "redaktor.wand")
    public void wand(RedaktorPlayer<?> sender, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            player.getInventory().addItem(new ItemBuilder(Material.WOODEN_AXE, "&cWand").build());
        }
    }

    @Command(permission = "redaktor.undo")
    public void undo(RedaktorPlayer<?> sender, String[] args) {
        Player pl = (Player) sender;
        if (args[0] != null) {
            sender.getEditor().undo(Integer.parseInt(args[0]));
        } else {
            sender.getEditor().undo();
        }
    }
}