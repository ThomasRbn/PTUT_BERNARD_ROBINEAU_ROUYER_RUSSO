package com.overcooked.ptut.recettes.aliment;

/**
 * Classe correspondant a une tomate
 */
public class Tomate extends Aliment {

    public Tomate() {
        nom = "Tomate";
    }

    public Tomate(int etat) {
        super(etat);
        nom = "Tomate";
    }

    @Override
    public Aliment cloneAlim() {
        return new Tomate(etat);
    }
}