package com.efnilite.redaktor.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

/**
 * Main utilities
 */
public class Util {

    private static Random random;
    private static String[] halfs;
    private static String[] facings;

    /**
     * Avoid instances
     */
    private Util() {

    }

    static {
        random = new Random();
        halfs = new String[] { "bottom", "top" };
        facings = new String[] { "north", "east", "south", "west" };
    }

    /**
     * Set a final field to a value
     *
     * @param   field
     *          The field
     *
     * @param   value
     *          The value to what it's going to be set to
     *
     * @throws  NoSuchFieldException
     *          When there is no such field
     *
     * @throws  IllegalAccessException
     *          If something else goes wrong
     */
    public static void setFinal(Field field, Object value) throws NoSuchFieldException, IllegalAccessException {
        field.setAccessible(true);

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        field.set(null, value);
    }

    /**
     * Turns a Location into a String used by {@link com.efnilite.redaktor.schematic.Schematic}
     *
     * @param   location
     *          The location to be turned into a string
     *
     * @return  the location in string form
     */
    public static String toDeserializableString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ() + ", " + location.getWorld().getName()).replace(".0", "");
    }

    /**
     * Turns a String into a Location used by {@link com.efnilite.redaktor.schematic.Schematic}
     *
     * @param   string
     *          The string
     *
     * @return  The location from the string
     */
    public static Location fromDeserializableString(String string) {
        String[] elements = string.replaceAll(" ", "").split(",");
        return new Location(Bukkit.getWorld(elements[3]), Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
    }

    /**
     * Returns a zero'd location.
     *
     * @return a location where everything is 0
     */
    public static Location zero() {
        return new Location(null, 0, 0, 0);
    }

    /**
     * Turns a String into a location
     *
     * @param   string
     *          The string (looks like x,y,z)
     *
     * @return  the Location from the String
     */
    public static Location fromString(String string) {
        String[] elements = string.split(",");
        if (elements.length == 3) {
            return new Location(null, Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
        } else {
            return zero();
        }
    }

    /**
     * Formats a material to be more readable
     *
     * @param   material
     *          The material
     *
     * @return  a readable version of the material
     */
    public static String format(Material material) {
        return material.name().toLowerCase().replaceAll("_", " ");
    }

    /**
     * Turns a location into a readable string
     *
     * @param   location
     *          The location
     *
     * @return  an easy to read string
     */
    public static String toString(Location location) {
        return (location.getX() + ", " + location.getY() + ", " + location.getZ()).replaceAll("(\\.\\d+)", "");
    }

    /**
     * Randomize blockdata
     *
     * @param   string
     *          The blockdata
     *
     * @return  the blockdata with random data
     */
    public static String randomizeData(String string) {
        return randomizeHalfs(randomizeFacings(randomizeBooleans(string)));
    }

    /**
     * Randomizes halfs in a String, used for the '&' operator in patterns
     *
     * @param   string
     *          The string
     *
     * @return  the string with random halfs
     */
    public static String randomizeHalfs(String string) {
        return string.toLowerCase().replaceAll("(bottom|top)", halfs[random.nextInt(halfs.length - 1)]);
    }

    /**
     * Randomizes facings in a String, used for the '&' operator in patterns
     *
     * @param   string
     *          The string
     *
     * @return  the string with random facings
     */
    public static String randomizeFacings(String string) {
        return string.toLowerCase().replaceAll("(north|east|south|west)", facings[random.nextInt(facings.length - 1)]);
    }

    /**
     * Randomizes booleans in a String, used for the '&' operator in patterns
     *
     * @param   string
     *          The string
     *
     * @return  the string with random booleans
     */
    public static String randomizeBooleans(String string) {
        return string.toLowerCase().replaceAll("(true|false)", Boolean.toString(Booleans.values()[random.nextInt(1)].getValue()).toLowerCase());
    }

    /**
     * An enum for randomizing booleans
     */
    private enum Booleans {
        TRUE(true),
        FALSE(false);

        private boolean value;

        Booleans(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    }
}