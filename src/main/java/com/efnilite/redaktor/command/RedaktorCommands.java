package com.efnilite.redaktor.command;

import com.efnilite.connotations.Command;
import com.efnilite.connotations.Commandable;
import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.pattern.types.BlockPattern;
import com.efnilite.redaktor.player.RedaktorPlayer;
import com.efnilite.redaktor.queue.types.BlockQueue;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.item.ItemBuilder;
import com.efnilite.redaktor.util.web.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedaktorCommands implements Commandable {

    @Command
    public void redaktor(CommandSender sender, String[] args) {
        if (args[0] != null) {
            if (args[0].equalsIgnoreCase("version")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&cRedaktor&8) &7Redaktor is currently version " + Redaktor.getInstance().getDescription().getVersion()));
            } else if (args[0].equalsIgnoreCase("update")) {
                UpdateChecker checker = Redaktor.getChecker();
                if (checker.check()) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&cRedaktor&8) &7Redaktor can be updated to" + "version " + checker.getLatestVersion() + "!"));
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&cRedaktor&8) &7Redaktor is currently" + "" + "up-to-date!"));
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7------ &8(&cRedaktor&8) &7------"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor &7- &fPrints this message"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor version &7- &fPrints the version"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor update &7- &fCheck to see if there's an update"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/wand &7- &fGet the Wand"));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7------ &8(&cRedaktor&8) &7------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor &7- &fPrints this message"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor version &7- &fPrints the version"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/redaktor update &7- &fCheck to see if there's an update"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/wand &7- &fGet the Wand"));
        }
    }

    @Command
    public void test(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        BlockQueue queue = new BlockQueue(new BlockPattern(Material.STONE));
        queue.build(new CuboidSelection(player.getLocation(), player.getLocation().add(2, 2, 2)));
    }

    @Command(
            permission = "redaktor.wand"
    )
    public void wand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().addItem(new ItemBuilder(Material.WOODEN_AXE, "&cWand").build());
        }
    }

    @Command(
            permission = "redaktor.undo"
    )
    public void undo(CommandSender sender, String[] args) {
        Player pl = (Player) sender;
        RedaktorPlayer player = RedaktorPlayer.wrap(pl);
        if (args[0] != null) {
            player.getEditor().undo(Integer.parseInt(args[0]));
        } else {
            player.getEditor().undo();
        }
    }
}