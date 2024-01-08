package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.recettes.etat.Cuisson;

public class Burger extends Plat {
    public Burger() {
        super();
        setNom("Burger");
        setDescription("Burger");
        recettesComposees.add(new Pain());
        recettesComposees.add(new Cuisson(new Viande()));
    }
}
