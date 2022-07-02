package dev.efnilite.redaktor.util.factory;

import dev.efnilite.redaktor.player.BukkitPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * A class for saving players so they can be easily retrieved
 */
public class PlayerFactory {

    /**
     * A map of all the registered players
     */
    private HashMap<UUID, BukkitPlayer> registeredPlayers;

    /**
     * Create a new instance
     */
    public PlayerFactory() {
        this.registeredPlayers = new HashMap<>();
    }

    /**
     * Unregister a player
     *
     * @param   player
     *          The player that needs to be unregistered
     */
    public void unregister(Player player) {
        this.registeredPlayers.remove(player.getUniqueId());
    }

    /**
     * Register a player
     *
     * @param   player
     *          The Bukkit player to be registered
     */
    public void register(Player player) {
        this.registeredPlayers.put(player.getUniqueId(), new BukkitPlayer(player));
    }

    /**
     * Gets a map of all the registered players
     *
     * @return a map
     */
    public HashMap<UUID, BukkitPlayer> getRegisteredPlayers() {
        return registeredPlayers;
    }
}