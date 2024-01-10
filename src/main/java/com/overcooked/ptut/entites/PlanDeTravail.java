package com.overcooked.ptut.entites;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Plat;

public class PlanDeTravail extends Bloc {

    Plat inventaire;

    public PlanDeTravail(int x, int y) {
        super(x, y);
    }

    public void poserDessus(Plat plat) {
        inventaire = plat;
    }

    public Plat prendre() {
        Plat plat = inventaire;
        inventaire = null;
        return plat;
    }

    public Plat getInventaire() {
        return inventaire;
    }
}
