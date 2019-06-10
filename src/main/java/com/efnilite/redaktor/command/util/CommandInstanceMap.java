package com.efnilite.redaktor.command.util;

import com.efnilite.redaktor.command.Commandable;

import java.lang.reflect.Method;

public class CommandInstanceMap {

    private Method method;
    private Commandable commandable;

    public CommandInstanceMap(Method method, Commandable commandable) {
        this.method = method;
        this.commandable = commandable;
    }

    public Method getMethod() {
        return method;
    }

    public Commandable getCommandable() {
        return commandable;
    }
}
