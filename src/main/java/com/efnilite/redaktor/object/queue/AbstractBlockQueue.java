package com.efnilite.redaktor.object.queue;

import com.efnilite.redaktor.object.pattern.Pattern;

public abstract class AbstractBlockQueue {

    protected Pattern pattern;

    public AbstractBlockQueue(Pattern pattern) {
        this.pattern = pattern;
    }
}
