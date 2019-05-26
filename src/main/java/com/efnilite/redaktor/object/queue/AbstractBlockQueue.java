package com.efnilite.redaktor.object.queue;

import org.bukkit.Material;

public abstract class AbstractBlockQueue {

    protected Material material;

    public AbstractBlockQueue(Material material) {
        this.material = material;
    }
}
