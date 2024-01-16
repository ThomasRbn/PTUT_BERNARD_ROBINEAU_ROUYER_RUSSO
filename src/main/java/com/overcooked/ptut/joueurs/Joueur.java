package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.Arrays;
import java.util.Objects;

public abstract class Joueur {

    /**
     * Position du joueur sur la carte
     */
    protected int[] position;

    /**
     * Inventaire du joueur (plats, car le joueur ne peut que transporter des plats)
     */
    protected Plat inventaire;

    /**
     * Action du joueur (représenté par l'enum Direction)
     */
    protected Action direction;
    /**
     * Numéro du joueur
     */
    protected int numJoueur;

    /**
     * Constructeur de joueur
     *
     * @param y coordonnée y
     * @param x coordonnée x
     */
    public Joueur(int y, int x) {
        position = new int[]{y, x};
        direction = Action.HAUT;
    }

    /**
     * Constructeur de joueur par copie
     *
     * @param y          coordonnée y
     * @param x          coordonnée x
     * @param inventaire inventaire du joueur
     * @param direction  direction du joueur
     * @param numJoueur  numéro du joueur
     */
    public Joueur(int y, int x, Plat inventaire, Action direction, int numJoueur) {
        this.position = new int[]{y, x};
        this.inventaire = inventaire;
        this.direction = direction;
        this.numJoueur = numJoueur;
    }

    /**
     * Méthode pour prendre un Plat et le mettre dans son inventaire
     *
     * @param objet Plat à prendre
     */
    public void prendre(Plat objet) {
        inventaire = objet;
    }

    /**
     * Méthode pour poser un Plat de son inventaire. L'inventaire est alors vide et l'objet est retourné.
     *
     * @return le plat posé
     */
    public Plat poser() {
        Plat objet = inventaire;
        inventaire = null;
        return objet;
    }

    /**
     * Méthode pour changer la direction du joueur
     *
     * @param action
     */
    public void changeDirection(Action action) {
        if (action == Action.HAUT || action == Action.BAS || action == Action.GAUCHE || action == Action.DROITE)
            direction = action;
    }

    /**
     * Méthode pour déplacer le joueur
     * @param action
     */
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

    /**
     * Méthode pour retourner la position de la case cible en fonction de la direction du joueur
     * @return la position de la case cible
     */
    public int[] getPositionCible() {
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

    /**
     * Méthode abstraite pour demander une action à un joueur (humain ou IA)
     * @param donneesJeu données du jeu
     * @return l'action demandée
     */
    public abstract Action demanderAction(DonneesJeu donneesJeu);

    /**
     * Méthode pour changer le numéro du joueur
     * @param numJoueur numéro du joueur
     */
    public void setNumJoueur(int numJoueur) {
        this.numJoueur = numJoueur;
    }

    /**
     * Méthode pour avoir le numéro du joueur
     */
    public int getNumJoueur() {
        return numJoueur;
    }

    /**
     * Méthode pour avoir la position du joueur
     * @return la position du joueur
     */
    public int[] getPosition() {
        return position;
    }

    /**
     * Méthode pour avoir la direction du joueur
     * @return la direction du joueur
     */
    public Action getDirection() {
        return direction;
    }

    /**
     * Méthode pour avoir l'inventaire du joueur
     * @return l'inventaire du joueur
     */
    public Plat getInventaire() {
        return inventaire;
    }

    /**
     * Méthode pour avoir le clone de l'inventaire du joueur
     * @return le clone de l'inventaire du joueur
     */
    public Plat getCloneInventaire() {
        return new Plat(inventaire);
    }

    /**
     * Override de la méthode equals pour comparer deux joueurs en fonction de leurs attributs clés
     * @return true si les deux joueurs sont égaux, false sinon
     */
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
