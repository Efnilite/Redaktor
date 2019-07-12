package com.efnilite.redaktor.player;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A wrapper type for org.bukkit.entity.Player.
 * Mainly used to store player-based data.
 */
public class BukkitPlayer implements RedaktorPlayer<Player> {

    private CuboidSelection selection;
    private Editor<Player> editor;
    private Location pos1;
    private Location pos2;
    private Player player;

    public BukkitPlayer(Player player) {
        this.player = player;
        this.editor = new Editor<>(player);
    }

    @Override
    public void send(String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', Redaktor.PREFIX + " &7" + message));
    }

    @Override
    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    @Override
    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    @Override
    public void setSelection(CuboidSelection selection) {
        this.selection = selection;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public Location getPos1() {
        return pos1;
    }

    @Override
    public Location getPos2() {
        return pos2;
    }

    @Override
    public Editor<Player> getEditor() {
        return editor;
    }

    @Override
    public CuboidSelection getSelection() {
        return selection;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getHoldingItem() {
        return player.getInventory().getItemInMainHand().getType() == Material.AIR ? player.getInventory().getItemInOffHand() : player.getInventory().getItemInMainHand();
    }

    public static BukkitPlayer wrap(Player player) {
        return Redaktor.getPlayerFactory().getRegisteredPlayers().get(player.getUniqueId());
    }
}