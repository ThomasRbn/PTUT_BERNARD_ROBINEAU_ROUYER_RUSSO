package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.recettes.etat.Etat;

public class Planche extends Transformateur{

    /**
     * Constructeur de la classe Planche
     * @param x
     * @param y
     */
    public Planche(int x, int y) {
        super(x, y);
        etat = Etat.COUPE;
    }

    /**
     * Constructeur par copie de la classe Planche
     * @param planche
     */
    public Planche(Planche planche) {
        super(planche);
    }
}
