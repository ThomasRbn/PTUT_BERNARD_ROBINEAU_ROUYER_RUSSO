package com.overcooked.ptut.joueurs.autonome;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.JoueurIA;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class JoueurAutoN4 extends JoueurIA {

    private int iteration = 0;

    public JoueurAutoN4(int y, int x) {
        super(y, x);
    }

    public JoueurAutoN4(int y, int x, Plat inventaire, Action direction, int numJoueur) {
        super(y, x, inventaire, direction, numJoueur);
    }

    private List<Action> actionsJ0 = List.of(Action.HAUT, Action.HAUT, Action.GAUCHE, Action.GAUCHE, Action.GAUCHE, Action.HAUT, Action.PRENDRE, Action.BAS, Action.BAS, Action.DROITE,
    Action.DROITE, Action.DROITE, Action.BAS, Action.POSER, Action.HAUT, Action.HAUT, Action.PRENDRE, Action.GAUCHE, Action.BAS, Action.POSER, Action.UTILISER, Action.PRENDRE, Action.PRENDRE,
    Action.DROITE, Action.BAS, Action.BAS, Action.POSER, Action.HAUT, Action.HAUT, Action.PRENDRE, Action.GAUCHE, Action.BAS, Action.POSER, Action.UTILISER, Action.PRENDRE, Action.DROITE,
    Action.BAS, Action.BAS, Action.DROITE, Action.POSER, Action.UTILISER, Action.HAUT, Action.HAUT, Action.PRENDRE, Action.GAUCHE, Action.BAS, Action.POSER, Action.UTILISER, Action.PRENDRE,
    Action.HAUT, Action.POSER, Action.GAUCHE, Action.GAUCHE);

    private List<Action> actionsJ1  = List.of(Action.HAUT, Action.HAUT, Action.PRENDRE, Action.GAUCHE, Action.GAUCHE, Action.GAUCHE, Action.BAS, Action.DROITE, Action.POSER, Action.UTILISER,
            Action.PRENDRE, Action.GAUCHE, Action.POSER, Action.BAS, Action.DROITE, Action.DROITE, Action.DROITE, Action.DROITE);

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

