package com.efnilite.redaktor;

import com.efnilite.redaktor.object.player.PlayerEvents;
import com.efnilite.redaktor.object.player.PlayerFactory;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.Reflect;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Configuration configuration;
    private static ChangeAllocator allocator;

    private static PlayerFactory playerFactory;

    @Override
    public void onEnable() {
        plugin = this;

        String version = Reflect.getVersion();

        if (!version.equals("v1_14_R1")) {
            this.getLogger().severe("Redaktor only works on 1.14.x");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        configuration = new Configuration();
        allocator = new ChangeAllocator();

        playerFactory = new PlayerFactory();

        this.getServer().getPluginManager().registerEvents(new PlayerEvents(), this);
    }

    public static Plugin getInstance() {
        return plugin;
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