package com.efnilite.redaktor.pattern;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for the # modifier in a {@link Pattern}
 * Used in {@link Pattern.Parser}
 *
 * List of usables:
 * - beds
 * - carpets
 * - glass
 * - glasspane/glass_pane
 * - logs
 * - ores
 * - planks
 * - slabs
 * - stones
 * - wool
 */
public enum BlockTypes {

    BEDS(
            Material.BLACK_BED, Material.BLUE_BED, Material.BROWN_BED, Material.CYAN_BED, Material.GREEN_BED, Material.LIME_BED, Material.MAGENTA_BED, Material.ORANGE_BED,
            Material.PINK_BED, Material.PURPLE_BED, Material.RED_BED, Material.WHITE_BED, Material.YELLOW_BED, Material.LIGHT_BLUE_BED, Material.LIGHT_GRAY_BED
    ),
    CARPETS(
            Material.BLACK_CARPET, Material.BLUE_CARPET, Material.BROWN_CARPET, Material.CYAN_CARPET, Material.GRAY_CARPET, Material.GREEN_CARPET, Material.LIGHT_BLUE_CARPET,
            Material.LIGHT_GRAY_CARPET, Material.LIME_CARPET, Material.MAGENTA_CARPET, Material.ORANGE_CARPET, Material.PINK_CARPET, Material.PURPLE_CARPET, Material.RED_CARPET,
            Material.WHITE_CARPET, Material.YELLOW_CARPET
    ),
    GLASS(
            Material.BLACK_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.GRAY_STAINED_GLASS, Material.GREEN_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.ORANGE_STAINED_GLASS,
            Material.PINK_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS
    ),
    GLASS_PANE(
            Material.BLACK_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE, Material.BROWN_STAINED_GLASS_PANE, Material.CYAN_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE,
            Material.GREEN_STAINED_GLASS_PANE, Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE,
            Material.ORANGE_STAINED_GLASS_PANE, Material.PINK_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.RED_STAINED_GLASS_PANE, Material.WHITE_STAINED_GLASS_PANE,
            Material.YELLOW_STAINED_GLASS_PANE
    ),
    LOGS(
            Material.ACACIA_LOG, Material.BIRCH_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG, Material.DARK_OAK_LOG
    ),
    ORES(
            Material.COAL_ORE, Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.GOLD_ORE, Material.IRON_ORE, Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.NETHER_QUARTZ_ORE
    ),
    PLANKS(
            Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS, Material.DARK_OAK_PLANKS
    ),
    SLABS(
            Material.ACACIA_SLAB, Material.ANDESITE_SLAB, Material.BIRCH_SLAB, Material.BRICK_SLAB, Material.COBBLESTONE_SLAB, Material.DIORITE_SLAB, Material.GRANITE_SLAB,
            Material.JUNGLE_SLAB, Material.OAK_SLAB, Material.PRISMARINE_SLAB, Material.PURPUR_SLAB, Material.QUARTZ_SLAB, Material.SANDSTONE_SLAB, Material.SPRUCE_SLAB,
            Material.STONE_SLAB, Material.CUT_SANDSTONE_SLAB, Material.DARK_OAK_SLAB, Material.DARK_PRISMARINE_SLAB, Material.MOSSY_COBBLESTONE_SLAB, Material.NETHER_BRICK_SLAB,
            Material.PETRIFIED_OAK_SLAB, Material.POLISHED_ANDESITE_SLAB, Material.POLISHED_DIORITE_SLAB, Material.POLISHED_GRANITE_SLAB, Material.PRISMARINE_BRICK_SLAB,
            Material.SMOOTH_RED_SANDSTONE_SLAB, Material.RED_NETHER_BRICK_SLAB, Material.END_STONE_BRICK_SLAB, Material.CUT_RED_SANDSTONE_SLAB, Material.STONE_BRICK_SLAB,
            Material.SMOOTH_QUARTZ_SLAB, Material.SMOOTH_SANDSTONE_SLAB, Material.SMOOTH_STONE_SLAB
    ),
    STONES(
            Material.STONE, Material.ANDESITE, Material.DIORITE
    ),
    WOOL(
            Material.BLACK_WOOL, Material.BLUE_WOOL, Material.BROWN_WOOL, Material.CYAN_WOOL, Material.GRAY_WOOL, Material.GREEN_WOOL, Material.LIGHT_BLUE_WOOL, Material.LIGHT_GRAY_WOOL,
            Material.LIME_WOOL, Material.MAGENTA_WOOL, Material.ORANGE_WOOL, Material.PINK_WOOL, Material.PURPLE_WOOL, Material.RED_WOOL,  Material.WHITE_WOOL, Material.YELLOW_WOOL
    );

    /**
     * The materials
     */
    private Material[] materials;

    /**
     * Creates a new instance
     *
     * @param   materials
     *          The materials
     */
    BlockTypes(Material... materials) {
        this.materials = materials;
    }

    /**
     * Gets the associated materials
     *
     * @return the materials
     */
    public List<BlockData> getMaterials() {
        List<BlockData> back = new ArrayList<>();
        for (Material material : materials) {
            back.add(material.createBlockData());
        }
        return back;
    }

    /**
     * Gets a BlockTypes instance from a name
     *
     * @param   name
     *          The name
     *
     * @return  a BlockTypes instance
     */
    public static BlockTypes getType(String name) {
        try {
            return BlockTypes.valueOf(name.toUpperCase().replaceAll(" ", "_"));
        } catch (IllegalArgumentException e) {
            return null;
        }
     }
}
