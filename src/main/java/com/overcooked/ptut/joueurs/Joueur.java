package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.Arrays;
import java.util.Objects;

public abstract class Joueur {

    /**
     * Position du joueur sur la carte
     */
    protected int[] position;

    protected Plat inventaire;

    protected Action direction;
    protected int numJoueur;

    public Joueur(int y, int x) {
        position = new int[]{y, x};
        direction = Action.HAUT;
    }

    public Joueur(int y, int x, Plat inventaire, Action direction, int numJoueur) {
        this.position = new int[]{y, x};
        this.inventaire = inventaire;
        this.direction = direction;
        this.numJoueur = numJoueur;
    }

    public void prendre(Plat objet) {
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

    public int getNumJoueur() {
        return numJoueur;
    }

    public int[] getPosition() {
        return position;
    }

    public Action getDirection() {
        return direction;
    }

    public Plat getInventaire() {
        return inventaire;
    }

    public Plat getCloneInventaire() {
        return new Plat(inventaire);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Joueur joueur = (Joueur) o;
        return numJoueur == joueur.numJoueur && Arrays.equals(position, joueur.position) && Objects.equals(inventaire, joueur.inventaire) && direction == joueur.direction;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(inventaire, direction, numJoueur);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }
}
