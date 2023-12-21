package com.overcooked.ptut.recettes.verificateur;

import com.overcooked.ptut.recettes.ListeAliments;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Recette;

import java.util.List;

public class VerificationAliment {
    ListeAliments listeAliments;

    public VerificationAliment() {
        this.listeAliments = new ListeAliments();
    }

    public Aliment verifiercompatibilite(List<Aliment> alimentsATraiter) {
        for (Recette recette : listeAliments.getRecettesPossibles()) {
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
