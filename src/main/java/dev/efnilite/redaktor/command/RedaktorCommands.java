package dev.efnilite.redaktor.command;

import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.command.util.Command;
import dev.efnilite.redaktor.command.util.Commandable;
import dev.efnilite.redaktor.player.BukkitPlayer;
import dev.efnilite.redaktor.util.item.ItemBuilder;
import dev.efnilite.redaktor.util.web.UpdateChecker;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Material;

import java.io.IOException;

public class RedaktorCommands implements Commandable {

    @Command
    public void redaktor(RedaktorPlayer<?> sender, String[] args) {
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase("version")) {
                sender.send("&7Redaktor is currently version " + Redaktor.getInstance().getDescription().getVersion());
            } else if (args[0].equalsIgnoreCase("update")) {
                UpdateChecker checker = Redaktor.getChecker();
                if (checker.check()) {
                    try {
                        sender.send("&7Redaktor can be updated to version " + checker.getLatestVersion() + "! (https://github.com/Efnilite/Redaktor/releases)");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
}