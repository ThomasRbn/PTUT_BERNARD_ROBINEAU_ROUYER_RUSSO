package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.framework.common.ArgParse;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIA;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIAState;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;

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
        // créer un problème, un état initial et un algo
        SearchProblem p = new OvercookedUnJoueurIA();
        State s = new OvercookedUnJoueurIAState(donneesJeu, numJoueur);
        AStar algo = (AStar) ArgParse.makeAlgo("astar", p, s);

        // résoudre
        ArrayList<Action> solution = algo.solve();
        return solution != null ? solution.getFirst() : Action.RIEN;
    }
}
