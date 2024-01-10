package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.objet.Mouvable;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.Scanner;

public class JoueurHumain extends Joueur {

    public JoueurHumain(int x, int y) {
        super(x, y);
    }

    public JoueurHumain(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez une action (HAUT, BAS, GAUCHE, DROITE, PRENDRE, POSER, COUPER)");
        String choix = "";

        boolean estConforme = false;
        boolean estJouable = false;

        while (!estConforme) {
            choix = sc.nextLine();

            try {
                Action.valueOf(choix);
                estConforme = true;
                estJouable = donneesJeu.isLegal(Action.valueOf(choix), this.numJoueur);
                if (!estJouable) {
                    System.out.println("Entrée invalide : " + choix + " (HAUT, BAS, GAUCHE, DROITE, PRENDRE, POSER, COUPER)");
                }
            } catch (IllegalArgumentException ignored) {
                System.out.println("Entrée invalide : " + choix + " (HAUT, BAS, GAUCHE, DROITE, PRENDRE, POSER, COUPER)");
            }
        }

        return Action.valueOf(choix);
    }
}
