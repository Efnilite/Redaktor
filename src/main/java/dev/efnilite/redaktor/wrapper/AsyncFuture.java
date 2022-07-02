package dev.efnilite.redaktor.wrapper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Removes the exceptions from the {@link CompletableFuture#get()} method
 *
 * @param <T> the type
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
}
