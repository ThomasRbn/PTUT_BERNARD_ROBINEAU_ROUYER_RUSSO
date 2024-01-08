package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

/**
 * Classe permettant l'ajout de chantilly à une boisson
 */
public class Coupe extends Etat {
    public Coupe(Aliment aliment) {
        super("Coupe", "Coupé", aliment);
    }
}
