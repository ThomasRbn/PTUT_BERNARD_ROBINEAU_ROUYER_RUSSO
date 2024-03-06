package com.overcooked.ptut.joueurs.autonome;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.List;

public class JoueurAutoN0 extends JoueurIA {

    private int iteration = 0;

    private List<Action> actionsJ0 = List.of(Action.DROITE, Action.DROITE, Action.HAUT, Action.PRENDRE, Action.BAS,
            Action.BAS, Action.GAUCHE, Action.BAS, Action.POSER, Action.GAUCHE, Action.HAUT, Action.HAUT);

    private List<Action> actionsJ1 = List.of(Action.HAUT, Action.DROITE, Action.HAUT, Action.DROITE, Action.HAUT, Action.PRENDRE, Action.GAUCHE,
            Action.GAUCHE, Action.BAS, Action.BAS, Action.DROITE, Action.BAS, Action.POSER, Action.GAUCHE);


    public JoueurAutoN0(int y, int x) {
        super(y, x);
    }

    public JoueurAutoN0(int y, int x, Plat inventaire, Action direction, int numJoueur) {
        super(y, x, inventaire, direction, numJoueur);
    }

    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        Action action;

        if (this.getNumJoueur() == 0) {
            action = actionsJ0.get(iteration);
            iteration++;
            if (iteration == actionsJ0.size() - 1) iteration = 0;
        } else {
            action = actionsJ1.get(iteration);
            iteration++;
            if (iteration == actionsJ1.size() - 1) iteration = 0;
        }
        return action;
    }
}

