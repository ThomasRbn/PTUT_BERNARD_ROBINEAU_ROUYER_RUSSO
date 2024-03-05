package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.algo.BFS;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchNodeAC;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblem;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIAv2;
import com.overcooked.ptut.joueurs.ia.problemes.OvercookedUnJoueurIAv2State;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.CalculHeuristiquePlatDecentr;
import com.overcooked.ptut.joueurs.ia.problemes.decentralisee.CalculHeuristiquePlatStateDecentr;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class JoueurIADecentrV3 extends JoueurIA {

    DonneesJeu donneesJeuClone;

    private List<Plat> platsBut;
    private AlimentCoordonnees elementCible;
    private List<AlimentCoordonnees> listeActions;

    public JoueurIADecentrV3(int x, int y) {
        super(x, y);
    }

    public JoueurIADecentrV3(int x, int y, Plat inventaire, Action direction, int numJoueur) {
        super(x, y, inventaire, direction, numJoueur);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        this.donneesJeuClone = new DonneesJeu(donneesJeu);
        this.platsBut = donneesJeuClone.getPlatsBut();
        // créer un problème, un état initial et un algo

        SearchProblemAC p = new CalculHeuristiquePlatDecentr(donneesJeuClone.getPlatsBut().getFirst(), numJoueur, donneesJeuClone);
        State s = new CalculHeuristiquePlatStateDecentr(donneesJeuClone, numJoueur);


        BFS algo = new BFS(p, s);

        // résoudre
//        ArrayList<Action> solution = algo.solve();


        SearchNodeAC solution = algo.solve();
        SearchNodeAC derniereSolution = solution;
        this.listeActions = new ArrayList<>();
        //Boucle pour récupéré le dernier Aliment coordonnee du resultat
        while (solution != null) {
            listeActions.add(solution.getAlimentCoordonnees());
            solution = solution.getParent();
        }

        // Affichage listeActions
        for (AlimentCoordonnees action : listeActions)
            System.out.println(action.getAliment().getEtatNom() + " " + action.getCoordonnees()[0] + " " + action.getCoordonnees()[1]);

        genererAutresJoueurs(numJoueur);

        this.elementCible = listeActions.getLast();

        SearchProblem p2 = new OvercookedUnJoueurIAv2();
        State s2 = new OvercookedUnJoueurIAv2State(donneesJeuClone, numJoueur, elementCible.getAliment(), elementCible.getCoordonnees());
        AStar algoAstar = new AStar(p2, s2);
        List<Action> listeAction = algoAstar.solve();
        return listeAction.getFirst();
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
        if (!listeActions.isEmpty()) {
            action = listeActions.getLast();
        }
        if (action != null) {
            System.out.println("Suppression de : " + action.getAliment().getEtatNom());
            supprimerElementPlatBut(action.getAliment());
        }
    }

    public void supprimerElementPlatBut(Aliment aliment) {
        listeActions.removeIf(alimentCoordonnees -> alimentCoordonnees.getAliment().equals(aliment));
    }
}
