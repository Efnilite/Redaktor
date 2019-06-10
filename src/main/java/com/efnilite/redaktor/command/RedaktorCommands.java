package com.efnilite.redaktor.command;

import com.efnilite.redaktor.command.util.Command;
import org.bukkit.command.CommandSender;

public class RedaktorCommands implements Commandable {

    @Command()
    public void redaktor(CommandSender sender, String[] args) {
        sender.sendMessage("Yeet!");
    }

    @Override
    public Class<?> getTypeClass() {
        return RedaktorCommands.class;
    }
}