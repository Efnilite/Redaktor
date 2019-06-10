package com.efnilite.redaktor.command;

/**
 * A class for registering custom commands with methods.
 */
public interface Commandable {

    /**
     * Gets the class (not instance) of the Commandable.
     *
     * @return the class
     */
    Class<?> getTypeClass();

}
