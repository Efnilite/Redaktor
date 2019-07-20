package com.efnilite.redaktor.selection;

import java.util.Arrays;
import java.util.List;

/**
 * A collections of CuboidSelections.
 * Used for abstract shapes.
 *
 * @see CuboidSelection
 */
public class SquareSelection implements Selection {

    /**
     * The list of cuboids
     */
    private List<CuboidSelection> cuboids;

    /**
     * Creates a new instance
     *
     * @param   cuboids
     *          The CuboidSelections
     */
    public SquareSelection(CuboidSelection... cuboids) {
        this.cuboids = Arrays.asList(cuboids);
    }

    /**
     * Gets the CuboidSelections
     *
     * @return the CuboidSelections
     */
    public List<CuboidSelection> getCuboids() {
        return cuboids;
    }
}