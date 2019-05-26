package com.efnilite.redaktor;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.queue.types.BlockQueue;
import com.efnilite.redaktor.util.ChangeAllocator;
import com.efnilite.redaktor.util.Configuration;
import com.efnilite.redaktor.util.Reflect;
import com.efnilite.redaktor.util.getter.AsyncBlockGetter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Redaktor extends JavaPlugin implements Listener {

    private static Thread main;
    private static Plugin plugin;
    private static Editor editor;
    private static Configuration configuration;
    private static ChangeAllocator allocator;

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
        editor = new Editor();
        main = Thread.currentThread();

        this.getServer().getPluginManager().registerEvents(this, this);
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

    public static Configuration getConfiguration() {
        return configuration;
    }

    public static ChangeAllocator getAllocator() {
        return allocator;
    }

    @EventHandler
    public void onC(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        if (e.getMessage().contains("set")) {
            BlockQueue queue = new BlockQueue();
            queue.setMaterial(Material.AIR);
            new AsyncBlockGetter(player.getLocation(), player.getLocation().clone().add(100, 20, 100), queue::build);
        } else if (e.getMessage().contains("eh")) {
            Cuboid cuboid = new Cuboid(player.getLocation(), player.getLocation().clone().add(20, 20, 20), player.getWorld());
            cuboid.getBlocks().forEach(t -> getLogger().info(t.getLocation().toString()));
        }
    }
}
