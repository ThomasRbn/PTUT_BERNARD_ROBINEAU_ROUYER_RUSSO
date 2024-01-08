package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

import java.util.Objects;

public abstract class Etat extends Aliment {
    protected Aliment composant;

    public Etat(String nom, String d, Aliment c) {
        this.nom = nom;
        this.description = d;
        this.composant = c;
    }

    public String toString() {
        return composant + " -- " + getDescription();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Etat that = (Etat) o;
        return Objects.equals(composant, that.composant);
    }

    public void setComposant(Aliment composant) {
        this.composant = composant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), composant);
    }
}
