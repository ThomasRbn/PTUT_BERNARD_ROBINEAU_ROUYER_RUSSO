package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.recettes.etat.Cuisson;

public class Poele extends Transformateur {

    /**
     * Constructeur de la classe Poele
     * @param x
     * @param y
     */
    public Poele(int x, int y) {
        super(x, y);
        etat = new Cuisson(elemPose);
    }

    /**
     * Constructeur par copie de la classe Poele
     * @param poele
     */
    public Poele(Poele poele) {
        super(poele);
    }
}
