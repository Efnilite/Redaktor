package dev.efnilite.redaktor.util.thread;

import dev.efnilite.redaktor.Redaktor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Utilities for Runnables
 *
 * @author Efnilite
 */
public class Tasks {

    private static Plugin plugin;

    static {
        plugin = Redaktor.getInstance();
    }

    public static int repeat(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimer(plugin, 0L, interval).getTaskId();
    }

    public static int repeatAsync(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimerAsynchronously(plugin, 0L, interval).getTaskId();
    }

    public static int async(BukkitRunnable runnable) {
        return runnable.runTaskAsynchronously(plugin).getTaskId();
    }

    public static int delayAsync(BukkitRunnable runnable, int delay) {
        return runnable.runTaskLaterAsynchronously(plugin, delay).getTaskId();
    }

    public static int delayTask(BukkitRunnable runnable, int delay) {
        return runnable.runTaskLater(plugin, delay).getTaskId();
    }

    public static int task(BukkitRunnable runnable) {
        return runnable.runTask(plugin).getTaskId();
    }
}