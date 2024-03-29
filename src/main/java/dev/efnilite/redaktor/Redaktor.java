package dev.efnilite.redaktor;

import dev.efnilite.redaktor.block.BlockFactory;
import dev.efnilite.redaktor.block.BlockFactory_v1_18_R1;
import dev.efnilite.redaktor.command.*;
import dev.efnilite.redaktor.command.util.CommandFactory;
import dev.efnilite.redaktor.command.util.Commandable;
import dev.efnilite.redaktor.player.PlayerListener;
import dev.efnilite.redaktor.schematic.structure.StructureManager;
import dev.efnilite.redaktor.util.ChangeAllocator;
import dev.efnilite.redaktor.util.Configuration;
import dev.efnilite.redaktor.util.factory.PlayerFactory;
import dev.efnilite.redaktor.util.web.Metrics;
import dev.efnilite.redaktor.util.web.UpdateChecker;
import dev.efnilite.redaktor.wrapper.console.ConsolePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The main plugin class
 */
public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Metrics metrics;
    private static UpdateChecker checker;
    private static ChangeAllocator allocator;
    private static Configuration configuration;

    private static StructureManager manager;
    private static BlockFactory blockFactory;
    private static PlayerFactory playerFactory;
    private static CommandFactory commandFactory;

    private static ConsolePlayer console;

    private static boolean isLatest;

    public static String PREFIX = "&8&l(&c&lRedaktor&8&l)";
    public static String SCHEMATIC_VERSION = "1";

    @Override
    public void onEnable() {
        plugin = this;

        String version = this.getVersion();
        this.getLogger().info("Registered under server version " + version);

        blockFactory = new BlockFactory_v1_18_R1();
        isLatest = true;

        configuration = new Configuration();
        allocator = new ChangeAllocator();
        checker = new UpdateChecker();
        playerFactory = new PlayerFactory();
        commandFactory = new CommandFactory();

        metrics = new Metrics(this);
        console = new ConsolePlayer(this.getServer().getConsoleSender());

        new RedaktorAPI();

        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.register(player);
            }
        }

        Commandable[] commandables = new Commandable[] {
                new RedaktorCommands(), new EditorCommands(), new SuperItemCommands(),
                new SchematicCommands(), new CuboidCommands()};
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

    public static StructureManager getStructureManager() {
        return manager;
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