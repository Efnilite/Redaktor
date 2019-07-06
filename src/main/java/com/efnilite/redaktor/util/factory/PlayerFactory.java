package com.efnilite.redaktor.util.factory;

import com.efnilite.redaktor.player.BukkitPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerFactory implements RegisterFactory<Player> {

    private HashMap<UUID, BukkitPlayer> registeredPlayers;

    public PlayerFactory() {
        this.registeredPlayers = new HashMap<>();
    }

    @Override
    public void unregister(Player player) {
        this.registeredPlayers.remove(player.getUniqueId());
    }

    @Override
    public void register(Player player) {
        this.registeredPlayers.put(player.getUniqueId(), new BukkitPlayer(player));
    }

    public HashMap<UUID, BukkitPlayer> getRegisteredPlayers() {
        return registeredPlayers;
    }
}