package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

/**
 * Classe permettant l'ajout de l'état de cuisson
 */
public class Cuisson extends Etat {
    public Cuisson(Aliment aliment) {
        super("Cuisson", "Cuit", aliment);
    }
}
