package dev.efnilite.redaktor.util.web;

import dev.efnilite.redaktor.Redaktor;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

public class UpdateChecker {

    private Plugin plugin;

    public UpdateChecker() {
        this.plugin = Redaktor.getInstance();
    }

    public boolean check() {
        String latest;
        try {
            latest = this.getLatestVersion();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (!plugin.getDescription().getVersion().equals(latest)) {
            plugin.getLogger().info("A new version of Redaktor is available to download!");
            plugin.getLogger().info("Newest version: " + latest);
            return true;
        } else {
            plugin.getLogger().info("Redaktor is currently up-to-date!");
            return false;
        }
    }

    public String getLatestVersion() throws IOException {
        InputStream stream;

        try {
            stream = new URL("https://raw.githubusercontent.com/Efnilite/Redaktor/master/src/main/resources/plugin.yml").openStream();
        } catch (IOException e) {
            plugin.getLogger().info("Unable to check for updates!");
            return "";
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String version = reader.lines()
                .filter(s -> s.contains("version: "))
                .collect(Collectors.toList())
                .get(0)
                .replace("version: ", "");
        stream.close();
        reader.close();
        return version;
    }
}