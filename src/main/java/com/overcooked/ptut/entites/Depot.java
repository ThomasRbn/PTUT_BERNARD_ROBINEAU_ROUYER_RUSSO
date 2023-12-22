package com.overcooked.ptut.entites;

import com.overcooked.ptut.recettes.aliment.Recette;

import java.util.ArrayList;
import java.util.List;

public class Depot {

    private int[] position;

    private List<Recette> platsDeposes;

    public Depot(int x, int y) {
        this.position = new int[]{x, y};
        platsDeposes = new ArrayList<>();
    }

    public void deposerPlat(Recette plat) {
        platsDeposes.add(plat);
    }

    public List<Recette> getPlatsDeposes() {
        return platsDeposes;
    }
}
