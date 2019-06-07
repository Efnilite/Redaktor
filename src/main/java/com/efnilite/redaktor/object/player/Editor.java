package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.Redaktor;
import com.efnilite.redaktor.block.IBlockFactory;
import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.pattern.Pattern;
import com.efnilite.redaktor.object.queue.types.BlockQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid2DResizeQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid3DResizeQueue;
import com.efnilite.redaktor.object.queue.types.SlowBlockQueue;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Editor<T extends CommandSender> {

    private T sender;
    private int change;
    private int maxChange;
    private World world;
    private IBlockFactory tools;

    public Editor(T sender) {
        this.change = 0;
        this.maxChange = -1;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        if (sender instanceof Player) {
            this.world = ((Player) sender).getWorld();
        }
    }

    public Editor(T sender, World world) {
        this.change = 0;
        this.maxChange = -1;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        this.world = world;
    }

    public Editor(T sender, World world, int maxChange) {
        this.change = 0;
        this.tools = Redaktor.getBlockFactory();
        this.sender = sender;
        this.world = world;
        this.maxChange = maxChange;
    }

    /**
     * Creates a Pyramid at a location.
     *
     * @param   position
     *          The position of where the pyramid center will be.
     *
     * @param   pattern
     *          The pattern the pyramid will follow.
     *
     * @param   size
     *          The size (height) of the pyramid.
     */
    public void createPyramid(Location position, Pattern pattern, int size) {
        int currentSize = size;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < currentSize; x++) {
                for (int z = 0; z < currentSize; z++) {
                    tools.setBlock(position.clone().add(x, y, z).getBlock(), pattern);
                    tools.setBlock(position.clone().add(-x, y, z).getBlock(), pattern);
                    tools.setBlock(position.clone().add(x, y, -z).getBlock(), pattern);
                    tools.setBlock(position.clone().add(-x, y, -z).getBlock(), pattern);
                    change += 4;
                }
            }
            currentSize--;
        }
    }

    /**
     * Set all blocks in a Cuboid to a certain material.
     *
     * @param   pattern
     *          The pattern the blocks need to be changed to.
     *
     * @param   cuboid
     *          The cuboid.
     */
    public void setBlocks(Pattern pattern, Cuboid cuboid) {
        BlockQueue queue = new BlockQueue(pattern);
        change += queue.build(cuboid);
    }

    /**
     * Set all blocks slowly in a Cuboid to a certain material.
     *
     * @param   pattern
     *          The pattern the blocks need to be changed to.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   perTick
     *          The amount of blocks that need to be changed per tick.
     */
    public void setSlowBlocks(Pattern pattern, Cuboid cuboid, int perTick) {
        SlowBlockQueue queue = new SlowBlockQueue(perTick, pattern);
        change += queue.build(cuboid);
    }

    /**
     * Copies a Cuboid in a 2-dimensional way.
     *
     * @see #copyCuboid(Cuboid, int, int, int) for a 3D way of doing this.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   x
     *          The amount of times the cuboid needs to be copied in the x-value.
     *
     * @param   z
     *          The amount of times the cuboid needs to be copied in the z-value.
     */
    public void copyCuboid(Cuboid cuboid, int x, int z) {
        if (x > 0 && z > 0) {
            Cuboid2DResizeQueue queue = new Cuboid2DResizeQueue(x, z);
            change += queue.build(cuboid);
        } else {
            throw new IllegalStateException("x and z need to be above 0");
        }
    }

    /**
     * Copies a Cuboid in a 3-dimensional way.
     *
     * @see #copyCuboid(Cuboid, int, int) for a 2D way of doing this.
     *
     * @param   cuboid
     *          The cuboid.
     *
     * @param   x
     *          The amount of times the cuboid needs to be copied in the x-value.
     *
     * @param   y
     *          The amount of times the cuboid needs to be copied in the y-value.
     *
     * @param   z
     *          The amount of times the cuboid needs to be copied in the z-value.
     */
    public void copyCuboid(Cuboid cuboid, int x, int y, int z) {
        if (x > 0 && y > 0 && z > 0) {
            Cuboid3DResizeQueue queue = new Cuboid3DResizeQueue(x, y, z);
            change += queue.build(cuboid);
        } else {
            throw new IllegalStateException("x, y and z need to be above 0");
        }
    }

    /**
     * Get a block at a location.
     *
     * @see #getBlock(Location)
     *
     * @param   x
     *          The x-value.
     *
     * @param   y
     *          The y-value.
     *
     * @param   z
     *          The z-value.
     *
     * @return  The block at the location.
     */
    public Block getBlock(int x, int y, int z) {
        return this.getBlock(new Location(world, x, y, z));
    }

    /**
     * Get a block from a location.
     *
     * @param   location
     *          The location.
     *
     * @return  The block at the location.
     */
    public Block getBlock(Location location) {
        return world.getBlockAt(location);
    }
}
