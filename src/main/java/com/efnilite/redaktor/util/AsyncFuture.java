package com.efnilite.redaktor.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * A wrapper of CompletableFuture, but easier for users.
 *
 * @param   <T>
 *          The return type.
 */
public class AsyncFuture<T> extends CompletableFuture<T> {

    @Override
    public T get() {
        try {
            return super.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) {
        try {
            return super.get(timeout, unit);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }
}
