package com.overcooked.ptut.objet.transformateur;

import com.overcooked.ptut.objet.Bloc;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;

public abstract class Transformateur extends Bloc {

    /**
     * Temps de transformation de l'aliment
     */
    protected final int tempsTransformation = 3;
    /**
     * Etat de l'aliment (en fonction du transformateur, cela peut être une cuisson, une coupe, etc.)
     */
    protected int etat;
    /**
     * Element posé sur le transformateur (Plat)
     */
    protected Plat inventaire;
    /**
     * Temps restant avant la fin de la transformation
     */
    protected int tempsRestant;

    protected boolean isBloque;

    protected Task<Void> transformation;

    protected ProgressBar progressBar;


    /**
     * Constructeur d'un transformateur
     *
     * @param x
     * @param y
     */
    public Transformateur(int x, int y) {
        super(x, y);
        tempsRestant = tempsTransformation;
        isBloque = false;
    }

    /**
     * Constructeur par copie d'un transformateur
     *
     * @param t
     */
    public Transformateur(Transformateur t) {
        super(t);
        this.etat = t.etat;
        this.inventaire = t.inventaire;
        this.tempsRestant = t.tempsRestant;
        this.isBloque = t.isBloque;
    }

    /**
     * Méthode permettant de transformer un aliment avec l'état auquel il doit être transformé
     *
     * @return Plat transformé
     */
    public Plat transform() {
        //Si aucun aliment n'est posé sur le transformateur, on ne fait rien
        if (inventaire == null
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
     *
     * @param elem Plat à ajouter
     * @return true si le plat a été ajouté, false sinon
     */
    public boolean ajouterElem(Plat elem) {
        if (inventaire != null) return false;
        if (elem.getRecettesComposees().size() != 1) return false;
        inventaire = elem;
        return true;
    }

    /**
     * Méthode permettant de retirer un Plat du transformateur
     */
    public Plat retirerElem() {
        Plat elem = inventaire;
        inventaire = null;
        return elem;
    }

    public Plat getInventaire() {
        return inventaire;
    }

    public boolean isBloque() {
        return isBloque;
    }

    public Transformateur setBloque(boolean bloque) {
        isBloque = bloque;
        return this;
    }

    public Task<Void> getTransformation() {
        return transformation;
    }

    public Transformateur setTransformation(Task<Void> transformation) {
        this.transformation = transformation;
        return this;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public Transformateur setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
        return this;
    }
}
