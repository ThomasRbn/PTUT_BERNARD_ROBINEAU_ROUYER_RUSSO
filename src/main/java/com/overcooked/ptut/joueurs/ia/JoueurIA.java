package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.algo.UCS;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchNodeAC;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.ia.problemes.calculChemin.AlgoCalculChemin;
import com.overcooked.ptut.joueurs.ia.problemes.calculChemin.AlgoCalculCheminState;
import com.overcooked.ptut.joueurs.ia.problemes.planificateurSimple.AlgoPlanificationPrimaire;
import com.overcooked.ptut.joueurs.ia.problemes.planificateurSimple.AlgoPlanificationPrimaireEtat;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class JoueurIA extends Joueur {

    public JoueurIA(int x, int y) {
        super(x, y);
    }

    public JoueurIA(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {

        // VERSION 1

//        // créer un problème, un état initial et un algo
//        SearchProblem p = new OvercookedUnJoueurIA();
//        State s = new OvercookedUnJoueurIAState(donneesJeu, numJoueur);
//        AStar algo = new AStar(p, s);;
//
//        // résoudre
//        ArrayList<Action> solution = algo.solve();
//        return solution != null ? solution.getFirst() : Action.RIEN;

        // créer un problème, un état initial et un algo


        // VERSION 2


        SearchProblemAC p = new AlgoPlanificationPrimaire(donneesJeu.getPlatsBut().getFirst(), numJoueur, donneesJeu);
        State s = new AlgoPlanificationPrimaireEtat(donneesJeu, numJoueur);
        UCS algo = new UCS(p, s);

        // résoudre
//        ArrayList<Action> solution = algo.solve();


        SearchNodeAC solution = algo.solve();
        SearchNodeAC derniereSolution = solution;
        List<AlimentCoordonnees> listeActions = new ArrayList<>();
        //Boucle pour récupéré le dernier Aliment coordonnee du resultat
        try {
            while (solution.getAlimentCoordonnees() != null) {
                listeActions.add(solution.getAlimentCoordonnees());
                derniereSolution = solution;
                solution = solution.getParent();
            }
        } catch (NullPointerException e) {
        }


        // Affichage listeActions
//        for(AlimentCoordonnees action : listeActions){
//            System.out.println(action.getAliment().getEtatNom() + " " + action.getCoordonnees()[0] + " " + action.getCoordonnees()[1]);
//        }

        try {
            AlimentCoordonnees alimentCoordonnees = derniereSolution.getAlimentCoordonnees();
            SearchProblem p2 = new AlgoCalculChemin();
            State s2 = new AlgoCalculCheminState(donneesJeu, numJoueur, alimentCoordonnees.getAliment(), alimentCoordonnees.getCoordonnees());
            AStar algoAstar = new AStar(p2, s2);
            List<Action> listeAction = algoAstar.solve();
            return listeAction == null ? Action.RIEN : listeAction.getFirst();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
        return Action.RIEN;
    }
}
