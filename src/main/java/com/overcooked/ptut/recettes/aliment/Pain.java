package com.overcooked.ptut.recettes.aliment;

/**
 * Classe correspondant a un pain
 */
public class Pain extends Aliment {

    public Pain() {
        nom = "Pain";
    }

    public Pain(int etat) {
        super(etat);
        nom = "Pain";
    }

    @Override
    public Aliment cloneAlim() {
        return new Pain(etat);
    }

}
