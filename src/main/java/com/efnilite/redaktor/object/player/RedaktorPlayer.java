package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.object.cuboid.Cuboid;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * A wrapper type for org.bukkit.entity.Player.
 * Mainly used to store player-based data.
 */
public class RedaktorPlayer {

    private Cuboid selection;
    private Location pos1;
    private Location pos2;
    private Player player;

    public RedaktorPlayer(Player player) {
        this.player = player;
    }

    public void delete() {
        this.pos1 = null;
        this.pos2 = null;
        this.player = null;
        this.selection = null;
    }

    public Location getPos1() {
        return pos1;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getPos2() {
        return pos2;
    }

    public Cuboid getSelection() {
        return selection;
    }

    public static RedaktorPlayer wrap(Player player) {
        return Redaktor.getPlayerFactory().getRegisteredPlayers().get(player.getUniqueId());
    }
}
