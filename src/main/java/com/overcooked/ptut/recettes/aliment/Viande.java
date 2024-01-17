package com.overcooked.ptut.recettes.aliment;

/**
 * Classe correspondant a une viande
 */
public class Viande extends Aliment {
    public Viande() {
        nom = "Viande";
    }

    public Viande(int etat) {
        super(etat);
        nom = "Viande";
    }

    @Override
    public Aliment cloneAlim() {
        return new Viande(etat);
    }
}
