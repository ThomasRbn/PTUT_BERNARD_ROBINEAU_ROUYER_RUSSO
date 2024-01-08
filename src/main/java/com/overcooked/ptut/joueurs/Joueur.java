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

    public Joueur(int x, int y) {
        position = new int[]{x, y};
        direction = Action.HAUT;
    }

    protected void prendre(Mouvable objet) {
        inventaire = objet;
    }

    public Mouvable poser() {
        Mouvable objet = inventaire;
        inventaire = null;
        return objet;
    }

    public void deplacer(Action action){
        direction = action;
        switch (action){
            case HAUT:
                position[1]--;
                break;
            case BAS:
                position[1]++;
                break;
            case GAUCHE:
                position[0]--;
                break;
            case DROITE:
                position[0]++;
                break;
        }
    }

    public int[] getTargetPosition(){
        int[] targetPosition = new int[2];
        targetPosition[0] = position[0];
        targetPosition[1] = position[1];
        switch (direction){
            case HAUT:
                targetPosition[1]--;
                break;
            case BAS:
                targetPosition[1]++;
                break;
            case GAUCHE:
                targetPosition[0]--;
                break;
            case DROITE:
                targetPosition[0]++;
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
}
