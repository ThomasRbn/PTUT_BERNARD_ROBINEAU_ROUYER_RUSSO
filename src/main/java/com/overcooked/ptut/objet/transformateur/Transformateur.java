package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.etat.Etat;

public abstract class Transformateur extends Bloc {
    protected Etat etat;
    protected Plat elemPose;

    public Transformateur(int x, int y) {
        super(x, y);
    }

    public Transformateur(Transformateur t) {
        super(t);
        this.etat = t.etat;
        this.elemPose = t.elemPose;
    }

    public Plat transform(){
        if(elemPose == null) return null;
        if (elemPose.getRecettesComposees().getFirst().equals(etat)) return elemPose;
        etat.setComposant(elemPose);
        elemPose.viderAliments();
        elemPose.ajouterAliment(etat);
        return elemPose;
    }

    public boolean ajouterElem(Plat elem){
        if (elem.getRecettesComposees().size() != 1) return false;
        elemPose = elem;
        return true;
    }

    public void retirerElem(){
        elemPose = null;
    }
}
