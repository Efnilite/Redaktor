package com.efnilite.redaktor.command.util;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.command.Commandable;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * The main reference class for registering commands
 * using methods.
 */
public class CommandFactory implements CommandExecutor {

    private CommandMap map;
    private HashMap<String, CommandInstanceMap> methods;

    public CommandFactory() {
        methods = new HashMap<>();
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            map = (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Register a Commandable class in which all methods will
     * be scanned for the 'Command' annotation.
     *
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
                map.register("redaktor", pluginCommand);
                methods.put(name, new CommandInstanceMap(method, commandable));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] strings) {
        String commandName = label;
        if (label.contains("redaktor:")) {
            commandName = label.split(":")[1];
        }

        if (strings == null) {
            Redaktor.getInstance().getLogger().info("strings");
        }

        try {
            CommandInstanceMap map = methods.get(commandName);
            Method method = map.getMethod();
            method.invoke(map.getCommandable(), sender, strings);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return true;
    }
}