package com.efnilite.redaktor.queue;

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
     */
    void build(T cuboid);

}
