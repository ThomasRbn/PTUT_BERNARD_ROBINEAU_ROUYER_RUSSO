package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.etat.Etat;

public abstract class Transformateur extends Bloc {
    protected Etat etat;
    protected Aliment elemPose;

    public Transformateur(int x, int y) {
        super(x, y);
    }

    public Transformateur(Transformateur t) {
        super(t);
        this.etat = t.etat;
        this.elemPose = t.elemPose;
    }

    public Aliment transform(){
        if(elemPose == null) return null;
        if (elemPose instanceof Cuisson) return elemPose;
        etat.setComposant(elemPose);
        return etat;
    }

    public void ajouterElem(Aliment elem){
        elemPose = elem;
    }

    public void retirerElem(){
        elemPose = null;
    }
}
