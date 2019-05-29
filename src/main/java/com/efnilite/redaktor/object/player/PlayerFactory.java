package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.util.factory.RegisterFactory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerFactory implements RegisterFactory<Player> {

    private HashMap<UUID, RedaktorPlayer> registeredPlayers;

    @Override
    public void unregister(Player player) {
        this.registeredPlayers.remove(player.getUniqueId());
    }

    @Override
    public void register(Player player) {
        this.registeredPlayers.put(player.getUniqueId(), new RedaktorPlayer(player));
    }

    public HashMap<UUID, RedaktorPlayer> getRegisteredPlayers() {
        return registeredPlayers;
    }
}