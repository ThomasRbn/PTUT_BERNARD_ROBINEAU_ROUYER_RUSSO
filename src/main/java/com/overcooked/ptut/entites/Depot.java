package com.overcooked.ptut.entites;

import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class Depot {

    private int[] position;

    private List<Plat> platsDeposes;

    public Depot(int x, int y) {
        this.position = new int[]{x, y};
        platsDeposes = new ArrayList<>();
    }

    public void deposerPlat(Plat plat) {
        platsDeposes.add(plat);
    }

    public List<Plat> getPlatsDeposes() {
        return platsDeposes;
    }
}
