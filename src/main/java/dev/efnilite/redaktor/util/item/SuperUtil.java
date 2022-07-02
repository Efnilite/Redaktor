package dev.efnilite.redaktor.util.item;

import dev.efnilite.redaktor.Redaktor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * An utilities class for SuperItems
 */
public class SuperUtil {

    /**
     * Avoid new instances
     */
    private SuperUtil() {

    }

    /**
     * Creates a SuperItem
     *
     * @param   item
     *          The item
     *
     * @param   command
     *          The command
     */
    public static void create(ItemStack item, String command) {
        setPersistentData(item, "super", PersistentDataType.BYTE, (byte) 0);
        setPersistentData(item, "supercommand", PersistentDataType.STRING, command);
    }


    /**
     * Gets persistentdata from an Itemstack
     *
     * @param   itemStack
     *          The itemstack
     *
     * @param   key
     *          The key
     *
     * @param   type
     *          The PersistentData type
     *
     * @param   <T>
     *          The type of value
     *
     * @return  the data value
     */
    public static <T> Object getPersistentData(ItemStack itemStack, String key, PersistentDataType<T, T> type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        return container.get(namespacedKey, type);
    }

    /**
     * Checks if an itemstack has persistentdata
     *
     * @param   itemStack
     *          The itemstack
     *
     * @param   key
     *          The key
     *
     * @param   type
     *          The PersistentData type
     *
     * @param   <T>
     *          The type of value
     *
     * @return  true if it has the data
     */
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

    /**
     * Sets the persistent data of an itemstack
     *
     * @param   itemStack
     *          The itemstack
     *
     * @param   key
     *          The key
     *
     * @param   type
     *          The PersistentData type
     *
     * @param   t
     *          The value
     *
     * @param   <T>
     *          The type of value
     */
    public static <T> void setPersistentData(ItemStack itemStack, String key, PersistentDataType<T, T> type, T t) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        container.set(namespacedKey, type, t);
        itemStack.setItemMeta(meta);
    }

    /**
     * Removes persistent data from an itemstack
     *
     * @param   itemStack
     *          The itemstack
     *
     * @param   key
     *          The key to be removed
     */
    public static void removePersistentData(ItemStack itemStack, String key) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(Redaktor.getInstance(), key);

        container.remove(namespacedKey);
        itemStack.setItemMeta(meta);
    }
}
