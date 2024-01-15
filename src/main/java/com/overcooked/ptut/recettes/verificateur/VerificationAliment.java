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

    public boolean verifiercompatibilite(List<Aliment> alimentsATraiter) {
        for (Plat recette : listeAliments) {
            boolean estPresent = true;
            List<Aliment> currAliments = new ArrayList<>(alimentsATraiter);
            for (Aliment alimentRecette : recette.getRecettesComposees()) {
                int currIndex = currAliments.indexOf(alimentRecette);
                estPresent = estPresent && (currIndex != -1);
                System.out.println(estPresent + " " + alimentRecette);
                if (currIndex != -1) {
                    currAliments.remove(currIndex);
                }
            }
            System.out.println("currAliments.isEmpty() = " + currAliments.isEmpty() + " estPresent = " + estPresent);
            if (estPresent && currAliments.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
