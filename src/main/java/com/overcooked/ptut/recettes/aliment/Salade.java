package com.overcooked.ptut.recettes.aliment;

/**
 * Classe correspondant a une salade
 */
public class Salade extends Aliment {

    public Salade() {
        nom = "Salade";
    }

    public Salade(int etat) {
        super(etat);
        nom = "Salade";
    }

    @Override
    public Aliment cloneAlim() {
        return new Salade(etat);
    }
}
