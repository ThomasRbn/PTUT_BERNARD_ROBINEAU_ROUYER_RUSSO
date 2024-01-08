package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.utilitaire.Action;

public class OvercookedUnJoueurIAState extends State implements HasHeuristic {

    //Données
    private DonneesJeu donnees;

    private int numJoueur;

    /**
     * Constructeur
     * @param donneesJeu Données du jeu
     * @param numJoueur Numéro du joueur courant
     */
    public OvercookedUnJoueurIAState(DonneesJeu donneesJeu, int numJoueur) {
        this.numJoueur = numJoueur;
        //Constructeur par copie
        this.donnees = new DonneesJeu(donneesJeu);
    }

    public DonneesJeu getDonnees() {
        return donnees;
    }

    /**
     * Retourne une copie de l'état
     */
    @Override
    protected State cloneState() {
        return new OvercookedUnJoueurIAState(donnees, numJoueur);
    }

    /**
     * Retourne vrai si l'état o est égal à l'état courant
     */
    @Override
    protected boolean equalsState(State o) {
        // TODO: redéfinir methode equals dans donnees jeu
        return donnees.equals(((OvercookedUnJoueurIAState) o).getDonnees());
    }

    @Override
    protected int hashState() {
        return 0;
    }

    @Override
    public double getHeuristic() {
        return 0; // TODO: calcul de l'heuristie selon un objectif
    }

    public String toString() {
        return donnees.toString();
    }

    /**
     * Retourne vrai si l'action a est légale dans l'état courant
     */
    public boolean isLegal(Action a) {
        return donnees.isLegal(a, numJoueur);
    }

    /**
     * Applique l'action a dans l'état courant
     */
    public void faireAction(Action a) {
        donnees.faireAction(a, numJoueur);
    }
}
