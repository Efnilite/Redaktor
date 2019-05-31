package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

    /*@EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().contains("sett")) {
            try {
                RedaktorAPI.toSchematic(new Cuboid(e.getPlayer().getLocation(), e.getPlayer().getLocation().add(2, 2, 2))).save("plugins/Example.json");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }*/
}