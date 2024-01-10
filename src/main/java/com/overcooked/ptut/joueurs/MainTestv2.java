package com.overcooked.ptut.joueurs;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.ArgParse;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.TreeSearch;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Lance un algorithme de recherche  
 * sur un problème donné et affiche le résultat
 */
public class MainTestv2 {

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

        DonneesJeu donneesJeu = new DonneesJeu("niveaux/niveau0.txt");
        while (donneesJeu.getPlatDepose().isEmpty()) {
            Joueur j = donneesJeu.getJoueur(0);
            Action action = j.demanderAction(donneesJeu);
            System.out.println(action);
            donneesJeu.faireAction(action, 0);
            System.out.println(donneesJeu);
        }


    }
}
