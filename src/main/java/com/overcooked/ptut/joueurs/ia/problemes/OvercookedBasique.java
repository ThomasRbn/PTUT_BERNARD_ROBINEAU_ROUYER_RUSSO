package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

import static com.overcooked.ptut.joueurs.utilitaire.Action.*;

public class OvercookedBasique extends SearchProblem {

    public OvercookedBasique() {
        // La liste des actions possibles
        ACTIONS = new Action[]{HAUT, GAUCHE, BAS, DROITE, PRENDRE, POSER};
    }
    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<>();
        for (Action a : ACTIONS){
            if (((OvercookedBasiqueState)s).isLegal(a)){
                actions.add(a);
            }
        }
        return actions;
    }

    @Override
    public State doAction(State s, Action a) {
        OvercookedBasiqueState o = (OvercookedBasiqueState) s.clone();
        switch (a) {
            case HAUT -> o.deplacementHaut();
            case GAUCHE -> o.deplacementGauche();
            case BAS -> o.deplacementBas();
            case DROITE -> o.deplacementDroite();
            case PRENDRE -> o.prendre();
            case POSER -> o.poser();
            default -> throw new IllegalArgumentException("Invalid" + a);
        }
        return o;
    }

    @Override
    public boolean isGoalState(State s) {
        OvercookedBasiqueState o = (OvercookedBasiqueState) s;
        return (o.getPositionSortie()[0] == o.getPositionPlat()[0] && o.getPositionSortie()[1] == o.getPositionPlat()[1]);
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1;
    }
}
