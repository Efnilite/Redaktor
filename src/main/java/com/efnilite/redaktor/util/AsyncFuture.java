package com.efnilite.redaktor.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
            return null;
        }
    }
}
