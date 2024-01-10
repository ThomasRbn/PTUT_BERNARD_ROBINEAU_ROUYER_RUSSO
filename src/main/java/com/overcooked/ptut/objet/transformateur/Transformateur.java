package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.etat.Etat;

public abstract class Transformateur extends Bloc {

    /**
     * Etat de l'aliment (en fonction du transformateur, cela peut être une cuisson, une coupe, etc.)
     */
    protected Etat etat;
    /**
     * Element posé sur le transformateur (Plat)
     */
    protected Plat elemPose;

    /**
     * Constructeur d'un transformateur
     * @param x
     * @param y
     */
    public Transformateur(int x, int y) {
        super(x, y);
    }

    /**
     * Constructeur par copie d'un transformateur
     * @param t
     */
    public Transformateur(Transformateur t) {
        super(t);
        this.etat = t.etat;
        this.elemPose = t.elemPose;
    }

    /**
     * Méthode permettant de transformer un aliment avec l'état auquel il doit être transformé
     * @return Plat transformé
     */
    public Plat transform(){
        if(elemPose == null) return null;
        if (elemPose.getRecettesComposees().getFirst().equals(etat)) return elemPose;
        etat.setComposant(elemPose);
        elemPose.viderAliments();
        elemPose.ajouterAliment(etat);
        return elemPose;
    }

    /**
     * Méthode permettant d'ajouter un Plat sur le transformateur. Le plat doit avoir une seule recette composée.
     * @param elem Plat à ajouter
     * @return true si le plat a été ajouté, false sinon
     */
    public boolean ajouterElem(Plat elem){
        if (elem.getRecettesComposees().size() != 1) return false;
        elemPose = elem;
        return true;
    }

    /**
     * Méthode permettant de retirer un Plat du transformateur
     */
    public Plat retirerElem(){
        elemPose = null;
        return elemPose;
    }
}
