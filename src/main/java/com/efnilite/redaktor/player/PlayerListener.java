package com.efnilite.redaktor.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerListener implements Listener {

    private ItemStack wand;

    public PlayerListener() {
        this.wand = new ItemBuilder(Material.WOODEN_AXE, "&cWand").build();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Redaktor.getPlayerFactory().register(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Redaktor.getPlayerFactory().unregister(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player pl = e.getPlayer();
        RedaktorPlayer player = RedaktorPlayer.wrap(pl);

        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (pl.getInventory().getItemInMainHand().getItemMeta() != null) {
                    if (pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(wand.getItemMeta().getDisplayName())) {
                        if (pl.getInventory().getItemInMainHand().getType() == wand.getType()) {
                            if (pl.hasPermission("redaktor.wand")) {
                                e.setCancelled(true);
                                player.setPos1(e.getClickedBlock().getLocation().clone());
                                if (player.getPos1() != null && player.getPos2() != null) {
                                    CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                                    player.send("&7Position 2 set to " + Util.toString(e.getClickedBlock().getLocation()) + " (" + selection.getDimensions().getVolume() + " blocks)");
                                } else {
                                    player.send("&7Position 1 set to " + Util.toString(e.getClickedBlock().getLocation()) + " (0 blocks)");
                                }
                            }
                        }
                    } else if (hasPersistentData(pl.getInventory().getItemInMainHand(), "isSuperItem")) {
                        String command = this.getPersistentData(pl.getInventory().getItemInMainHand(), "SuperItemCommand", PersistentDataType.STRING);

                        // do a lot of stuff
                    }
                }
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (pl.getInventory().getItemInMainHand().getItemMeta() != null) {
                    if (pl.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(wand.getItemMeta().getDisplayName())) {
                        if (pl.getInventory().getItemInMainHand().getType() == wand.getType()) {
                            if (pl.hasPermission("redaktor.wand")) {
                                e.setCancelled(true);
                                player.setPos2(e.getClickedBlock().getLocation().clone());
                                if (player.getPos1() != null && player.getPos2() != null) {
                                    CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                                    player.send("&7Position 2 set to " + Util.toString(e.getClickedBlock().getLocation()) + " (" + selection.getDimensions().getVolume() + " blocks)");
                                } else {
                                    player.send("&7Position 2 set to " + Util.toString(e.getClickedBlock().getLocation()) + " (0 blocks)");
                                }
                            }
                        }
                    } else if (hasPersistentData(pl.getInventory().getItemInMainHand(), "isSuperItem")) {
                        String command = this.getPersistentData(pl.getInventory().getItemInMainHand(), "SuperItemCommand", PersistentDataType.STRING);

                        // do a lot of stuff
                    }
                }
            }
        }
    }

    private <A, B> B getPersistentData(ItemStack item, String key, PersistentDataType<A, B> type) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey name = new NamespacedKey(Redaktor.getInstance(), key);

        return container.get(name, type);
    }

    private boolean hasPersistentData(ItemStack item, String key) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        NamespacedKey name = new NamespacedKey(Redaktor.getInstance(), key);

        return container.has(name, PersistentDataType.BYTE);
    }
}