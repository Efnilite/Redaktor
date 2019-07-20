package com.efnilite.redaktor.util.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for creating items.
 */
public class ItemBuilder {

    /**
     * The amount of the item
     */
    private int amount = 1;

    /**
     * The name of the item
     */
    private String name;

    /**
     * The lore
     */
    private List<String> lore;

    /**
     * The type
     */
    private Material type;

    /**
     * Creates a new instance
     */
    public ItemBuilder() {
        this(null, null);
    }

    /**
     * Creates a new instance
     *
     * @param   material
     *          The material
     *
     * @param   name
     *          The name of the item
     */
    public ItemBuilder(Material material, String name) {
        this.name = name;
        this.lore = new ArrayList<>();
        this.type = material;
    }

    /**
     * Creates a new instance
     *
     * @param   material
     *          The material
     *
     * @param   amount
     *          The amount of the item
     *
     * @param   name
     *          The name of the item
     */
    public ItemBuilder(Material material, int amount, String name) {
        this.amount = amount;
        this.name = name;
        this.lore = new ArrayList<>();
        this.type = material;
    }

    /**
     * Finishes everything and gives the ItemStack result.
     *
     * @return  the result
     */
    public ItemStack build() {
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(item.getType());
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Sets the item amount
     *
     * @param   amount
     *          The item amount
     *
     * @return  the instance
     */
    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    /**
     * Sets the lore
     *
     * @param   lore
     *          The lore
     *
     * @return  the instance
     */
    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Sets the name
     *
     * @param   name
     *          The name
     *
     * @return  the instance
     */
    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets the type
     *
     * @param   type
     *          The type
     *
     * @return  the instance
     */
    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    /**
     * Gets the amount
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Gets the lore
     *
     * @return the lore
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the item type
     *
     * @return the type
     */
    public Material getType() {
        return type;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
