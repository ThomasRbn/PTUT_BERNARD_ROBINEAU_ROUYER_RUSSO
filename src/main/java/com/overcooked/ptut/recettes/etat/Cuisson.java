package com.overcooked.ptut.recettes.etat;

import com.overcooked.ptut.recettes.aliment.Aliment;

/**
 * Classe permettant l'ajout de chantilly à une boisson
 */
public class Cuisson extends DecorateurEtat {
    public Cuisson(Aliment aliment) {
        super("Cuisson", "Cuit", aliment);
    }
}
