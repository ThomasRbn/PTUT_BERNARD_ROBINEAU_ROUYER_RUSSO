package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.problemes.calculCheminVersionFictive.OvercookedBasique;
import com.overcooked.ptut.joueurs.ia.problemes.calculCheminVersionFictive.OvercookedBasiqueState;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

/**
 * Lance un algorithme de recherche  
 * sur un problème donné et affiche le résultat
 */
public class MainTest {

    public static void main(String[] args){


        // créer un problème, un état initial et un algo
        SearchProblem p = new OvercookedBasique();
        State s = new OvercookedBasiqueState();
        AStar algo = new AStar(p, s);
        
        // résoudre
        ArrayList<Action> solution =  algo.solve();
        if( solution != null ) {
            System.out.println(solution);
            algo.printSolution();
        }
    }
}
