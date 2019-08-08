package com.efnilite.redaktor.wrapper;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.selection.Selection;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * An interface for objects that can use Redaktor commands. In this case those are
 * {@link com.efnilite.redaktor.wrapper.console.ConsolePlayer} and {@link com.efnilite.redaktor.player.BukkitPlayer}
 *
 * @param   <T>
 *          The instance of who to send messages to if actions are completed.
 */
public interface RedaktorPlayer<T extends CommandSender> {

    /**
     * Sends a message
     *
     * @param   message
     *          The message
     */
    void send(String message);

    /**
     * Send a message from the lang.yml file
     *
     * @param   message
     *          The yaml path
     *
     * @param   replacements
     *          The replacements
     */
    void sendLang(String message, String... replacements);

    /**
     * Sets the first position
     *
     * @param   location
     *          The first position
     */
    void setPos1(Location location);

    /**
     * Sets the second position
     *
     * @param   location
     *          The second position
     */
    void setPos2(Location location);

    /**
     * Sets the selection
     *
     * @param   selection
     *          The selection
     */
    void setSelection(Selection selection);

    /**
     * Checks if the instance is a player
     *
     * @return true if it is a player
     */
    boolean isPlayer();

    /**
     * Gets the location
     *
     * @return the location
     */
    Location getLocation();

    /**
     * Gets the first position of the selection
     *
     * @return the first position
     */
    Location getPos1();

    /**
     * Gets the second position of the selection
     *
     * @return the second position
     */
    Location getPos2();

    /**
     * Gets the editor
     *
     * @return the editor
     */
    Editor<T> getEditor();

    /**
     * Gets the current selection
     *
     * @return the selection
     */
    Selection getSelection();

    /**
     * Wraps a CommandSender to a RedaktorPlayer instance
     *
     * @param   sender
     *          The CommandSender instance
     *
     * @return  a RedaktorPlayer instance
     */
    static RedaktorPlayer<?> wrap(CommandSender sender) {
        if (sender instanceof Player) {
            return Redaktor.getPlayerFactory().getRegisteredPlayers().get(((Player) sender).getUniqueId());
        } else if (sender instanceof ConsoleCommandSender) {
            return Redaktor.getConsolePlayer();
        } else {
            return null;
        }
    }
}