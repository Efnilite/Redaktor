package com.efnilite.redaktor.object.selection;

import java.util.Arrays;
import java.util.List;

public class SquareSelection {

    private List<CuboidSelection> cuboids;

    public SquareSelection(CuboidSelection... cuboids) {
        this.cuboids = Arrays.asList(cuboids);
    }

    public List<CuboidSelection> getCuboids() {
        return cuboids;
    }
}