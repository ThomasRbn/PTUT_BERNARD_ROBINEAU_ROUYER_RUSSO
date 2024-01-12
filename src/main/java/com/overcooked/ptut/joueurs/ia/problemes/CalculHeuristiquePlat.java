package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;

public class CalculHeuristiquePlat extends SearchProblemAC {

    int[] coordonneesDepart;

    Plat platBut;

    AlimentCoordonnees depot;

    boolean retourDepot;

    public CalculHeuristiquePlat(Plat plat, int[] coordonneesDepart, DonneesJeu donneesJeu) {
        retourDepot = false;
        platBut = plat;
        List<AlimentCoordonnees> alimentCoordonneesList = new ArrayList<>();
        Plat inventaire = donneesJeu.getJoueur(0).getInventaire();
        int num = 0;
        depot = new AlimentCoordonnees(new Aliment("Depot", "Aliment fictif"), donneesJeu.getCoordonneesDepot());
        if (inventaire != null && platBut.equals(inventaire)) {
            retourDepot = true;
            return;
        }

        for (Aliment a : plat.getRecettesComposees()) {
            if (inventaire != null && inventaire.getRecettesComposees().contains(a)) {
                continue;
            }
            List<int[]> listeCoordonnees = donneesJeu.getCoordonneesAliment(a);
            for (int[] coordonnees : listeCoordonnees) {
                alimentCoordonneesList.add(new AlimentCoordonnees(a, coordonnees));
                num++;
            }
        }

        ALIMENTCO = alimentCoordonneesList.toArray(new AlimentCoordonnees[0]);
    }

    @Override
    public ArrayList<AlimentCoordonnees> getAlimentCoordonnees(State s) {
        ArrayList<AlimentCoordonnees> actions = new ArrayList<>();
        if (isDerniereAction(s)) {
            actions.add(depot);
            return actions;
        }
        for (AlimentCoordonnees a : ALIMENTCO) {
            if (((CalculHeuristiquePlatState) s).isLegal(a.getAliment())) {
                actions.add(a);
            }
        }
        return actions;
    }

    @Override
    public State doAlimentCoordonnees(State s, AlimentCoordonnees a) {
        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s.clone();
        o.deplacement(a.getCoordonnees(), a.getAliment());
        return o;
    }

    @Override
    public boolean isGoalState(State s) {
        return ((CalculHeuristiquePlatState) s).estAuDepot();
    }

    public boolean isDerniereAction(State s) {
        if(retourDepot){
            return true;
        }
        CalculHeuristiquePlatState o = (CalculHeuristiquePlatState) s;
        List<Aliment> aliments = o.visitees;
        List<Aliment> alimentBut = platBut.getRecettesComposees();
        if (aliments.size() != alimentBut.size()) {
            return false;
        }
        for (Aliment a : alimentBut) {
            if (!aliments.contains(a)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getAlimentCost(State s, AlimentCoordonnees a) {
        if(((CalculHeuristiquePlatState)s).estDepose()){
            return 0;
        }
        int[] coordonneesActuelle = ((CalculHeuristiquePlatState) s).getCoordonneesActuelles();
        int[] coordonneesAliment = a.getCoordonnees();
        return Math.abs(coordonneesActuelle[0] - coordonneesAliment[0]) + Math.abs(coordonneesActuelle[1] - coordonneesAliment[1]);
    }
}