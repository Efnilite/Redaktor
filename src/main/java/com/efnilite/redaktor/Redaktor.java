package com.efnilite.redaktor;

import com.efnilite.redaktor.block.BlockFactory;
import com.efnilite.redaktor.block.server.BlockFactory_v131;
import com.efnilite.redaktor.block.server.BlockFactory_v141;
import com.efnilite.redaktor.command.EditorCommands;
import com.efnilite.redaktor.command.RedaktorCommands;
import com.efnilite.redaktor.command.SchematicCommands;
import com.efnilite.redaktor.command.SuperItemCommands;
import com.efnilite.redaktor.command.util.CommandFactory;
import com.efnilite.redaktor.command.util.Commandable;
import com.efnilite.redaktor.player.PlayerListener;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.factory.PlayerFactory;
import com.efnilite.redaktor.util.web.Metrics;
import com.efnilite.redaktor.util.web.UpdateChecker;
import com.efnilite.redaktor.wrapper.console.ConsolePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * The main plugin class
 */
public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Metrics metrics;
    private static UpdateChecker checker;
    private static ChangeAllocator allocator;
    private static Configuration configuration;

    private static BlockFactory blockFactory;
    private static PlayerFactory playerFactory;
    private static CommandFactory commandFactory;

    private static ConsolePlayer console;

    private static boolean isLatest;

    public static String PREFIX = "&8&l(&c&lRedaktor&8&l)";

    @Override
    public void onEnable() {
        plugin = this;

        String version = this.getVersion();

        if (version.equals("v1_14_R1")) {
            blockFactory = new BlockFactory_v141();
            isLatest = true;
        } else if (!version.equals("v1_13_R2")) {
            blockFactory = new BlockFactory_v131();
            isLatest = false;
        } else {
            this.getLogger().severe("Redaktor only works on 1.14.x and 1.13.x (not 1.13)");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        this.getLogger().info("Registered under server version " + version);

        configuration = new Configuration();
        allocator = new ChangeAllocator();
        checker = new UpdateChecker();
        playerFactory = new PlayerFactory();
        commandFactory = new CommandFactory();

        metrics = new Metrics(this);
        console = new ConsolePlayer(this.getServer().getConsoleSender());

        new RedaktorAPI();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (!this.getDataFolder().exists()) {
            File schematics = new File(this.getDataFolder().toString() + "/schematics/");
            schematics.mkdirs();
        }

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.register(player);
            }
        }

        Commandable[] commandables = new Commandable[] { new RedaktorCommands(), new EditorCommands(), new SuperItemCommands(), new SchematicCommands() };
        for (Commandable commandable : commandables) {
            commandFactory.registerClass(commandable);
        }

        checker.check();
    }

    @Override
    public void onDisable() {
        this.getServer().getScheduler().cancelTasks(this);

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.unregister(player);
            }
        }
    }

    /**
     * Checks if the server is running latest
     *
     * @return true if the server is running 1.14.x
     */
    public static boolean isLatest() {
        return isLatest;
    }

    /**
     * Gets the {@link Configuration}
     *
     * @return the {@link Configuration}
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the default {@link ConsolePlayer} instance
     *
     * @return the default {@link ConsolePlayer}
     */
    public static ConsolePlayer getConsolePlayer() {
        return console;
    }

    /**
     * Gets the {@link UpdateChecker}
     *
     * @return the {@link UpdateChecker}
     */
    public static UpdateChecker getChecker() {
        return checker;
    }

    /**
     * Gets the {@link BlockFactory}
     *
     * @return the {@link BlockFactory}
     */
    public static BlockFactory getBlockFactory() {
        return blockFactory;
    }

    /**
     * Gets the Plugin instance
     *
     * @return the instance
     */
    public static Plugin getInstance() {
        return plugin;
    }

    /**
     * Gets the {@link ChangeAllocator}
     *
     * @return the {@link ChangeAllocator}
     */
    public static ChangeAllocator getAllocator() {
        return allocator;
    }

    /**
     * Gets the {@link PlayerFactory}
     *
     * @return the {@link PlayerFactory}
     */
    public static PlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    /**
     * Get the net.minecraft.server version
     *
     * @return the version
     */
    private String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
}