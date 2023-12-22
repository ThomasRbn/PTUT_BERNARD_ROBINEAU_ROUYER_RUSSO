package com.overcooked.ptut.Joueurs;

import com.overcooked.ptut.Joueurs.IA.framework.common.ArgParse;
import com.overcooked.ptut.Joueurs.IA.framework.common.State;
import com.overcooked.ptut.Joueurs.IA.framework.recherche.SearchProblem;
import com.overcooked.ptut.Joueurs.IA.framework.recherche.TreeSearch;

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
        TreeSearch algo = ArgParse.makeAlgo(algo_name, p, s);
        
        // résoudre
        if( algo.solve() )
            algo.printSolution();
    }
}
