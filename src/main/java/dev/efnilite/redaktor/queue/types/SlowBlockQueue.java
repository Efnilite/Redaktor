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
 * A slower queue for dramatic effect.
 *
 * @see BlockQueue
 */
public class SlowBlockQueue implements EditQueue<Selection> {

    /**
     * The blocks/tick amount
     */
    private int tick;

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
     *          The pattern
     *
     * @param   tick
     *          The blocks/tick amount
     *
     * @param   editor
     *          The editor
     */
    public SlowBlockQueue(Pattern pattern, Editor<?> editor, int tick) {
        this.tick = tick;
        this.editor = editor;
        this.pattern = pattern;
    }

    @Override
    public int build(Selection selection) {
        BlockFactory factory = Redaktor.getBlockFactory();
        new AsyncBlockGetter(selection, t -> {
            Queue<Block> queue = new LinkedList<>(t);

            BukkitRunnable runnable = new BukkitRunnable() {
                @Override
                public void run() {
                    for (int i = 0; i < tick; i++) {
                        if (queue.peek() == null) {
                            this.cancel();
                            editor.storeUndo(selection.toHistory());
                            return;
                        }

                        Block block = queue.poll();
                        BlockData data = pattern.apply(block);

                        if (data == null) {
                            return;
                        }

                        if (!block.getBlockData().getAsString(true).equals(data.getAsString(true))) {
                            factory.setBlock(block.getLocation(), data);
                        }
                    }
                }
            };
            Tasks.repeat(runnable, 1);
        });
        return 1;
    }
}