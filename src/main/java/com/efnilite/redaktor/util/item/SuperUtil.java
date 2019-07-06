package com.efnilite.redaktor.util.item;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SuperUtil {

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
}
