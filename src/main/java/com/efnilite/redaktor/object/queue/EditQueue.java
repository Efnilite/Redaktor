package com.efnilite.redaktor.object.queue;

/**
 * A class for all queues.
 *
 * @param   <T>
 *          The return type required for the {@link #build(Object)} method.
 */
public interface EditQueue<T> {

    /**
     * Builds whatever the EditQueue does.
     *
     * @param   cuboid
     *          The data it needs to build whatever the EditQueue does.
     *
     * @return  The amount of blocks that have been changed.
     */
    int build(T cuboid);

}
