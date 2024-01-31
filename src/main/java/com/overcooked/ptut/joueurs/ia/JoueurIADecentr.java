package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.algo.BFS;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchNodeAC;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.CalculHeuristiquePlatDecentr;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.CalculHeuristiquePlatStateDecentr;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.OvercookedUnJoueurIADecentr;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.OvercookedUnJoueurIAStateDecentr;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class JoueurIADecentr extends JoueurIA {

    DonneesJeu donneesJeuClone;

    private List<Plat> platsBut;

    public JoueurIADecentr(int x, int y) {
        super(x, y);
    }

    public JoueurIADecentr(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        this.donneesJeuClone = new DonneesJeu(donneesJeu);
        this.platsBut = donneesJeuClone.getPlatsBut();
        // créer un problème, un état initial et un algo
        SearchProblem p = new OvercookedUnJoueurIADecentr();

        if (inventaire == null)
            genererAutresJoueurs(numJoueur);

        State s = new OvercookedUnJoueurIAStateDecentr(donneesJeuClone, numJoueur);
        AStar algo = new AStar(p, s);
        List<AlimentCoordonnees> listeActions = new ArrayList<>();
        // Boucle pour récupéré le dernier Aliment coordonnee du resultat
        ArrayList<Action> solution = algo.solve();

        SearchProblemAC prob = new CalculHeuristiquePlatDecentr(this.platsBut.getFirst(), numJoueur, this.donneesJeuClone);
        State state = new CalculHeuristiquePlatStateDecentr(this.donneesJeuClone, numJoueur);
        BFS algo2 = new BFS(prob, state);
        // résoudre
        SearchNodeAC sol = algo2.solve();
        List<AlimentCoordonnees> lisAction = new ArrayList<>();
        // Boucle pour récupéré le dernier Aliment coordonnee du resultat
        while (sol != null && sol.getAlimentCoordonnees() != null) {
            lisAction.add(sol.getAlimentCoordonnees());
            sol = sol.getParent();
        }
        AlimentCoordonnees action = null;
        // Affichage lisAction
        if (!lisAction.isEmpty()) {
            action = lisAction.getLast();
            System.out.println(action.getAliment().getEtatNom() + " " + action.getCoordonnees()[0] + " " + action.getCoordonnees()[1]);
        }

        // résoudre
        return solution != null ? solution.getFirst() : Action.RIEN;
    }

    private void genererAutresJoueurs(int numJoueur) {
        // Trouver les joueurs autres que celui que nous controlons
        if (this.donneesJeuClone.getJoueurs().size() > 1) {
            for (Joueur j : this.donneesJeuClone.getJoueurs()) {
                if (j.getNumJoueur() != numJoueur && j.getInventaire() == null) {
                    trouverCible(j);
                }
            }
        }
    }

    private void trouverCible(Joueur j) {
        int numJoueurOther = j.getNumJoueur();
        int[] positionOtherJoueur = this.donneesJeuClone.getJoueur(numJoueurOther).getPosition();
        SearchProblemAC p = new CalculHeuristiquePlatDecentr(this.platsBut.getFirst(), numJoueurOther, this.donneesJeuClone);
        State s = new CalculHeuristiquePlatStateDecentr(this.donneesJeuClone, numJoueurOther);
        BFS algo = new BFS(p, s);
        // résoudre
        SearchNodeAC solution = algo.solve();
        List<AlimentCoordonnees> listeActions = new ArrayList<>();
        // Boucle pour récupéré le dernier Aliment coordonnee du resultat
        while (solution != null && solution.getAlimentCoordonnees() != null) {
            listeActions.add(solution.getAlimentCoordonnees());
            solution = solution.getParent();
        }
        AlimentCoordonnees action = null;
        // Affichage listeActions
        if (!listeActions.isEmpty()) {
            action = listeActions.getLast();
            //listeActions.forEach(alimentCoordonnees -> {
            //    System.out.println(alimentCoordonnees.getAliment().getEtatNom() + " " + alimentCoordonnees.getCoordonnees()[0] + " " + alimentCoordonnees.getCoordonnees()[1]);
            //});
            //System.out.println(action.getAliment().getEtatNom() + " " + action.getCoordonnees()[0] + " " + action.getCoordonnees()[1]);
        }
        if (action != null) {
            //System.out.println("Suppression de : " + action.getAliment().getEtatNom());
            supprimerElementPlatBut(action.getAliment());
        }
    }

    public void supprimerElementPlatBut(Aliment aliment) {
        for (Plat plat : this.platsBut) {
            plat.supprimerAliment(aliment);
            return;
        }
    }
}
