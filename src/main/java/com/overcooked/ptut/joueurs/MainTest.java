package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.framework.common.ArgParse;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;

/**
 * Lance un algorithme de recherche  
 * sur un problème donné et affiche le résultat
 */
public class MainTest {

    public static void main(String[] args){

        // fixer le message d'aide
        ArgParse.setUsage
            ("Utilisation :\n\n"
             + "java LancerRecherche [-prob problem] [-algo algoname]"
             + "[-v] [-h]\n"
             + "-prob : Le nom du problem {dum, map, vac, puz, rush}. Par défaut vac\n"
             + "-algo : L'algorithme {rnd, bfs, dfs, ucs, gfs, astar}. Par défault rnd\n"
             + "-v    : Rendre bavard (mettre à la fin)\n"
             + "-h    : afficher ceci (mettre à la fin)"
             );

        
        // récupérer les options de la ligne de commande
        String prob_name = ArgParse.getProblemFromCmd(args);
        String algo_name = ArgParse.getAlgoFromCmd(args);

        // créer un problème, un état initial et un algo
        SearchProblem p = ArgParse.makeProblem(prob_name);
        State s = ArgParse.makeInitialState(prob_name);
        AStar algo = (AStar) ArgParse.makeAlgo(algo_name, p, s);
        
        // résoudre
        ArrayList<Action> solution =  algo.solve();
        if( solution != null ) {
            System.out.println(solution);
            algo.printSolution();
        }
    }
}
