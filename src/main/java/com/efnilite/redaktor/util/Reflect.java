package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class Reflect {

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static void setField(Field field, Object target, Object value) {
        field.setAccessible(true);
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
