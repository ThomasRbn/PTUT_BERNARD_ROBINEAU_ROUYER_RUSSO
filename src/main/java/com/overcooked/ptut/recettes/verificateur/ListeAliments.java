package com.overcooked.ptut.recettes.verificateur;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Burger;
import com.overcooked.ptut.recettes.aliment.Recette;

import java.util.ArrayList;
import java.util.List;

public class ListeAliments {
    List<Recette> recettesPossibles;

    public ListeAliments() {
        recettesPossibles = new ArrayList<>();
        recettesPossibles.add(new Burger());
    }

    public Aliment verifiercompatibilite(List<Aliment> alimentsATraiter) {
        for (Recette recette : recettesPossibles) {
            boolean estPresent = true;
            for (Aliment alimentRecette : recette.getRecettesComposees()) {
                System.out.println(alimentRecette);
                System.out.println(alimentsATraiter);
                estPresent = estPresent && alimentsATraiter.contains(alimentRecette);
            }
            if (estPresent) {
                System.out.println("La recette " + recette.getNom() + " est possible");
                return recette;
            }
        }
        System.out.println("Aucune recette n'est possible");
        return null;
    }
}
