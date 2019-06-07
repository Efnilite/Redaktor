package com.efnilite.redaktor.util.getter;

import com.efnilite.redaktor.util.Tasks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * A class for getting connected blocks from a specific location.
 *
 * Idea by https://gitlab.com/Moderocky
 */
public class AsyncConnectedGetter extends BukkitRunnable implements AsyncGetter<List<Block>> {

    private Location pos;
    private Consumer<List<Block>> consumer;

    public AsyncConnectedGetter(Location pos, Consumer<List<Block>> consumer) {
        this.pos = pos;
        this.consumer = consumer;

        Tasks.async(this);
    }

    @Override
    public void run() {
        Material material = pos.getBlock().getType();
        List<Block> blocks = new ArrayList<>();
        List<Block> current = new ArrayList<>(this.getType(blocks, pos, material));
        blocks.addAll(current);

        while (true) {
            List<Block> add = new ArrayList<>();
            List<Boolean> complete = new ArrayList<>();
            for (Block block : current) {
                add.addAll(this.getType(blocks, block.getLocation(), material));
                if (add.size() == 0) {
                    complete.add(false);
                } else {
                    complete.add(true);
                }
                blocks.addAll(add);
                add.clear();
            }
            if (!complete.contains(true)) {
                asyncDone(blocks);
                return;
            }
            complete.clear();
            current.clear();
            current.addAll(add);
        }
    }

    @Override
    public void asyncDone(List<Block> value) {
        consumer.accept(value);
    }

    private List<Block> getType(List<Block> current, Location location, Material material) {
        List<Block> add = new ArrayList<>();
        Location position = location.clone().add(1, 1, 1);
        Location position2 = location.clone().subtract(1, 1, 1);
        int max = Math.max(position.getBlockX(), position2.getBlockX());
        int mix = Math.min(position.getBlockX(), position2.getBlockX());
        int may = Math.max(position.getBlockY(), position2.getBlockY());
        int miy = Math.min(position.getBlockY(), position2.getBlockY());
        int maz = Math.max(position.getBlockZ(), position2.getBlockZ());
        int miz = Math.min(position.getBlockZ(), position2.getBlockZ());
        for (int x = mix; x <= max; x++) {
            for (int y = miy; y <= may; y++) {
                for (int z = miz; z <= maz; z++) {
                    location.setX(x);
                    location.setY(y);
                    location.setZ(z);

                    if (location.getBlock().getType() == material && !current.contains(location.getBlock())) {
                        add.add(location.getBlock());
                    }
                }
            }
        }
        return add;
    }
}