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

    private int amount = 1;
    private String name;
    private List<String> lore;
    private Material type;

    public ItemBuilder() {
        this.name = "";
        this.lore = new ArrayList<>();
        this.type = null;
    }

    public ItemBuilder(Material material, String name) {
        this.name = name;
        this.lore = new ArrayList<>();
        this.type = material;
    }

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

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setType(Material type) {
        this.type = type;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public List<String> getLore() {
        return lore;
    }

    public Material getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
