package dev.efnilite.redaktor.command.util;

import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.wrapper.RedaktorPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class CommandFactory implements CommandExecutor {

    private Plugin plugin;
    private CommandMap map;
    private HashMap<String, CommandInstanceMap> methods;

    public CommandFactory() {
        this.plugin = Redaktor.getInstance();
        this.methods = new HashMap<>();
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            this.map = (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void registerClass(Commandable commandable) {
        Class<?> clazz = commandable.getClass();
        for (Method method : clazz.getMethods()) {
            if (method.getAnnotation(Command.class) != null) {
                Command command = method.getAnnotation(Command.class);
                String name = method.getName().toLowerCase();
                BukkitCommand pluginCommand = new BukkitCommand(name, this);
                if (!command.description().equals("")) {
                    pluginCommand.setDescription(command.description());
                }
                if (!command.permission().equals("")) {
                    pluginCommand.setPermission(command.permission());
                }
                if (!command.permissionMessage().equals("")) {
                    pluginCommand.setPermissionMessage(command.permissionMessage());
                }
                if (!command.usage().equals("")) {
                    pluginCommand.setUsage(command.usage());
                }
                map.register(plugin.getName().toLowerCase(), pluginCommand);
                methods.put(name, new CommandInstanceMap(method, commandable));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] strings) {
        String commandName = label;
        if (label.contains(plugin.getName().toLowerCase() + ":")) {
            commandName = label.split(":")[1];
        }

        CommandInstanceMap map = methods.get(commandName);
        Method method = map.getMethod();
        Command annotation = method.getAnnotation(Command.class);

        if (!sender.hasPermission(annotation.permission())) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', annotation.permissionMessage()));
            return false;
        }

        try {
            method.invoke(map.getCommandable(), RedaktorPlayer.wrap(sender), strings);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}