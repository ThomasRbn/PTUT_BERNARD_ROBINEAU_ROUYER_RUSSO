package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class OvercookedUnJoueurIA extends SearchProblem {

    public OvercookedUnJoueurIA() {
        // La liste des actions possibles
        ACTIONS = new Action[]{HAUT, GAUCHE, BAS, DROITE, PRENDRE, POSER};
    }

    /**
     * Retourne la liste des actions possibles dans l'état s
      */
    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<>();
        for (Action a : ACTIONS){
            if (((OvercookedUnJoueurIAState)s).isLegal(a)){
                actions.add(a);
            }
        }
        return actions;
    }

    /**
     * Retourne l'état résultant de l'application de l'action a dans l'état s
     */
    @Override
    public State doAction(State s, Action a) {
        OvercookedUnJoueurIAState o = (OvercookedUnJoueurIAState) s.clone();
        o.faireAction(a);
        return o;
    }

    /**
     * Retourne vrai si l'état s'est un état but
     */
    @Override
    public boolean isGoalState(State s) {
        OvercookedUnJoueurIAState o = (OvercookedUnJoueurIAState) s;
        return o.isGoalState();
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1;
    }
}
