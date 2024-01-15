package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.recettes.aliment.Aliment;

import java.util.ArrayList;
import java.util.List;

public class CalculHeuristiquePlatState extends State {

    List<Aliment> visitees;

    DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu) {
        this.donneesJeu = donneesJeu;
        this.visitees = new ArrayList<>();
        this.coordonneesActuelles = donneesJeu.getJoueur(0).getPosition();
        coutTot = 0;
    }

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu, List<Aliment> visitees, int[] coordonneesActuelles, int coutTot) {
        this.donneesJeu = donneesJeu;
        this.visitees = new ArrayList<>(visitees);
        this.coordonneesActuelles = coordonneesActuelles.clone();
        this.coutTot = coutTot;
    }

    @Override
    protected State cloneState() {
        return new CalculHeuristiquePlatState(donneesJeu, visitees, coordonneesActuelles, coutTot);
    }

    @Override
    protected boolean equalsState(State o) {
        //on vérifie les coordonnees actuelle et les aliments visités
        CalculHeuristiquePlatState etat = (CalculHeuristiquePlatState) o;
        if (coordonneesActuelles[0] != etat.coordonneesActuelles[0] || coordonneesActuelles[1] != etat.coordonneesActuelles[1]) {
            return false;
        }
        if(visitees.size() != etat.visitees.size()){
            return false;
        }
        for (Aliment a : visitees) {
            if (!etat.visitees.contains(a)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected int hashState() {
        return 0;
    }

    public boolean isLegal(Aliment a) {
        return !visitees.contains(a);
    }

    public void deplacement(int[] nouvelleCo, Aliment a) {
        visitees.add(a);
        coordonneesActuelles = nouvelleCo;
    }

    public int[] getCoordonneesActuelles() {
        return coordonneesActuelles;
    }

    public boolean estAuDepot(){
        return !visitees.isEmpty() && visitees.getLast().getNom() == "Depot";
    }

    public boolean estDepose(){
        return !donneesJeu.getPlatDepose().isEmpty();
    }

}
