package com.efnilite.redaktor.object.player;

import com.efnilite.redaktor.object.cuboid.Cuboid;
import com.efnilite.redaktor.object.pattern.Pattern;
import com.efnilite.redaktor.object.queue.types.BlockQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid2DResizeQueue;
import com.efnilite.redaktor.object.queue.types.Cuboid3DResizeQueue;
import com.efnilite.redaktor.object.queue.types.SlowBlockQueue;
import org.bukkit.command.CommandSender;

public class Editor<T extends CommandSender> {

    private T sender;

    public Editor(T sender) {
        this.sender = sender;
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
        queue.build(cuboid);
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
        queue.build(cuboid);
    }

    /**
     * Copies a Cuboid in a 2-dimensional way.
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
            queue.build(cuboid);
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
            queue.build(cuboid);
        } else {
            throw new IllegalStateException("x, y and z need to be above 0");
        }
    }
}
