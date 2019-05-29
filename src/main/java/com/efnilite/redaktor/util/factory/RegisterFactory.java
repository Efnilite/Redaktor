package com.efnilite.redaktor.util.factory;

public interface RegisterFactory<T> {

    void register(T object);

    void unregister(T object);

}