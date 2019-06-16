package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;

public class Reflect {

    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }
}
