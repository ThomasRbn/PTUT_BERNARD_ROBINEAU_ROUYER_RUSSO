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

    protected Action direction;
    protected int numJoueur;

    public Joueur(int y, int x) {
        position = new int[]{y, x};
        direction = Action.HAUT;
    }

    public void prendre(Mouvable objet) {
        inventaire = objet;
    }

    public Mouvable poser() {
        Mouvable objet = inventaire;
        inventaire = null;
        return objet;
    }

    public void changeDirection(Action action) {
        if (action != Action.RIEN && action != Action.PRENDRE && action != Action.POSER)
            direction = action;
    }

    public void deplacer(Action action) {
        switch (action) {
            case HAUT:
                position[0]--;
                break;
            case BAS:
                position[0]++;
                break;
            case GAUCHE:
                position[1]--;
                break;
            case DROITE:
                position[1]++;
                break;
        }
    }

    public int[] retournePositionCible() {
        int[] targetPosition = new int[2];
        targetPosition[0] = position[0];
        targetPosition[1] = position[1];
        switch (direction) {
            case HAUT:
                targetPosition[0]--;
                break;
            case BAS:
                targetPosition[0]++;
                break;
            case GAUCHE:
                targetPosition[1]--;
                break;
            case DROITE:
                targetPosition[1]++;
                break;
        }
        return targetPosition;
    }

    public abstract Action demanderAction(DonneesJeu donneesJeu);

    public int setNumJoueur(int numJoueur) {
        return this.numJoueur = numJoueur;
    }

    public int[] getPosition() {
        return position;
    }

    public Action getDirection() {
        return direction;
    }

    public Mouvable getInventaire() {
        return inventaire;
    }
}
