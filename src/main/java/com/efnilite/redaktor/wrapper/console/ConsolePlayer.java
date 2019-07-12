package com.efnilite.redaktor.wrapper.console;

import com.efnilite.redaktor.Editor;
import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.selection.CuboidSelection;
import com.efnilite.redaktor.util.Util;
import com.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;

/**
 * A wrapper for Console so that it can also execute Redaktor commands.
 */
public class ConsolePlayer implements RedaktorPlayer<ConsoleCommandSender> {

    private Editor<ConsoleCommandSender> editor;
    private ConsoleCommandSender sender;
    private CuboidSelection selection;
    private Location pos1;
    private Location pos2;

    public ConsolePlayer(ConsoleCommandSender sender) {
        this.sender = sender;
        this.editor = new Editor<>(Bukkit.getConsoleSender());
    }

    @Override
    public void send(String message) {
        this.sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Redaktor.PREFIX + " &7" + message));
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
    public CuboidSelection getSelection() {
        return selection;
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public Location getLocation() {
        return Util.zero();
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
    public void setSelection(CuboidSelection selection) {
        this.selection = selection;
    }

    @Override
    public Editor<ConsoleCommandSender> getEditor() {
        return editor;
    }
}
