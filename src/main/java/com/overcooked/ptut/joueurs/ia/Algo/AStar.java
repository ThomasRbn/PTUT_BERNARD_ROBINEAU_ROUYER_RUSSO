package com.overcooked.ptut.joueurs.ia.algo;


import com.overcooked.ptut.joueurs.ia.framework.common.ArgParse;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchNode;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.TreeSearch;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStar extends TreeSearch {
    /**
     * Crée un algorithme de recherche
     *
     * @param p Le problème à résoudre
     * @param s L'état initial
     */
    public AStar(SearchProblem p, State s) {
        super(p, s);
        // Initialisation de la frontière avec une PriorityQueue basée sur le coût et l'heuristique
        frontier = new PriorityQueue<>(new SearchNodeComparator());
    }

//    @Override
    public ArrayList<Action> solve() {
//        int explore = 0;
        SearchNode node = SearchNode.makeRootSearchNode(intial_state);
        State state = node.getState();

        // On commence à l'état initial
        frontier.add(node);

        // On initialise l'ensemble des nœuds déjà explorés a vide
        explored.clear();

        if (ArgParse.DEBUG)
            System.out.print("[" + state);

        while (!frontier.isEmpty()){
            // Stratégie: Astar
            node = frontier.poll();
//            System.out.println(node.getState());

            // Si le nœud contient un état but
            if (problem.isGoalState(node.getState())) {
                // On enregistre le nœud final
                end_node = node;
                // On retourne vrai
//                System.out.println("Noeuds explorés: "+explore);
                return end_node.getPathFromRoot();
            } else {
                // On ajoute l'état du nœud dans l'ensemble des nœuds explorés
                explored.add(node.getState());
//                System.out.println(node.getState());

                // Les actions possibles depuis cet état
                ArrayList<Action> actions = problem.getActions(node.getState());
//                System.out.println(actions);
                // Pour chaque nœud enfant
                for (Action a : actions) {
//                    explore++;
//                    System.out.println(a);
//                    System.out.println(node.getState());
                    // Nœud enfant
                    SearchNode child = SearchNode.makeChildSearchNode(problem, node, a);
//                    System.out.println(child.getHeuristic());
//                    System.out.println(child.getState());
                    // S'il n'est pas dans la frontière et si son état n'a pas été visité
                    if (!frontier.contains(child) && !explored.contains(child.getState())) {
                        // L'insérer dans la frontière avec la priorité du coût
                        frontier.add(child);
                    }else if(frontier.contains(child)){
                        // Si le nœud est déjà dans la frontière
                        // On récupère le nœud de la frontière
                        SearchNode frontier_node = frontier.stream().filter(n -> n.equals(child)).findFirst().get();
                        // Si la somme heuristique + cout du nœud enfant est inférieur à celle du nœud de la frontière
                        if((child.getHeuristic() + child.getCost()) < (frontier_node.getHeuristic() + frontier_node.getCost())){
                            // On le remplace
                            frontier.remove(frontier_node);
                            frontier.add(child);
                        }
                    }
                }
            }
        }
        System.out.println("Pas de solution trouvée.");
        // Pas de solutions trouvées
        return null;
    }

    // Comparator par heuristique + cout
    private static class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return Double.compare((node1.getHeuristic() + node1.getCost()), (node2.getHeuristic() + node2.getCost()));
        }
    }
}
