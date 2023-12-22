package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

public class OvercookedBasique extends SearchProblem {
    public static final Action UP = new Action("Up");
    public static final Action LEFT = new Action("Left");
    public static final Action DOWN = new Action("Down");
    public static final Action RIGHT = new Action("Right");
    public static final Action PICK = new Action("Pick");
    public static final Action DROP = new Action("Drop");

    public OvercookedBasique() {
        // La liste des actions possibles
        ACTIONS = new Action[]{UP, LEFT, DOWN, RIGHT, PICK, DROP};
    }
    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<Action>();
        for (Action a : ACTIONS){
            if (((OvercookedBasiqueState)s).isLegal(a)){
                actions.add(a);
            };
        }
        return actions;
    }

    @Override
    public State doAction(State s, Action a) {
        OvercookedBasiqueState o = (OvercookedBasiqueState) s.clone();
        switch (a.getName()) {
            case "Up" -> o.deplacementHaut();
            case "Left" -> o.deplacementGauche();
            case "Down" -> o.deplacementBas();
            case "Right" -> o.deplacementDroite();
            case "Pick" -> o.prendre();
            case "Drop" -> o.poser();
            default -> throw new IllegalArgumentException("Invalid" + a);
        }
        return o;
    }

    @Override
    public boolean isGoalState(State s) {
        OvercookedBasiqueState o = (OvercookedBasiqueState) s;
        return o.getPortePlat() || (o.getPositionSortie()[0] == o.getPositionPlat()[0] && o.getPositionSortie()[1] == o.getPositionPlat()[1]);
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1;
    }
}
