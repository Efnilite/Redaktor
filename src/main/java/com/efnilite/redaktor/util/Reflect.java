package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Reflect {

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    public static void setField(Field field, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, newValue);
    }
}