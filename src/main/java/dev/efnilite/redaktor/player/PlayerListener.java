package dev.efnilite.redaktor.player;

import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.selection.CuboidSelection;
import dev.efnilite.redaktor.util.Util;
import dev.efnilite.redaktor.util.item.ItemBuilder;
import dev.efnilite.redaktor.util.item.SuperUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

/**
 * A class for handling player events
 */
public class PlayerListener implements Listener {

    /**
     * The wand item
     */
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
        BukkitPlayer player = BukkitPlayer.wrap(pl);

        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (player.getHoldingItem().getItemMeta() != null) {
                    if (player.getHoldingItem().getItemMeta().getDisplayName().equals(wand.getItemMeta().getDisplayName())) {
                        if (player.getHoldingItem().getType() == wand.getType()) {
                            if (pl.hasPermission("redaktor.wand")) {

                                e.setCancelled(true);
                                player.setPos1(e.getClickedBlock().getLocation());

                                if (player.getPos1() != null && player.getPos2() != null) {
                                    CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                                    player.setSelection(selection);

                                    player.sendLang("set-position-1", Util.toString(e.getClickedBlock().getLocation()), Integer.toString(selection.getDimensions().getVolume()));
                                } else {
                                    player.sendLang("set-position-1", Util.toString(e.getClickedBlock().getLocation()), Integer.toString(0));
                                }
                            }
                        }
                    }
                }
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (player.getHoldingItem().getItemMeta() != null) {
                    if (player.getHoldingItem().getItemMeta().getDisplayName().equals(wand.getItemMeta().getDisplayName())) {
                        if (player.getHoldingItem().getType() == wand.getType()) {
                            if (pl.hasPermission("redaktor.wand")) {

                                e.setCancelled(true);
                                player.setPos2(e.getClickedBlock().getLocation());

                                if (player.getPos1() != null && player.getPos2() != null) {
                                    CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                                    player.setSelection(selection);
                                    if (Redaktor.getConfiguration().getFile("config").getBoolean("bounding")) {
                                        //Redaktor.getStructureManager().showBounds(player, selection); todo
                                    }

                                    player.sendLang("set-position-2", Util.toString(e.getClickedBlock().getLocation()), Integer.toString(selection.getDimensions().getVolume()));
                                } else {
                                    player.sendLang("set-position-2", Util.toString(e.getClickedBlock().getLocation()), Integer.toString(0));
                                }
                            }
                        }
                    }
                }
            }
            if (SuperUtil.hasPersistentData(player.getHoldingItem(), "super", PersistentDataType.BYTE)) {
                String command = (String) SuperUtil.getPersistentData(player.getHoldingItem(), "supercommand", PersistentDataType.STRING);
                pl.performCommand(command);
            }
        }
    }
}