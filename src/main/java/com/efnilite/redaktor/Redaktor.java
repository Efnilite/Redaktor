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
import com.efnilite.redaktor.util.factory.PlayerFactory;
import com.efnilite.redaktor.util.web.Metrics;
import com.efnilite.redaktor.util.web.UpdateChecker;
import com.efnilite.redaktor.wrapper.console.ConsolePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Redaktor extends JavaPlugin {

    private static Plugin plugin;
    private static Metrics metrics;
    private static UpdateChecker checker;
    private static ChangeAllocator allocator;

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
        }

        allocator = new ChangeAllocator();
        checker = new UpdateChecker();

        playerFactory = new PlayerFactory();
        commandFactory = new CommandFactory(this);

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
        if (Bukkit.getOnlinePlayers().size() > 0) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                playerFactory.unregister(player);
            }
        }
    }

    public static boolean isLatest() {
        return isLatest;
    }

    public static ConsolePlayer getConsolePlayer() {
        return console;
    }

    public static UpdateChecker getChecker() {
        return checker;
    }

    public static BlockFactory getBlockFactory() {
        return blockFactory;
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static ChangeAllocator getAllocator() {
        return allocator;
    }

    public static PlayerFactory getPlayerFactory() {
        return playerFactory;
    }

    private String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
}