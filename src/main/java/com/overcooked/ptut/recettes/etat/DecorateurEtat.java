package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

public abstract class DecorateurEtat extends Aliment {
    protected Aliment composant;

    public DecorateurEtat(String nom, String d, Aliment c) {
        this.nom = nom;
        this.description = d;
        this.composant = c;
    }

    public String toString() {
        return composant + " -- " + getDescription();
    }
}
