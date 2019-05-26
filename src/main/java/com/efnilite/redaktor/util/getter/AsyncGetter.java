package com.efnilite.redaktor.util.getter;

public interface AsyncGetter<T> {

    void asyncDone(T value);

}