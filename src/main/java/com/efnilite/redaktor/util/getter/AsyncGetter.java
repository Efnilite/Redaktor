package com.efnilite.redaktor.util.getter;

/**
 * An interface related to all asynchronous data getters,
 * which don't return as it should, since it's on a
 * different Thread.
 *
 * @param   <T>
 *          The return value when the asynchronous
 *          data collection is done.
 */
public interface AsyncGetter<T> {

    void asyncDone(T value);

}