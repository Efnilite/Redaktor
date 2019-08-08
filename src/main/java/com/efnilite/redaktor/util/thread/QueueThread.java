package com.efnilite.redaktor.util.thread;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A class for processing BukkitRunnables as a Thread to avoid creating a new
 * thread every time someone wants to run an operation.
 */
public class QueueThread extends Thread {

    /**
     * The queue
     */
    public LinkedBlockingQueue<BukkitRunnable> queue;

    /**
     * Creates a new instance
     *
     * @param   runnables
     *          The runnables to be run at the start
     */
    public QueueThread(BukkitRunnable... runnables) {
        if (runnables.length != 0) {
            this.queue = new LinkedBlockingQueue<>(Arrays.asList(runnables));
        } else {
            this.queue = new LinkedBlockingQueue<>();
        }

        this.start();
    }

    /**
     * Queue an array of runnables to be run
     *
     * @param runnables the runnables
     */
    public void queue(BukkitRunnable... runnables) {
        for (BukkitRunnable runnable : runnables) {
            queue.offer(runnable);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                BukkitRunnable runnable = queue.take();
                runnable.run();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}