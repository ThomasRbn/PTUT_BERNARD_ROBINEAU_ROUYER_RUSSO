package com.overcooked.ptut.joueurs.ia;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.algo.AStar;
import com.overcooked.ptut.joueurs.ia.algo.UCS;
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

    private static boolean conditionSuppressionElement(Aliment aliment, AlimentCoordonnees alimentCoordonnees) {
        return alimentCoordonnees.getAliment().equals(aliment);
    }

    // Utilisera Problème
    @Override
    public Action demanderAction(DonneesJeu donneesJeu) {
        this.donneesJeuClone = new DonneesJeu(donneesJeu);
        this.platsBut = donneesJeuClone.getPlatsBut();
        // créer un problème, un état initial et un algo

        SearchProblemAC p = new CalculHeuristiquePlatDecentr(donneesJeuClone.getPlatsBut().getFirst(), numJoueur, donneesJeuClone);
        State s = new CalculHeuristiquePlatStateDecentr(donneesJeuClone, numJoueur);

        UCS algo = new UCS(p, s);

        // résoudre
//        ArrayList<Action> solution = algo.solve();

        SearchNodeAC solution = algo.solve();
        SearchNodeAC derniereSolution = solution;
        this.listeActions = new ArrayList<>();
        //Boucle pour récupéré le dernier Aliment coordonnee du resultat
        try {
            while (solution.getAlimentCoordonnees() != null) {
                listeActions.add(solution.getAlimentCoordonnees());
                solution = solution.getParent();
            }
        } catch (Exception ignored) {
        }

        // Affichage listeActions
        genererAutresJoueurs(numJoueur);

        try {
            this.elementCible = listeActions.getLast();
        } catch (Exception e) {
        }

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
        UCS algo = new UCS(p, s);
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
            supprimerElementLisAction(action.getAliment());
        }
        //listeActions.forEach(System.out::println);
    }

    public void supprimerElementLisAction(Aliment aliment) {
        listeActions.removeIf(alimentCoordonnees -> conditionSuppressionElement(aliment, alimentCoordonnees));
    }
}
