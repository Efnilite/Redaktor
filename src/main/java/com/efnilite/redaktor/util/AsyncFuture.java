package com.efnilite.redaktor.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncFuture<T> extends CompletableFuture<T> {

    @Override
    public T get() {
        try {
            return super.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }
}
