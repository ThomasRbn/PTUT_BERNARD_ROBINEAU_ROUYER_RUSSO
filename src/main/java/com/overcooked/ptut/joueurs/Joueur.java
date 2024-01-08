package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Mouvable;

public abstract class Joueur {

    /**
     * Position du joueur sur la carte
     */
    protected int[] position;

    protected Mouvable inventaire;

    protected int numJoueur;

    public Joueur(int x, int y) {
        position = new int[]{x, y};
    }

    protected void prendre(Mouvable objet) {
        inventaire = objet;
    }

    protected Mouvable deposer() {
        Mouvable objet = inventaire;
        inventaire = null;
        return objet;
    }

    public abstract Action demanderAction(DonneesJeu donneesJeu);

    public int setNumJoueur(int numJoueur) {
    	return this.numJoueur = numJoueur;
    }

    public int[] getPosition() {
        return position;
    }
}
