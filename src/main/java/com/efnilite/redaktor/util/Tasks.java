package com.efnilite.redaktor.util;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * A class for running tasks
 */
public class Tasks {

    /**
     * An instance of the plugin to avoid calling it several times
     */
    private static Plugin plugin;

    static {
        plugin = Redaktor.getInstance();
    }

    /**
     * Avoid instances
     */
    private Tasks() {

    }

    /**
     * Create a new repeating sync task
     *
     * @param   runnable
     *          The runnable
     *
     * @param   interval
     *          The interval it should be repeated at
     *
     * @return  the id
     */
    public static int repeat(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimer(plugin, 0, interval).getTaskId();
    }

    /**
     * Create a new repeating async task
     *
     * @param   runnable
     *          The runnable
     *
     * @param   interval
     *          The interval it should be repeated at
     *
     * @return  the id
     */
    public static int repeatAsync(BukkitRunnable runnable, int interval) {
        return runnable.runTaskTimerAsynchronously(plugin, 0, interval).getTaskId();
    }

    /**
     * Create an async task
     *
     * @param   runnable
     *          The runnable
     *
     * @return  the id
     */
    public static int async(BukkitRunnable runnable) {
        return runnable.runTaskAsynchronously(plugin).getTaskId();
    }

    /**
     * Create a sync task
     *
     * @param   runnable
     *          The runnable
     *
     * @return  the id
     */
    public static int task(BukkitRunnable runnable) {
        return runnable.runTask(plugin).getTaskId();
    }
}