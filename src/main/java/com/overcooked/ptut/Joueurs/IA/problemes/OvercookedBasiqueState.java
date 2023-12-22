package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.Arrays;

import static com.overcooked.ptut.joueurs.ia.problemes.OvercookedBasique.*;

public class OvercookedBasiqueState extends State implements HasHeuristic {

    //Données
    private int[][] grille;
    private int[] positionJoueur;
    private boolean portePlat;
    private int[] positionPlat;
    private int[] positionSortie;

    //Constructeur vide
    public OvercookedBasiqueState() {
        this.grille = new int[10][10];
        this.positionJoueur = new int[2];
        this.positionPlat = new int[2];
        this.positionSortie = new int[2];
        // initialisation de la grille
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                grille[i][j] = 0;
            }
        }
        // initialisation de la position du joueur
        positionJoueur[0] = 0;
        positionJoueur[1] = 0;
        // initialisation de la position du plat
        positionPlat[0] = 4;
        positionPlat[1] = 4;
        // initialisation de la position de la sortie
        positionSortie[0] = 7;
        positionSortie[1] = 8;
        portePlat = false;
    }

    // Constructeur par copie
    public OvercookedBasiqueState(int[][] grille, int[] positionJoueur, int[] positionPlat, int[] positionSortie, boolean portePlat) {
        this.grille = new int[grille.length][grille[0].length];
        this.positionJoueur = new int[2];
        this.positionPlat = new int[2];
        this.positionSortie = new int[2];
        // initialisation de la grille
        for (int i = 0; i < grille.length; i++) {
            this.grille[i] = grille[i].clone();
        }
        // initialisation de la position du joueur
        this.positionJoueur = positionJoueur.clone();
        // initialisation de la position du plat
        this.positionPlat = positionPlat.clone();
        // initialisation de la position de la sortie
        this.positionSortie = positionSortie.clone();
        this.portePlat = portePlat;
    }

    public int[] getPositionPlat() {
        return positionPlat;
    }

    public int[] getPositionSortie() {
        return positionSortie;
    }

    public boolean getPortePlat(){
        return portePlat;
    }

    @Override
    protected State cloneState() {
        return new OvercookedBasiqueState(grille, positionJoueur, positionPlat, positionSortie, portePlat);
    }

    @Override
    protected boolean equalsState(State o) {
        return Arrays.equals(positionJoueur, ((OvercookedBasiqueState) o).positionJoueur) &&
                Arrays.equals(positionPlat, ((OvercookedBasiqueState) o).positionPlat) &&
                portePlat == ((OvercookedBasiqueState) o).portePlat;
    }

    @Override
    protected int hashState() {
        return 0;
    }

    @Override
    public double getHeuristic() {
        return portePlat ? Math.abs(positionJoueur[0] - positionSortie[0]) + Math.abs(positionJoueur[1] - positionSortie[1]) + 1 :
                Math.abs(positionPlat[0] - positionSortie[0]) + Math.abs(positionPlat[1] - positionSortie[1]) +
                        Math.abs(positionJoueur[0] - positionPlat[0]) + Math.abs(positionJoueur[1] - positionPlat[1]) + 2;
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille.length; j++) {
                if (i == positionJoueur[0] && j == positionJoueur[1] && portePlat) {
                    s += "X ";
                    //Si coordonnées joueurs
                }else if (i == positionJoueur[0] && j == positionJoueur[1]) s += "J ";
                    //Si coordonnées Plat
                else if (i == positionPlat[0] && j == positionPlat[1]) s += "P ";
                else if (i == positionSortie[0] && j == positionSortie[1]) s += "S ";
                else s += grille[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }

    public boolean isLegal(Action a) {
        if (a == UP) {
            if (positionJoueur[0] == 0) return false;
            else if (grille[positionJoueur[0] - 1][positionJoueur[1]] == 1) return false;
            else return true;
        } else if (a == LEFT) {
            if (positionJoueur[1] == 0) return false;
            else if (grille[positionJoueur[0]][positionJoueur[1] - 1] == 1) return false;
            else return true;
        } else if (a == DOWN) {
            if (positionJoueur[0] == grille.length - 1) return false;
            else if (grille[positionJoueur[0] + 1][positionJoueur[1]] == 1) return false;
            else return true;
        } else if (a == RIGHT) {
            if (positionJoueur[1] == grille.length - 1) return false;
            else if (grille[positionJoueur[0]][positionJoueur[1] + 1] == 1) return false;
            else return true;
        } else if (a == PICK) {
            if (portePlat) return false;
            else if (positionJoueur[0] == positionPlat[0] && positionJoueur[1] == positionPlat[1]) return true;
            else return false;
        } else if (a == DROP) {
            return portePlat;
        }
        return false;
    }

    // Méthodes effectuant les actions

    public void deplacementHaut() {
        positionJoueur[0]--;
    }

    public void deplacementBas() {
        positionJoueur[0]++;
    }

    public void deplacementGauche() {
        positionJoueur[1]--;
    }

    public void deplacementDroite() {
        positionJoueur[1]++;
    }

    public void prendre() {
        portePlat = true;
    }

    public void poser() {
        portePlat = false;
        positionPlat = positionJoueur.clone();
    }
}
