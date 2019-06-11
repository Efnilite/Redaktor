package com.efnilite.redaktor;

import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.block.server.BlockFactory_v131;
import com.efnilite.redaktor.block.server.BlockFactory_v141;
import com.efnilite.redaktor.command.Commandable;
import com.efnilite.redaktor.command.RedaktorCommands;
import com.efnilite.redaktor.command.SelectionCommands;
import com.efnilite.redaktor.command.util.CommandFactory;
import com.efnilite.redaktor.object.player.PlayerFactory;
import com.efnilite.redaktor.object.player.PlayerListener;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.Reflect;
import com.efnilite.redaktor.util.web.Metrics;
import com.efnilite.redaktor.util.web.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Metrics metrics;
    private static ChangeAllocator allocator;
    private static Configuration configuration;

    private static IBlockFactory blockFactory;
    private static PlayerFactory playerFactory;
    private static CommandFactory commandFactory;

    @Override
    public void onEnable() {
        plugin = this;

        String version = Reflect.getVersion();

        if (version.equals("v1_14_R1")) {
            blockFactory = new BlockFactory_v141();
        } else if (!version.equals("v1_13_R2")) {
            blockFactory = new BlockFactory_v131();
        } else {
            this.getLogger().severe("Redaktor only works on 1.14.x and 1.13.x (not 1.13)");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        configuration = new Configuration();
        allocator = new ChangeAllocator();

        commandFactory = new CommandFactory();
        playerFactory = new PlayerFactory();

        metrics = new Metrics(this);

        new UpdateChecker();
        new RedaktorAPI();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.register(player);
            }
        }

        Commandable[] commandables = new Commandable[] { new RedaktorCommands(), new SelectionCommands() };
        for (Commandable commandable : commandables) {
            commandFactory.registerClass(commandable);
        }
    }

    @Override
    public void onDisable() {
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.unregister(player);
            }
        }
    }

    public static CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public static IBlockFactory getBlockFactory() {
        return blockFactory;
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static Metrics getMetrics() {
        return metrics;
    }

    public static ChangeAllocator getAllocator() {
        return allocator;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static PlayerFactory getPlayerFactory() {
        return playerFactory;
    }
}