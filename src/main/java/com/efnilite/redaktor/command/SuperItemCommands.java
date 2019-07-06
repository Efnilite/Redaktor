package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Commandable;

public class SuperItemCommands implements Commandable {

    /*@Command(
        permission = "redaktor.supertool"
    )
    public void supertool(CommandSender sender, String[] args) {
        if (Redaktor.isLatest()) {
            if (sender instanceof Player) {
                if (args[0] != null) {
                    Player player = (Player) sender;
                    if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                        StringJoiner joiner = new StringJoiner(" ");
                        for (String arg : args) {
                            joiner.add(arg);
                        }

                        SuperItemBuilder.create(player, joiner.toString());
                        BukkitPlayer.wrap(player).send("You created a new SuperItem.");
                    }
                }
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                BukkitPlayer.wrap(player).send("You need to be running 1.14.x to enable this!");
            }
        }
    }*/
}