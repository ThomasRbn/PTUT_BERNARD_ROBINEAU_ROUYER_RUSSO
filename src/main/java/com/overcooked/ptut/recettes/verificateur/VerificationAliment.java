package com.overcooked.ptut.recettes.verificateur;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe annexe permettant la vérification de la compatibilité des aliments pour créer un plat donné.
 * Elle n'est pas utile désormais, mais peut être réutilisée pour une amélioration future.
 */
public class VerificationAliment {
    List<Plat> listeAliments;

    public VerificationAliment(List<Plat> listeAliments) {
        this.listeAliments = listeAliments;
    }

    public Aliment verifiercompatibilite(List<Aliment> alimentsATraiter) {
        for (Plat recette : listeAliments) {
            boolean estPresent = true;
            List<Aliment> currAliments = new ArrayList<>(alimentsATraiter);
            for (Aliment alimentRecette : recette.getRecettesComposees()) {
                int currIndex = currAliments.indexOf(alimentRecette);
                estPresent = estPresent && (currIndex != -1);
                if (currIndex != -1) {
                    currAliments.remove(currIndex);
                }
            }
            if (estPresent && currAliments.isEmpty()) {
                System.out.println("La recette " + recette.getNom() + " est possible");
                return recette;
            }
        }
        System.out.println("Aucune recette n'est possible");
        return null;
    }
}
