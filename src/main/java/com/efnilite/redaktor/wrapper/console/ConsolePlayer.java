package com.efnilite.redaktor.wrapper.console;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;

public class ConsolePlayer implements RedaktorPlayer<ConsoleCommandSender> {

    private Location pos1;
    private Location pos2;
    private ConsoleCommandSender sender;
    private Editor<ConsoleCommandSender> editor;

    public ConsolePlayer(ConsoleCommandSender sender) {
        this.sender = sender;
    }

    @Override
    public void send(String message) {
        this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&cRedaktor&8) &7" + message));
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
    public boolean isPlayer() {
        return false;
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
    public Editor<ConsoleCommandSender> getEditor() {
        return editor;
    }
}
