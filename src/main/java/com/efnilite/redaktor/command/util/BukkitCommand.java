package com.efnilite.redaktor.command.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * A custom command wrapper
 *
 * @author Efnilite
 */
public class BukkitCommand extends Command {

    private CommandExecutor executor;

    public BukkitCommand(String label, CommandExecutor executor) {
        super(label);
        this.executor = executor;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        executor.onCommand(sender, this, label, args);
        return true;
    }
}