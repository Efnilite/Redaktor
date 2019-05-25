package com.efnilite.redaktor.util;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks {

    private static Plugin plugin;

    static {
        plugin = Redaktor.getInstance();
    }

    public static int repeat(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimer(plugin, 0, interval).getTaskId();
    }

    public static int repeatAsync(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimerAsynchronously(plugin, 0, interval).getTaskId();
    }

    public static int async(BukkitRunnable runnable) {
        return runnable.runTaskAsynchronously(plugin).getTaskId();
    }

    public static int task(BukkitRunnable runnable) {
        return runnable.runTask(plugin).getTaskId();
    }
}