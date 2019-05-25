package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;

public class Reflect {

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private static String getReflectionVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
    }

    public static boolean methodExists(Class<?> c, String methodName, Class<?>... parameterTypes) {
        try {
            c.getDeclaredMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException | SecurityException e) {
            return false;
        }
    }

    public static boolean classExists(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static Class<?> getCraftClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getReflectionVersion() + name);
        } catch (ClassNotFoundException e) {
            return getClass("");
        }
    }

    public static Class<?> getMinecraftClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getReflectionVersion() + name);
        } catch (ClassNotFoundException e) {
            return getClass("");
        }
    }

    public static Class<?> getClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            return getClass("");
        }
    }

}
