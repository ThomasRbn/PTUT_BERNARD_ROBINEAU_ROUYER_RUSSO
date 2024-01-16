package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

public abstract class Transformateur extends Bloc {

    /**
     * Etat de l'aliment (en fonction du transformateur, cela peut être une cuisson, une coupe, etc.)
     *
     */
    protected int etat;

    /**
     * Element posé sur le transformateur (Plat)
     */
    protected Plat inventaire;

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
        this.inventaire = t.inventaire;
        this.tempsRestant = t.tempsRestant;
    }

    /**
     * Méthode permettant de transformer un aliment avec l'état auquel il doit être transformé
     * @return Plat transformé
     */
    public Plat transform(){
        //Si aucun aliment n'est posé sur le transformateur, on ne fait rien
        if(inventaire == null
                || inventaire.getRecettesComposees().isEmpty())
            return null;

        //Si l'aliment est déjà dans l'état voulu, on ne fait rien
        if (inventaire.getRecettesComposees().getFirst().equals(etat)) return inventaire;

        //Si l'aliment n'est pas dans l'état voulu, on le transforme
        Aliment alim = inventaire.getRecettesComposees().getFirst();
        System.out.println(alim);
        alim.setEtat(etat);
        inventaire.viderAliments();
        inventaire.ajouterAliment(alim);
        return inventaire;
    }

    /**
     * Méthode permettant d'ajouter un Plat sur le transformateur. Le plat doit avoir une seule recette composée.
     * @param elem Plat à ajouter
     * @return true si le plat a été ajouté, false sinon
     */
    public boolean ajouterElem(Plat elem){
        if (inventaire != null) return false;
        if (elem.getRecettesComposees().size() != 1) return false;
        inventaire = elem;
        return true;
    }

    /**
     * Méthode permettant de retirer un Plat du transformateur
     */
    public Plat retirerElem(){
        Plat elem = inventaire;
        inventaire = null;
        return elem;
    }

    public Plat getInventaire() {
        return inventaire;
    }
}
