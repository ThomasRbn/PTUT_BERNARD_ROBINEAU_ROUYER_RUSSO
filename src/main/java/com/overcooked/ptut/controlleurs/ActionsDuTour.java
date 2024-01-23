package com.overcooked.ptut.controlleurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.Map;

import static com.overcooked.ptut.constructionCarte.GestionActions.faireAction;

public class ActionsDuTour {

    private DonneesJeu jeu;
    private boolean isTourTermine;
    private Map<Joueur, Action> actions;

    public ActionsDuTour(DonneesJeu jeu) {
        this.actions = new java.util.HashMap<>();
        this.jeu = jeu;
        this.isTourTermine = false;
    }

    public Map<Joueur, Action> getActions() {
        return actions;
    }

    public void ajouterAction(Joueur joueur, Action action) {
        actions.putIfAbsent(joueur, action);
        if (jeu.getJoueurs().size() == actions.size()) {
            resoudreActions();
        } else {
            isTourTermine = false;
        }
    }

    public void resoudreActions() {
        for (Joueur joueur : actions.keySet()) {
            faireAction(actions.get(joueur), joueur.getNumJoueur(), jeu);
        }
        isTourTermine = true;
        actions.clear();
    }

    public boolean isTourTermine() {
        return isTourTermine;
    }
}
