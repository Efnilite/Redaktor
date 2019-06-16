package com.efnilite.redaktor.util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Calculates TPS to get a block/sec change limit.
 */
public class ChangeLocator extends BukkitRunnable {

    private int change;
    private double locator;
    private long lastNano;

    public ChangeLocator() {
        this.locator = 1000.0;
        this.change = (int) locator;
        this.lastNano = System.currentTimeMillis();

        Tasks.repeatAsync(this, 4);
    }

    @Override
    public void run() {
        long diff = System.currentTimeMillis() - lastNano;
        lastNano = System.currentTimeMillis();

        double tps = (locator / diff) * 20.00;
        tps = (tps > 20.00) ? 20.00 : tps;

        if (tps > 0 && tps < 18) {
            change = (int) Math.round(tps * (locator / 18.0));
        } else if (tps > 18) {
            change = (int) locator;
        }
    }

    public int getChanger() {
        return change;
    }
}
