package dev.efnilite.redaktor.player;

import dev.efnilite.redaktor.Editor;
import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
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

    /**
     * The selection
     */
    private Selection selection;

    /**
     * The editor
     */
    private Editor<Player> editor;

    /**
     * The first position
     */
    private Location pos1;

    /**
     * The second position
     */
    private Location pos2;

    /**
     * The player instance
     */
    private Player player;

    /**
     * Creates a new instance
     *
     * @param   player
     *          The player
     */
    public BukkitPlayer(Player player) {
        this.player = player;
        this.editor = new Editor<>(player, -1, false);
    }

    @Override
    public void send(String message) {
        this.player.sendMessage(ChatColor.translateAlternateColorCodes('&', Redaktor.PREFIX + " &7" + message));
    }

    @Override
    public void sendLang(String message, String... replacements) {
        String string = Redaktor.getConfiguration().getString("lang", "languages." + Redaktor.getConfiguration().getLanguage() + "." + message);
        if (replacements.length != 0) {
            for (String replacement : replacements) {
                string = string.replaceFirst("(%\\w+%)", replacement);
            }
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Redaktor.PREFIX + " &7" + string));
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
    public void setSelection(Selection selection) {
        this.selection = selection;
        this.pos1 = selection.getPos1();
        this.pos2 = selection.getPos2();
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
    public Selection getSelection() {
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