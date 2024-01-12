package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import com.overcooked.ptut.recettes.etat.Cuisson;
import com.overcooked.ptut.recettes.etat.Etat;

public abstract class Transformateur extends Bloc {

    /**
     * Etat de l'aliment (en fonction du transformateur, cela peut être une cuisson, une coupe, etc.)
     *
     */
    protected Etat etat;

    /**
     * Element posé sur le transformateur (Plat)
     */
    protected Plat elemPose;

    /**
     * Temps de transformation de l'aliment
     */
    protected final int tempsTransformation = 3;

    /**
     * Temps restant avant la fin de la transformation
     */
    protected int tempsRestant;

    /**
     * Constructeur d'un transformateur
     * @param x
     * @param y
     */
    public Transformateur(int x, int y) {
        super(x, y);
        tempsRestant = tempsTransformation;
    }

    /**
     * Constructeur par copie d'un transformateur
     * @param t
     */
    public Transformateur(Transformateur t) {
        super(t);
        this.etat = t.etat;
        this.elemPose = t.elemPose;
        this.tempsRestant = t.tempsRestant;
    }

    /**
     * Méthode permettant de transformer un aliment avec l'état auquel il doit être transformé
     * @return Plat transformé
     */
    public Plat transform(){
        //Si aucun aliment n'est posé sur le transformateur, on ne fait rien
        if(elemPose == null
                || elemPose.getRecettesComposees().isEmpty())
            return null;

        //Si l'aliment est déjà dans l'état voulu, on ne fait rien
        if (elemPose.getRecettesComposees().getFirst().equals(etat)) return elemPose;

        //Si l'aliment n'est pas dans l'état voulu, on le transforme
        Aliment alim = elemPose.getRecettesComposees().getFirst();
        etat.setComposant(alim);
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
