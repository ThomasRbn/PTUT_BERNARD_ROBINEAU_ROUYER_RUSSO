package com.overcooked.ptut.recettes.aliment;

import java.util.ArrayList;
import java.util.List;

public class Plat extends Aliment {
    // Liste des recettes qui composent la recette en question
    List<Aliment> recettesComposees;

    public Plat() {
        super();
        this.recettesComposees = new ArrayList<>();
    }

    public Plat(Plat plat){
        super();
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.addAll(plat.recettesComposees);
    }

    public Plat(String nom, Aliment aliment1, Aliment aliment2) {
        super();
        this.nom = nom;
        this.recettesComposees = new ArrayList<>();
        this.recettesComposees.add(aliment1);
        this.recettesComposees.add(aliment2);
    }

    public boolean ajouterAliment(Aliment aliment) {
        return recettesComposees.add(aliment);
    }

    public List<Aliment> getRecettesComposees() {
        return recettesComposees;
    }

    public boolean retirerAliment(Aliment aliment) {
        return recettesComposees.remove(aliment);
    }
}
