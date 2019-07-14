package com.efnilite.redaktor.util;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * Calculates TPS to get a block/tick (1/20th of a second) change limit.
 */
public class ChangeAllocator extends BukkitRunnable {

    private int change;
    private long lastNano;
    private double locator;
    private double tps;

    public ChangeAllocator() {
        this.locator = 2000;
        this.change = (int) locator;
        this.lastNano = System.currentTimeMillis();

        Tasks.repeatAsync(this, 4);
    }

    @Override
    public void run() {
        long diff = System.currentTimeMillis() - lastNano;
        lastNano = System.currentTimeMillis();

        tps = (locator / diff) * 20.00;
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

    public double getTps() {
        return tps;
    }
}
