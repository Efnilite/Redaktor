package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.selection.CuboidSelection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Redaktor.getPlayerFactory().register(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Redaktor.getPlayerFactory().unregister(e.getPlayer());
    }

    @EventHandler
    public void testing(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().contains("sett")) {
            new CuboidSelection(e.getPlayer().getLocation(), e.getPlayer().getLocation().clone().add(10, 10, 10)).test();
        }
    }
}