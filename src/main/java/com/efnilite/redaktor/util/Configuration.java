package com.efnilite.redaktor.util;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Configuration {

    private HashMap<String, FileConfiguration> files = new HashMap<>();
    private static Plugin plugin;

    public Configuration() {
        plugin = Redaktor.getInstance();
        plugin.getDataFolder().mkdir();

        if (!new File(plugin.getDataFolder().toString() + "/permissions.yml").exists()) {
            plugin.saveResource("config.yml", false);
            plugin.saveResource("permissions.yml", false);
        }
    }

    @SuppressWarnings("Duplicates")
    public String getString(String file, String path) {
        FileConfiguration config;
        if (files.get(file) == null) {
            config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + file));
            files.put(file, config);
        } else {
            config = files.get(file);
        }
        return ChatColor.translateAlternateColorCodes('&', config.getString(path));
    }

    @SuppressWarnings("Duplicates")
    public List<String> getStringList(String file, String path) {
        FileConfiguration config;
        if (files.get(file) == null) {
            config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + file));
            files.put(file, config);
        } else {
            config = files.get(file);
        }
        List<String> values = new ArrayList<>();
        for (String string : config.getStringList(path)) {
            values.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return values;
    }

    public FileConfiguration getFile(String file) {
        FileConfiguration config;
        if (files.get(file) == null) {
            config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + file));
            files.put(file, config);
        } else {
            config = files.get(file);
        }
        return config;
    }
}