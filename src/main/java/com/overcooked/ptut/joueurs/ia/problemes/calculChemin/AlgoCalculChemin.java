package com.overcooked.ptut.joueurs.ia.problemes.calculChemin;

import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class AlgoCalculChemin extends SearchProblem {

    public AlgoCalculChemin() {
        // La liste des actions possibles
        ACTIONS = new Action[]{HAUT, GAUCHE, BAS, DROITE, PRENDRE, POSER, UTILISER};
    }

    /**
     * Retourne la liste des actions possibles dans l'état s
      */
    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<>();
        for (Action a : ACTIONS){
            if (((AlgoCalculCheminState)s).isLegal(a)){
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
        AlgoCalculCheminState o = (AlgoCalculCheminState) s.clone();
        o.faireAction(a);
        return o;
    }

    /**
     * Retourne vrai si l'état s'est un état but
     */
    @Override
    public boolean isGoalState(State s) {
        AlgoCalculCheminState o = (AlgoCalculCheminState) s;
        return o.isGoalState();
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1;
    }
}
