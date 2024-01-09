package com.overcooked.ptut;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.utilitaire.Action;

public class Overcooked {
    public String chemin = "niveaux/niveau0.txt";

    public static void main(String[] args) {
        Overcooked overcooked = new Overcooked();
        DonneesJeu jeu = new DonneesJeu(overcooked.chemin);

        while (true) {
            overcooked.jeu(jeu);
        }
    }

    public void jeu(DonneesJeu jeu) {
        Joueur joueur = jeu.getJoueurs().getFirst();
        Action action = joueur.demanderAction(jeu);
        jeu.faireAction(action, joueur.getNumJoueur());
        System.out.println(jeu);
    }
}
