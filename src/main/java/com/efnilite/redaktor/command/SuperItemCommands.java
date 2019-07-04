package com.efnilite.redaktor.command;

import com.efnilite.connotations.Commandable;

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
                        RedaktorPlayer.wrap(player).send("You created a new SuperItem.");
                    }
                }
            }
        } else {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                RedaktorPlayer.wrap(player).send("You need to be running 1.14.x to enable this!");
            }
        }
    }

    public static class SuperItemBuilder {

        public static void create(Player player, String command) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey name = new NamespacedKey(Redaktor.getInstance(), "isSuperItem");

            container.set(name, PersistentDataType.BYTE, (byte) 0);
            PersistentDataContainer container2 = meta.getPersistentDataContainer();
            NamespacedKey name2 = new NamespacedKey(Redaktor.getInstance(), "SuperItemCommand");

            container2.set(name2, PersistentDataType.STRING, command);
            item.setItemMeta(meta);
            player.getInventory().setItemInMainHand(item);
        }

        public static void create(ItemStack item, String command) {
            ItemMeta meta = item.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey name = new NamespacedKey(Redaktor.getInstance(), "isSuperItem");

            container.set(name, PersistentDataType.BYTE, (byte) 0);
            PersistentDataContainer container2 = meta.getPersistentDataContainer();
            NamespacedKey name2 = new NamespacedKey(Redaktor.getInstance(), "SuperItemCommand");

            container2.set(name2, PersistentDataType.STRING, command);
            item.setItemMeta(meta);
        }
    }*/
}