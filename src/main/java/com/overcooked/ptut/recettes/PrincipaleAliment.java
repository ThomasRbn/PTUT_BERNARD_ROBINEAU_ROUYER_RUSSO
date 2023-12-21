package com.overcooked.ptut.recettes;

import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.etat.Coupe;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.etat.Viande;

public class PrincipaleAliment {
    public static void main(String[] args) {
        Aliment viande = new Viande();
        System.out.println(viande);
        Aliment viandeCuite = new Cuisson(viande);
        System.out.println(viandeCuite);
    }
}