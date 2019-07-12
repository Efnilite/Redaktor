package com.efnilite.redaktor.wrapper;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.selection.CuboidSelection;
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

    void send(String message);

    void setPos1(Location location);

    void setPos2(Location location);

    void setSelection(CuboidSelection selection);

    boolean isPlayer();

    Location getLocation();

    Location getPos1();

    Location getPos2();

    Editor<T> getEditor();

    CuboidSelection getSelection();

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