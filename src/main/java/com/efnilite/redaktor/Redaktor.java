package com.efnilite.redaktor;

import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.block.server.BlockFactory_v131;
import com.efnilite.redaktor.block.server.BlockFactory_v141;
import com.efnilite.redaktor.object.player.PlayerEvents;
import com.efnilite.redaktor.object.player.PlayerFactory;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.Reflect;
import com.efnilite.redaktor.util.web.Metrics;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Metrics metrics;
    private static ChangeAllocator allocator;
    private static IBlockFactory blockFactory;
    private static Configuration configuration;
    private static PlayerFactory playerFactory;

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

        playerFactory = new PlayerFactory();
        configuration = new Configuration();
        allocator = new ChangeAllocator();
        blockFactory = new BlockFactory_v141();

        metrics = new Metrics(this);

        this.getLogger().warning("If you are reloading this server, Redaktor will break!");
        this.getLogger().warning("Please refrain from using /reload since it will break a lot of other plugins, too.");
        this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
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