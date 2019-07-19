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

/**
 * An utilities class for the Configuration
 */
public class Configuration {

    private Plugin plugin;
    private HashMap<String, FileConfiguration> files;

    public Configuration() {
        this.plugin = Redaktor.getInstance();
        this.files = new HashMap<>();

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
            this.plugin.saveResource("config.yml", false);
            this.plugin.saveResource("lang.yml", false);
        }

        this.files.put("config", this.getFile("config.yml"));
        this.files.put("lang", this.getFile("lang.yml"));
    }

    /**
     * Get a string which has already been coloured
     *
     * @param   file
     *          The file without plugin dir
     *
     * @param   path
     *          The YAML path
     *
     * @return  a colourized string
     */
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

    /**
     * Get a string list which has already been coloured
     *
     * @param   file
     *          The file without plugin dir
     *
     * @param   path
     *          The YAML path
     *
     * @return  a colourized string list
     */
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

    /**
     * Get the language used by the lang file
     *
     * @return the language
     */
    public String getLanguage() {
        return this.files.get("config").getString("language").toLowerCase();
    }

    public FileConfiguration getFile(String file) {
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + file));
    }
}