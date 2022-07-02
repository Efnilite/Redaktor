package dev.efnilite.redaktor.queue.types;

import dev.efnilite.redaktor.Editor;
import dev.efnilite.redaktor.Redaktor;
import dev.efnilite.redaktor.block.BlockFactory;
import dev.efnilite.redaktor.pattern.Pattern;
import dev.efnilite.redaktor.queue.EditQueue;
import dev.efnilite.redaktor.selection.Selection;
import dev.efnilite.redaktor.util.getter.AsyncBlockGetter;
import dev.efnilite.redaktor.util.thread.Tasks;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue for setting a lot of blocks to the same material.
 */
public class BlockQueue implements EditQueue<Selection> {

    private int change;

    /**
     * The pattern
     */
    private Pattern pattern;

    /**
     * The editor
     */
    private Editor<?> editor;

    /**
     * Creates a new instance
     *
     * @param   pattern
     *          The pattern the blocks are going to be set to
     *
     * @param   editor
     *          The editor
     */
    public BlockQueue(Pattern pattern, Editor<?> editor) {
        this.pattern = pattern;
        this.editor = editor;
    }

    @Override
    public int build(Selection selection) {
        BlockFactory factory = Redaktor.getBlockFactory();
        new AsyncBlockGetter(selection, t -> {
            Queue<Block> queue = new LinkedList<>(t);
            Redaktor.getInstance().getLogger().info("Async get: " + t.size());

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    int change = 0;
                    for (int i = 0; i < Redaktor.getAllocator().getChanger(); i++) {
                        if (queue.peek() == null) {
                            this.cancel();
                            editor.storeUndo(selection.toHistory());
                            Redaktor.getInstance().getLogger().info("Done: " + change);
                            return;
                        }

                        Block block = queue.poll();
                        BlockData data = pattern.apply(block);

                        if (data == null) {
                            continue;
                        }

                        if (!block.getBlockData().getAsString(true).equals(data.getAsString(true))) {
                            factory.setBlock(block.getLocation(), data);
                            change++;
                        }
                    }
                    change(change);
                }
            };
            Tasks.repeat(runnable, 1);
        });
        return 1;
    }

    public void change(int change) {
        this.change += change;
    }
}