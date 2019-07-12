package com.efnilite.redaktor.util.item;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SuperUtil {

    public static void create(ItemStack item, String command) {
        setPersistentData(item, "issuper", PersistentDataType.BYTE, (byte) 0);
        setPersistentData(item, "supercommand", PersistentDataType.STRING, command);
    }

    public static <T> Object getPersistentData(ItemStack itemStack, String key, PersistentDataType<T, T> type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        return container.get(namespacedKey, type);
    }

    public static <T> boolean hasPersistentData(ItemStack itemStack, String key, PersistentDataType<T, T> type) {
        if (itemStack.getType() != Material.AIR) {
            ItemMeta meta = itemStack.getItemMeta();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

            return container.has(namespacedKey, type);
        } else {
            return false;
        }
    }

    public static <T> void setPersistentData(ItemStack itemStack, String key, PersistentDataType<T, T> type, T t) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        container.set(namespacedKey, type, t);
        itemStack.setItemMeta(meta);
    }

    public static <T> void removePersistentData(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        container.remove(namespacedKey);
        itemStack.setItemMeta(meta);
    }
}
