package com.efnilite.connotations;

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

/**
 * The main reference class for registering commands
 * using methods.
 *
 * @author Efnilite
 */
public class CommandFactory implements CommandExecutor {

    private Plugin plugin;
    private CommandMap map;
    private HashMap<String, CommandInstanceMap> methods;

    /**
     * Creates a new CommandFactory
     *
     * @param   plugin
     *          The plugin using this.
     */
    public CommandFactory(Plugin plugin) {
        this.plugin = plugin;
        this.methods = new HashMap<>();
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            this.map = (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a Commandable class in which all methods will
     * be scanned for the 'Command' annotation.
     * <p>
     * If one of the methods contains one of those annotations it will register
     * using the method name as the main command name.
     *
     * @param   commandable
     *          The commandable instance.
     */
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
            method.invoke(map.getCommandable(), sender, strings);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}