package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.Strings;
import com.efnilite.redaktor.util.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

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
        Configuration configuration = Redaktor.getConfiguration();
        Player pl = e.getPlayer();
        RedaktorPlayer player = RedaktorPlayer.wrap(pl);

        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (pl.hasPermission(configuration.getFile("permissions.yml").getString("permissions.wand"))) {
                    if (pl.getInventory().getItemInMainHand() == wand) {
                        e.setCancelled(true);
                        player.setPos1(e.getClickedBlock().getLocation().clone());
                        if (player.getPos1() != null && player.getPos2() != null) {
                            CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                            player.send("&7You set position 1 (" + selection.getDimensions().getVolume() + " blocks)");
                        } else {
                            player.send("&7You set position 1 (0 blocks)");
                        }
                    }
                }
            } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (pl.hasPermission(configuration.getFile("permissions.yml").getString("permissions.wand"))) {
                    if (pl.getInventory().getItemInMainHand() == wand) {
                        e.setCancelled(true);
                        player.setPos2(e.getClickedBlock().getLocation().clone());
                        if (player.getPos1() != null && player.getPos2() != null) {
                            CuboidSelection selection = new CuboidSelection(player.getPos1(), player.getPos2());
                            player.send("&7Position 2 set to " + Strings.toString(e.getClickedBlock().getLocation()) + " (" + selection.getDimensions().getVolume() + " blocks)");
                        } else {
                            player.send("&7You set position 2 (0 blocks)");
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Configuration configuration = Redaktor.getConfiguration();
        Player player = e.getPlayer();
        String command = e.getMessage().replace("/", "").split(" ")[0];
        String[] args = e.getMessage().replace(command, "").split(" ");

        if (command.equals("wand")) {
            if (player.hasPermission(configuration.getFile("permissions.yml").getString("permissions.wand"))) {
                player.getInventory().addItem(this.wand);
            }
        }
    }
}