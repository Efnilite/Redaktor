package com.efnilite.redaktor.util;

import com.efnilite.redaktor.Redaktor;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Calculates TPS to get a block/tick (1/20th of a second) change limit.
 */
public class ChangeAllocator extends BukkitRunnable {

    private int change;
    private long lastNano;
    private double locator;
    private double tps;

    /**
     * Creates a new instance
     */
    public ChangeAllocator() {
        this.locator = Redaktor.getConfiguration().getFile("config.yml").getDouble("change");
        this.change = (int) locator;
        this.lastNano = System.currentTimeMillis();

        Tasks.repeatAsync(this, 4);
    }

    /**
     * The main code for calculating the TPS and the changer.
     * <p>
     * The TPS is calculated by the difference between the milliseconds of now and 4 seconds ago.
     * If the difference is small, this means that the server is operating at ~20 tps.
     * If the difference is bigger, this means that the server is operating at below 20 tps.
     * <p>
     * With the current code, it calculates the TPS by dividing the {@link #locator} var and the difference.
     */
    @Override
    public void run() {
        long diff = System.currentTimeMillis() - lastNano;
        lastNano = System.currentTimeMillis();

        tps = (locator / diff) * 2.0;

        change = (int) Math.round(tps * locator / 20);
    }

    /**
     * Get the amount of blocks the plugin is allowed to change per tick.
     *
     * @return the amount of blocks
     */
    public int getChanger() {
        return change;
    }

    /**
     * Get the TPS calculated by the plugin used for determining the {@link #change}
     *
     * @return the calculated TPS
     */
    public double getTps() {
        return tps;
    }
}
