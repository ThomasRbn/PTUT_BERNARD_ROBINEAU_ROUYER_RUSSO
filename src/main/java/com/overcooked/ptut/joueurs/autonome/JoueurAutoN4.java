package com.overcooked.ptut.joueurs.autonome;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

public class JoueurAutoN4 extends JoueurIA {

    private int iteration = 0;

    public JoueurAutoN4(int y, int x) {
        super(y, x);
    }

    public JoueurAutoN4(int y, int x, Plat inventaire, Action direction, int numJoueur) {
        super(y, x, inventaire, direction, numJoueur);
    }

    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        Action action = switch (iteration) {
            case 0, 1, 5, 14, 15, 26, 27, 40, 41, 48 -> Action.HAUT;
            case 9, 10, 11, 22, 34, 37 -> Action.DROITE;
            case 7, 8, 12, 18, 23, 24, 30, 35, 36, 44, 52, 53 -> Action.BAS;
            case 2, 3, 4, 17, 29, 43, 50, 51 -> Action.GAUCHE;
            case 6, 16, 21, 28, 33, 42, 47 -> Action.PRENDRE;
            case 13, 19, 25, 31, 38, 45, 49 -> Action.POSER;
            case 20, 32, 39, 46 -> Action.UTILISER;
            default -> throw new IllegalStateException("Unexpected value: " + iteration);
        };
        iteration++;
        if (iteration > 53) iteration = 0;
        return action;
    }
}

