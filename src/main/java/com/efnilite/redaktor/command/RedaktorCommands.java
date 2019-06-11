package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RedaktorCommands implements Commandable {

    @Command
    public void redaktor(CommandSender sender, String[] args) {
        sender.sendMessage("Yeet!");
    }

    @Command(permission = "redaktor.wand")
    public void wand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getInventory().addItem(new ItemBuilder(Material.WOODEN_AXE, "&cWand").build());
        }
    }
}