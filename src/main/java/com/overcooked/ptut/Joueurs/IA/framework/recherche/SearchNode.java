package com.overcooked.ptut.joueurs.ia.framework.recherche;


import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.utilitaire.Action;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Représente un nœud de l'arbre de recherche
 *
 */

public class SearchNode {
    /**
     * Un compteur de nœuds créés
     */
    protected static int COUNT = 0;
    
    /**
     * La profondeur max atteinte
     */
    protected static int DEPTH = 0; 

    /**
     * Un id pour les différencier à l'affichage 
     */
    protected int id;
    
    /**
     * Le nœud parent de ce nœud
     */
    protected SearchNode parent; 

    /**
     * L'état stocké dans le nœud
     */
    protected State state;

    /**
     * L'action depuis le parent a ici 
     */
    protected Action action;
    
    /**
     * La profondeur du nœud
     */
    protected int depth; 

    /**
     * Le coût du chemin depuis la racine à moi
     */
    private double path_cost;

    /** 
     * @return Le nombre total de nœuds créés (visités)
     */
    public static int getTotalSearchNodes() {
        return COUNT;
    }

    /** 
     * @return La profondeur maximum atteinte 
     */
    public static int getMaxDepth() {
        return DEPTH;
    }

    /**
     * Factory pour créer un nœud racine correspondant à l'état
     * initial 
     * <p>Le parent est null le coût=0, et pas d'action arrivant
     * ici </p>
     * @param s L'état initial  
     * @return Le nœud correspondant à l'état initial
     */
    
    public static SearchNode makeRootSearchNode(State s){
        return new SearchNode(null, s, null, 0);
    }

    /** 
     * Factory pour créer un nœud de l'arbre de recherche
     * @param pb Le problème
     * @param par Le parent de ce nœud
     * @param a L'action exécutée sur le parent qui arrive ici
     */
    public static SearchNode makeChildSearchNode(SearchProblem pb,
                                                 SearchNode par,
                                                 Action a) {
        // on exécute l'action et calcul le coût
        
        return new SearchNode(par,
                              pb.doAction(par.getState(), a), a,
                              pb.getActionCost(par.getState(), a) +
                              par.getCost());
    }

    /**
     * Crée un nœud
     * @param p Le nœud parent
     * @param s L'état de ce nœud
     * @param a L'action qui mène du parent a ce nœud
     * @param c Le coût de faire l'action 
     */
    public SearchNode(SearchNode p, State s, Action a, double c){
        id = COUNT++;
        state = s;
        action = a;
        parent = p;
        depth = 0;
        if (p != null) // on n'est pas une racine
            depth = p.getDepth() + 1; 
        if (depth > DEPTH)
            DEPTH=depth;
        
        path_cost = c;
    }
    
    /**
     * @return L'id de ce nœud
     */
    
    public int getId(){
        return id;
    }
    
    /**
     * @return La profondeur de ce nœud
     */
    
    public int getDepth(){
        return depth;
    }
    
    /**
     * @return Le parent de ce nœud
     */

    public SearchNode getParent(){
        return parent;
    }

    /**
     * @return L'action qui mène à ce nœud
     */
    
    public Action getAction(){
        return action;
    }

    /** 
     * @return L'état du problème de ce nœud
     */
    
    public State getState(){
        return state;
    }

    /**
     * @return Le coût du chemin de la racine à ce nœud
     */
    
    public double getCost(){
        return path_cost;
    }

    /**
     * Calcule l'heuristique (sous-estimation du coût au but)
     * <p>Valable uniquement pour certain type de problèmes
     * (implantant {@link HasHeuristic}),
     * pour les autres on retourne zéro.</p>
     * @return La valeur l'heuristique
     */
    
    public double getHeuristic(){
        if(!(state instanceof HasHeuristic))
            return 0;
        return ((HasHeuristic) state).getHeuristic(); 
    }

    /**
     * @return Une représentation du nœud
     */
    public String toString() {
        if(parent != null)
            return "("+id+", " + parent.getId() + ", " +path_cost+")";
        return "("+id+", root, " +path_cost+")";
    }

    /**
     * Test l'égalité avec un autre nœud
     * <p>On test l'égalité sur les états</p>
     * @param o L'autre nœud
     * @return Vrai si les deux nœuds contiennent des états égaux
     */
    
    @Override
	public boolean equals(Object o) {
		if (o != null && getClass() == o.getClass()) {
			SearchNode other = (SearchNode) o;
			return state.equals(other.getState());  
		}
		return false;
	}       
   
    /**
     * Calcule le chemin (la liste d'actions) depuis la racine à
     * ce nœud.
     * <p>Le chemin est construit en suivant les parents, jusqu'à la
     * racine. </p>
     *  
     * @return la liste d'action deoui l
     */
    
    public ArrayList<Action> getPathFromRoot(){
        ArrayList<SearchNode> path = new ArrayList<>();
        SearchNode curent = this;

        // Aller à la racine
        do{
            path.add(curent);
            curent = curent.getParent();
        } while (curent != null);

        // Inverser pour commencer par la racine
        Collections.reverse(path);

        // Extraire les actions du chemin
        ArrayList<Action> solution = new ArrayList<>();
        for(SearchNode n: path)
            if (n.getParent() != null) // ignorer la racine 
                solution.add(n.getAction());

        return solution;
    }
}
    
    
        
        


