package com.overcooked.ptut.entites;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class Depot extends Bloc {

    private List<Plat> platsDeposes;

    public Depot(int x, int y) {
        super(x, y);
        platsDeposes = new ArrayList<>();
    }

    public Depot(Depot depot) {
        super(depot);
        this.platsDeposes = new ArrayList<>(depot.platsDeposes);
    }

    public void deposerPlat(Plat plat) {
        platsDeposes.add(plat);
    }

    public List<Plat> getPlatsDeposes() {
        return platsDeposes;
    }
}
