package com.overcooked.ptut.recettes.aliment;

import java.util.List;

public abstract class Recette extends RecettePrimaire {
    // Liste des recettes qui composent la recette en question
    List<Aliment> recettesComposees;

    public Recette(List<Aliment> recettesComposees) {
        nom = "Recette";
        description = "Recette";
        this.recettesComposees = recettesComposees;
    }

    public boolean ajouterAliment(Aliment aliment) {
        return recettesComposees.add(aliment);
    }

    public boolean retirerAliment(Aliment aliment) {
        return recettesComposees.remove(aliment);
    }
}
