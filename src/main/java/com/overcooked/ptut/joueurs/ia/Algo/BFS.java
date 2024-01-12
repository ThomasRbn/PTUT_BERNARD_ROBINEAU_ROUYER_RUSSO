package com.overcooked.ptut.joueurs.ia.algo;



import com.overcooked.ptut.joueurs.ia.framework.common.ArgParse;
import com.overcooked.ptut.joueurs.ia.framework.recherche.*;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BFS extends TreeSearchAC {

    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public BFS(SearchProblemAC p, State s) {
        super(p, s);
        // Initialisation de la frontière avec une PriorityQueue basée sur le cout
        frontier = new PriorityQueue<>(new SearchNodeComparator());
    }

//    @Override
    public double solve() {
//        System.out.println("Algo choisi: BFS");
        SearchNodeAC node = SearchNodeAC.makeRootSearchNode(intial_state);
        State state = node.getState();

        // On commence à l'état initial
        frontier.add(node);

        // On initialise l'ensemble des nœuds déjà explorés a vide
        explored.clear();

        if (ArgParse.DEBUG)
            System.out.print("[" + state);

        while (!frontier.isEmpty()){
            // Stratégie: BFS
            node = frontier.poll();

            // Si le nœud contient un état but
            if (problem.isGoalState(node.getState())) {
                // On enregistre le nœud final
                end_node = node;
                // On retourne vrai
//                System.out.println("Cout: "+end_node.getCost());
                return end_node.getCost();
            } else {
                // On ajoute l'état du nœud dans l'ensemble des nœuds explorés
                explored.add(node.getState());

                // Les actions possibles depuis cet état
                ArrayList<AlimentCoordonnees> alimentCoordonnees = problem.getAlimentCoordonnees(node.getState());

                // Pour chaque nœud enfant
                for (AlimentCoordonnees a : alimentCoordonnees) {
                    // Nœud enfant
                    SearchNodeAC child = SearchNodeAC.makeChildSearchNode(problem, node, a);

                    // S'il n'est pas dans la frontière et si son état n'a pas été visité
                    if (!frontier.contains(child) && !explored.contains(child.getState())) {
                        // L'insérer dans la frontière avec la priorité du coût
                        frontier.add(child);
                    }else if(frontier.contains(child)){
                        // Si le nœud est déjà dans la frontière
                        // On récupère le nœud de la frontière
                        SearchNodeAC frontier_node = frontier.stream().filter(n -> n.equals(child)).findFirst().get();
                        // Si le cout du nœud enfant est inférieur au cout du nœud de la frontière
                        if(child.getCost() < frontier_node.getCost()){
                            // On le remplace
                            frontier.remove(frontier_node);
                            frontier.add(child);
                        }
                    }
                }
            }
        }

        // Pas de solutions trouvées
        throw new IllegalArgumentException("Pas de solution trouvée BFS.solve");


    }

    // Comparator par le cout
    private static class SearchNodeComparator implements Comparator<SearchNodeAC> {
        @Override
        public int compare(SearchNodeAC node1, SearchNodeAC node2) {
            return Double.compare(node1.getCost(), node2.getCost());
        }
    }
}