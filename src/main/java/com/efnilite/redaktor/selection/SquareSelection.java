package com.efnilite.redaktor.selection;

import java.util.Arrays;
import java.util.List;

/**
 * A collections of Cuboids.
 * Used for abstract shapes.
 *
 * @see CuboidSelection
 */
public class SquareSelection implements Selection {

    private List<CuboidSelection> cuboids;

    public SquareSelection(CuboidSelection... cuboids) {
        this.cuboids = Arrays.asList(cuboids);
    }

    public List<CuboidSelection> getCuboids() {
        return cuboids;
    }
}