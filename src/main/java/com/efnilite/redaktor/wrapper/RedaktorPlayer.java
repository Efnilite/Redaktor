package com.efnilite.redaktor.wrapper;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.Redaktor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface RedaktorPlayer<T extends CommandSender> {

    void send(String message);

    void setPos1(Location location);

    void setPos2(Location location);

    boolean isPlayer();

    Location getPos1();

    Location getPos2();

    Editor<T> getEditor();

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