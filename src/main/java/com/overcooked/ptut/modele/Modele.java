package com.overcooked.ptut.modele;

import com.overcooked.ptut.constructionCarte.Carte;
import com.overcooked.ptut.Joueurs.Joueur;

import java.util.ArrayList;
import java.util.List;

public class Modele {

    /**
     * Plateau de jeu <br>
     * Sa taille dépend du fichier de configuration chargé à la création
     */
    private final char[][] carte;

    /**
     * Liste des joueurs
     */
    private final List<Joueur> joueurs;

    /**
     * Constructeur initialisant le plateau de jeu à partir du fichier et la liste des joueurs
     *
     * @param chemin chemin du fichier de configuration
     */
    public Modele(String chemin) {
        this.carte = Carte.chargerCarte(chemin);
        this.joueurs = new ArrayList<>();
    }

    public static void main(String[] args) {
        Modele m = new Modele("assets/cartes/carte1.txt");
        System.out.println(m);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (char[] chars : carte) {
            for (char aChar : chars) {
                s.append(aChar);
            }
            s.append("\n");
        }
        return s.toString();
    }
}