package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.constructionCarte.GestionActions;
import com.overcooked.ptut.joueurs.ia.algo.BFS;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.HasHeuristic;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.utilitaire.Action;
import com.overcooked.ptut.recettes.aliment.Plat;

import static com.overcooked.ptut.constructionCarte.ComparateurDonneesJeu.ComparerDonneesJeu;

public class OvercookedUnJoueurIAState extends State implements HasHeuristic {

    //Données
    private DonneesJeu donnees;

    private int numJoueur;

    /**
     * Constructeur
     *
     * @param donneesJeu Données du jeu
     * @param numJoueur  Numéro du joueur courant
     */
    public OvercookedUnJoueurIAState(DonneesJeu donneesJeu, int numJoueur) {
        this.numJoueur = numJoueur;
        //Constructeur par copie
        this.donnees = new DonneesJeu(donneesJeu);
    }

    public DonneesJeu getDonnees() {
        return donnees;
    }

    /**
     * Retourne une copie de l'état
     */
    @Override
    protected State cloneState() {
        return new OvercookedUnJoueurIAState(donnees, numJoueur);
    }

    /**
     * Retourne vrai si l'état o est égal à l'état courant
     */
    @Override
    protected boolean equalsState(State o) {
        return ComparerDonneesJeu(donnees, ((OvercookedUnJoueurIAState) o).getDonnees());
    }

    @Override
    protected int hashState() {
        return 0;
    }

    /**
     * Retourne l'heuristique de l'état courant
     */
    @Override
    public double getHeuristic() {
        double coutMin = Double.MAX_VALUE;
        // On parcours l'ensemble des plats but
        for (Plat plat : donnees.getPlatsBut()) {
            //On calcule le cout de l'état courant pour chaque plat
            SearchProblemAC p = new CalculHeuristiquePlat(plat, donnees.getJoueur(numJoueur).getPosition(), donnees);
            State s = new CalculHeuristiquePlatState(donnees);
            BFS algo = new BFS(p, s);
            double cout = algo.solve();
            //On garde le cout minimum
            if (cout < coutMin) {
                coutMin = cout;
            }
        }

        // résoudre
        return coutMin;
    }

    public String toString() {
        return donnees.toString();
    }

    /**
     * Retourne vrai si l'action a est légale dans l'état courant
     */
    public boolean isLegal(Action a) {
        return GestionActions.isLegal(a, numJoueur, donnees);
    }

    /**
     * Applique l'action a dans l'état courant
     */
    public void faireAction(Action a) {
        GestionActions.faireAction(a, numJoueur, donnees);
    }

    public boolean isGoalState() {
        //On vérifie que le premier plat déposé correspond au premier plat de la liste but
        return !donnees.getPlatDepose().isEmpty() && donnees.getPlatDepose().get(0).equals(donnees.getPlatsBut().get(0));
    }
}
