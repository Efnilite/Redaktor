package com.efnilite.redaktor.util.web;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

/**
 * Checks for updates.
 */
public class UpdateChecker {

    /**
     * The plugin instance
     */
    private Plugin plugin;

    /**
     * Creates a new instance
     */
    public UpdateChecker() {
        this.plugin = Redaktor.getInstance();
    }

    /**
     * Checks the version
     *
     * @return true if it can be updated
     */
    public boolean check() {
        String latest = this.getLatestVersion();
        if (!plugin.getDescription().getVersion().equals(latest)) {
            plugin.getLogger().info("A new version of Redaktor is available to download!");
            plugin.getLogger().info("Newest version: " + latest);
            return true;
        } else {
            plugin.getLogger().info("Redaktor is currently up-to-date!");
            return false;
        }
    }

    /**
     * Gets the latest version from github
     *
     * @return the latest version
     */
    public String getLatestVersion() {
        InputStream stream;

        try {
            stream = new URL("https://raw.githubusercontent.com/Efnilite/Redaktor/master/src/main/resources/plugin.yml").openStream();
        } catch (IOException e) {
            plugin.getLogger().info("Unable to check for updates!");
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines().filter(s -> s.contains("version: ")).collect(Collectors.toList()).get(0).replace("version: ", "");
    }
}