package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.pattern.BlockPattern;
import org.bukkit.Material;
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
            Redaktor.getPlayerFactory().getRegisteredPlayers().get(e.getPlayer().getUniqueId())
                    .getEditor()
                    .createPyramid(e.getPlayer().getLocation(),
                            new BlockPattern(Material.STONE),
                            3);
        }
    }
}