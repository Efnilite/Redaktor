package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A wrapper type for org.bukkit.entity.Player.
 * Mainly used to store player-based data.
 */
public class RedaktorPlayer {

    private Editor<Player> editor;
    private Location pos1;
    private Location pos2;
    private Player player;

    public RedaktorPlayer(Player player) {
        this.player = player;
        this.editor = new Editor<>(player);
    }

    public void send(String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&cRedaktor&8) &7" + message));
    }

    public Location getPos1() {
        return pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public Editor<Player> getEditor() {
        return editor;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public static RedaktorPlayer wrap(Player player) {
        return Redaktor.getPlayerFactory().getRegisteredPlayers().get(player.getUniqueId());
    }
}