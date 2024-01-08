package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.recettes.etat.Coupe;

public class Planche extends Transformateur{
    public Planche(int x, int y) {
        super(x, y);
        etat = new Coupe(elemPose);
    }
}
