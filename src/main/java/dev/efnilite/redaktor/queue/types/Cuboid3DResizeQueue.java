package dev.efnilite.redaktor.queue.types;

import dev.efnilite.redaktor.Editor;
import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.block.BlockFactory;
import dev.efnilite.redaktor.queue.EditQueue;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.util.getter.AsyncBlockGetter;
import dev.efnilite.redaktor.util.thread.Tasks;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for copying cuboids in a 3D way
 */
public class Cuboid3DResizeQueue implements EditQueue<Selection> {

    /**
     * The x modifier
     */
    private int x;

    /**
     * The y modifier
     */
    private int y;

    /**
     * The z modifier
     */
    private int z;

    /**
     * The editor
     */
    private Editor<?> editor;

    /**
     * Creates a new instance
     *
     * @param   editor
     *          The editor
     *
     * @param   x
     *          The x modifier
     *
     * @param   y
     *          The y modifier
     *
     * @param   z
     *          The z modifier
     */
    public Cuboid3DResizeQueue(Editor<?> editor, int x, int y, int z) {
        this.editor = editor;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int build(Selection selection) {
        BlockFactory factory = Redaktor.getBlockFactory();
        int x = selection.getDimensions().getWidth();
        int z = selection.getDimensions().getLength();

        new AsyncBlockGetter(selection, t -> {
            Queue<Block> queue = new LinkedList<>();
            Queue<Block> original = new LinkedList<>();
            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Cuboid3DResizeQueue.this.x; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(x, 0, 0).getBlock());
                        }
                    }
                    for (int i = 0; i < Cuboid3DResizeQueue.this.y; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(0, y, 0).getBlock());
                        }
                    }
                    for (int i = 0; i < Cuboid3DResizeQueue.this.z; i++) {
                        original.addAll(t);
                        for (Block block : t) {
                            queue.add(block.getLocation().add(0, 0, z).getBlock());
                        }
                    }
                }
            };
            Tasks.async(runnable);

            BukkitRunnable runnable1 = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                        if (queue.peek() == null || original.peek() == null) {
                            this.cancel();
                            editor.storeUndo(selection.toHistory());
                            return;
                        }

                        Block block = queue.poll();
                        Block or = original.poll();
                        if (!block.getBlockData().getAsString(true).equals(or.getBlockData().getAsString(true))) {
                            factory.setBlock(block.getLocation(), or.getBlockData());
                        }
                    }
                }
            };
            Tasks.repeat(runnable1, 1);
        });
        return 1;
    }
}