package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.recettes.etat.Cuisson;

public class Poele extends Transformateur {
    public Poele(int x, int y) {
        super(x, y);
        etat = new Cuisson(elemPose);
    }
}
