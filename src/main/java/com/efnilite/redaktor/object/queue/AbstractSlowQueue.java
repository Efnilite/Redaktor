package com.efnilite.redaktor.object.queue;

public abstract class AbstractSlowQueue {

    protected int perTick;

    public AbstractSlowQueue(int perTick) {
        this.perTick = perTick;

    }
}
