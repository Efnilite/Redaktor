package com.efnilite.redaktor.command;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.command.util.Command;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.player.BukkitPlayer;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.util.item.SuperUtil;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Material;

import java.util.StringJoiner;

public class SuperItemCommands implements Commandable {

    @Command(permission = "redaktor.superitem")
    public void superitem(RedaktorPlayer<?> sender, String[] args) {
        if (Redaktor.isLatest()) {
            if (sender.isPlayer()) {
                if (args[0] != null) {
                    BukkitPlayer player = (BukkitPlayer) sender;
                    if (player.getHoldingItem().getType() != Material.AIR) {
                        StringJoiner joiner = new StringJoiner(" ");
                        for (String arg : args) {
                            joiner.add(arg);
                        }

                        SuperUtil.create(player.getHoldingItem(), "/" + joiner.toString());
                        sender.send("You turned your held &c" + Util.format(player.getHoldingItem().getType()) + "&7 into a SuperItem");
                        sender.send("Now if you right or left-click you will execute &c'" + joiner.toString() + "'");
                    } else {
                        sender.send("You can't set air as a SuperItem!");
                    }
                } else {
                    sender.send("You need to set a command!");
                }
            } else {
                sender.send("You need to be a player to create SuperItems!");
            }
        } else {
            sender.send("You need to be running 1.14 for this to work!");
        }
    }

    @Command(permission = "redaktor.removesuperitem", aliases = { "removesuper", "deletesuperitem", "deletesuper" })
    public void removesuperitem(RedaktorPlayer<?> sender, String[] args) {
        if (Redaktor.isLatest()) {
            if (sender.isPlayer()) {
                BukkitPlayer player = (BukkitPlayer) sender;
                if (player.getHoldingItem().getType() != Material.AIR) {
                    SuperUtil.removePersistentData(player.getHoldingItem(), "issuper");
                    SuperUtil.removePersistentData(player.getHoldingItem(), "supercommand");

                    sender.send("You deleted your held &c" + Util.format(player.getHoldingItem().getType()) + "&7 as a SuperItem");
                } else {
                    sender.send("You can't remove air as a tool!");
                }
            } else {
                sender.send("You need to be a player to delet SuperItems!");
            }
        } else {
            sender.send("You need to be running 1.14 for this to work!");
        }
    }
}