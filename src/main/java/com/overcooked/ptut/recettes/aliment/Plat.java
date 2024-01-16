package com.overcooked.ptut.recettes.aliment;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant un plat, avec un nom et une liste de recettes qui le composent
 */
public class Plat extends Aliment {
    // Liste des recettes qui composent la recette en question
    List<Aliment> recettesComposees;

    public Plat() {
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
    }

    public Plat(Plat plat){
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.addAll(plat.getRecettesComposees());
    }

    public Plat(Aliment aliment1, Aliment aliment2) {
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.add(new Aliment(aliment1));
        this.recettesComposees.add(new Aliment(aliment2));
    }

    public Plat(Aliment aliment){
        super();
        this.nom = "Plat";
        description = "Plat";
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.add(new Aliment(aliment));
    }

    public boolean equals(Plat plat) {
        if (this == plat) return true;
        if (plat == null || getClass() != plat.getClass()) return false;
        List<Aliment> currAliments = new ArrayList<>(recettesComposees);
        for (Aliment aliment : plat.recettesComposees) {
            int currIndex = currAliments.indexOf(aliment);
            if (currIndex != -1) {
                currAliments.remove(currIndex);
            } else {
                return false;
            }
        }
        return currAliments.isEmpty();
    }

    public boolean ajouterAliment(Aliment aliment) {
        return recettesComposees.add(aliment);
    }

    public List<Aliment> getRecettesComposees() {
        List<Aliment> recettesComposees = new ArrayList<>();
        for (Aliment aliment : this.recettesComposees) {
            recettesComposees.add(new Aliment(aliment));
        }
        return recettesComposees;
    }

    public boolean retirerAliment(Aliment aliment) {
        return recettesComposees.remove(aliment);
    }

    public boolean viderAliments(){
        recettesComposees.clear();
        return true;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "recettesComposees=" + recettesComposees +
                '}';
    }
}
