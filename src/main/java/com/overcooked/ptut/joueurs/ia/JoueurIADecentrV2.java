package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.algo.BFS;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchNodeAC;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIAv2;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIAv2State;
import com.overcooked.ptut.joueurs.ia.problemes.decentraliseeV2.AlgoPlanification;
import com.overcooked.ptut.joueurs.ia.problemes.decentraliseeV2.AlgoPlanificationEtat;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class JoueurIADecentrV2 extends JoueurIA {

    public JoueurIADecentrV2(int x, int y) {
        super(x, y);
    }

    public JoueurIADecentrV2(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {

    // Pour plus tard: on récupère la premiere action de l'autre joueur
//        SearchProblemAC p = new CalculHeuristiquePlat(donneesJeu.getPlatsBut().getFirst(), numJoueur==0? 1 : 0, donneesJeu);
//        State s = new CalculHeuristiquePlatState(donneesJeu, numJoueur==0? 1 : 0);
//        BFS algo = new BFS(p, s);

        // résoudre
//        ArrayList<Action> solution = algo.solve();


//        SearchNodeAC solution = algo.solve();
//        SearchNodeAC derniereSolution = solution;
//        List<AlimentCoordonnees> listeActions = new ArrayList<>();
//        //Boucle pour récupéré le dernier Aliment coordonnee du resultat de l'autre joueur.
//        while (solution.getAlimentCoordonnees() != null) {
//            listeActions.add(solution.getAlimentCoordonnees());
//            derniereSolution = solution;
//            solution = solution.getParent();
//        }

        //TODO: prendre en compte le cas ou c'est un plan de travail, etc


        SearchProblemAC p = new AlgoPlanification(donneesJeu.getPlatsBut().getFirst(), numJoueur, donneesJeu);
        State s = new AlgoPlanificationEtat(donneesJeu, numJoueur);
        BFS algo = new BFS(p, s);

        SearchNodeAC solution = algo.solve();
        SearchNodeAC derniereSolution = solution;
        List<AlimentCoordonnees> listeActions = new ArrayList<>();
        //Boucle pour récupéré le dernier Aliment coordonnee du resultat
        while (solution.getAlimentCoordonnees() != null) {
            listeActions.add(solution.getAlimentCoordonnees());
            derniereSolution = solution;
            solution = solution.getParent();
        }


        // Affichage listeActions
        for(AlimentCoordonnees action : listeActions){
            System.out.println(action.getAliment().getEtatNom() + " " + action.getCoordonnees()[0] + " " + action.getCoordonnees()[1]);
        }

        AlimentCoordonnees alimentCoordonnees = derniereSolution.getAlimentCoordonnees();

        SearchProblem p2 = new OvercookedUnJoueurIAv2();
        State s2 = new OvercookedUnJoueurIAv2State(donneesJeu, numJoueur, alimentCoordonnees.getAliment(), alimentCoordonnees.getCoordonnees());
        AStar algoAstar = new AStar(p2, s2);
        List<Action> listeAction = algoAstar.solve();
        return listeAction == null || listeAction.isEmpty() ? Action.RIEN: listeAction.getFirst();
    }
}
