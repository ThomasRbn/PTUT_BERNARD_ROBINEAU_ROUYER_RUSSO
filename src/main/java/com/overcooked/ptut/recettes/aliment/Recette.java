package com.overcooked.ptut.recettes.aliment;

import java.util.ArrayList;
import java.util.List;

public abstract class Recette extends Aliment {
    // Liste des recettes qui composent la recette en question
    List<Aliment> recettesComposees;

    public Recette() {
        super();
        this.recettesComposees = new ArrayList<>();
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
