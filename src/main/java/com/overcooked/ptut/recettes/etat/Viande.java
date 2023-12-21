package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

public class Viande extends Aliment {
    @Override
    public double cout() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "Viande";
    }

    public String toString() {
        return getDescription() + " : " + cout() + " euros";
    }

}
