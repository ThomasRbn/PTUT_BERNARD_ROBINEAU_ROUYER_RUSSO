package com.overcooked.ptut.recettes.aliment;

import com.overcooked.ptut.recettes.etat.Coupe;
import com.overcooked.ptut.recettes.etat.Cuisson;

import java.util.List;

public class Burger extends Recette {
    public Burger() {
        super();
        setNom("Burger");
        setDescription("Burger");
        recettesComposees.add(new Pain());
        recettesComposees.add(new Cuisson(new Viande()));
    }
}
