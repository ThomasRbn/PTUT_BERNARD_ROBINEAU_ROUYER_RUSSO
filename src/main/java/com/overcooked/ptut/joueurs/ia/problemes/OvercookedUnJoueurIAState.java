package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.utilitaire.Action;

public class OvercookedUnJoueurIAState extends State implements HasHeuristic {

    //Données
    private DonneesJeu donnees;

    private int numJoueur;

    // Constructeur par copie
    public OvercookedUnJoueurIAState(DonneesJeu donneesJeu, int numJoueur) {
        this.numJoueur = numJoueur;
        //TODO: constructeur par copie
//        this.donnees = new DonneesJeu(donneesJeu);
    }

    public DonneesJeu getDonnees() {
        return donnees;
    }

    @Override
    protected State cloneState() {
        return new OvercookedUnJoueurIAState(donnees, numJoueur);
    }

    @Override
    protected boolean equalsState(State o) {
        // TODO: redéfinir methode equals dans donnes jeu
        return donnees.equals(((OvercookedUnJoueurIAState) o).getDonnees());
    }

    @Override
    protected int hashState() {
        return 0;
    }

    @Override
    public double getHeuristic() {
        return 0;
    }

    public String toString() {
        return donnees.toString();
    }

    public boolean isLegal(Action a) {
        switch (a){
            case HAUT -> {
            }
            case GAUCHE -> {
            }
            case BAS -> {
            }
            case DROITE -> {
            }
            case PRENDRE -> {
            }
            case POSER -> {
            }
            default -> throw new IllegalArgumentException("OvercookedBasuqyeState.isLegal, action invalide" + a);
        }
return false;
    }

    // Méthodes effectuant les actions

    public void deplacementHaut() {

    }

    public void deplacementBas() {

    }

    public void deplacementGauche() {

    }

    public void deplacementDroite() {

    }

    public void prendre() {

    }

    public void poser() {

    }
}
