package com.efnilite.redaktor;

import com.efnilite.redaktor.object.queue.BlockQueue;
import com.efnilite.redaktor.object.schematic.Schematic;
import com.efnilite.redaktor.object.schematic.WritableBlock;
import com.efnilite.redaktor.util.getter.BlockGetter;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Reflect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class Redaktor extends JavaPlugin implements Listener {

    private static Thread main;
    private static Plugin plugin;
    private static Editor editor;
    private static ChangeAllocator allocator;

    @Override
    public void onEnable() {
        plugin = this;

        String version = Reflect.getVersion();

        if (!version.equals("v1_14_R1")) {
            this.getLogger().severe("Redaktor only works on 1.14.x");
            this.getServer().getPluginManager().disablePlugin(this);
        }

        allocator = new ChangeAllocator();
        editor = new Editor();
        main = Thread.currentThread();

        this.getServer().getPluginManager().registerEvents(this, this);











        try {
            new Schematic().save("plugins/one.json", new WritableBlock[] {new WritableBlock(Material.AIR, null, 0, 0, 0),
                    new WritableBlock(Material.GOLD_BLOCK, null, 1, 0, 0)});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == main;
    }

    public static Plugin getInstance() {
        return plugin;
    }

    public static Editor getEditor() {
        return editor;
    }

    public static ChangeAllocator getAllocator() {
        return allocator;
    }

    @EventHandler
    public void onC(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (e.getMessage().contains("set")) {
            BlockQueue queue = new BlockQueue();
            BlockGetter getter = new BlockGetter(player.getLocation(), player.getLocation().clone().add(20, 20, 50));
            getter.setQueue(queue);
        } else if (e.getMessage().contains("eh")) {
            try {
                new Schematic().paste("plugins/one.json", player.getLocation());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
