package dev.efnilite.redaktor.util;

import dev.efnilite.redaktor.schematic.Schematic;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

/**
 * Main utilities
 */
public class Util {

    private static final Random random;
    private static final String[] halfs;
    private static final String[] facings;
    private static final Thread thread;

    /**
     * Avoid instances
     */
    private Util() {

    }

    static {
        thread = Thread.currentThread();
        random = new Random();
        halfs = new String[] { "bottom", "top" };
        facings = new String[] { "north", "east", "south", "west" };
    }

    /**
     * Checks if the code currently running is in the main thread
     *
     * @return true if the code is in the main thread
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == thread;
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
     * Turns a Location into a String used by {@link Schematic}
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
     * Turns a String into a Location used by {@link Schematic}
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
     * Gets the max of the locations
     *
     * @param   pos1
     *          The first location
     *
     * @param   pos2
     *          The second location
     *
     * @return  the max values of the locations
     */
    public static Location max(Location pos1, Location pos2) {
        World world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        return new Location(world, Math.max(pos1.getBlockX(), pos2.getBlockX()), Math.max(pos1.getBlockY(), pos2.getBlockY()), Math.max(pos1.getBlockZ(), pos2.getBlockZ()));
    }

    /**
     * Gets the min of the locations
     *
     * @param   pos1
     *          The first location
     *
     * @param   pos2
     *          The second location
     *
     * @return  the min values of the locations
     */
    public static Location min(Location pos1, Location pos2) {
        World world = pos1.getWorld() == null ? pos2.getWorld() : pos1.getWorld();
        return new Location(world, Math.min(pos1.getBlockX(), pos2.getBlockX()), Math.min(pos1.getBlockY(), pos2.getBlockY()), Math.min(pos1.getBlockZ(), pos2.getBlockZ()));
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

        private final boolean value;

        Booleans(boolean value) {
            this.value = value;
        }

        public boolean getValue() {
            return value;
        }
    }
}