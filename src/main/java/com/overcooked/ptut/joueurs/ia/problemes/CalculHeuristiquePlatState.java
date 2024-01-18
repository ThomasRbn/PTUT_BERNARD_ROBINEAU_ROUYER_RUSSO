package com.overcooked.ptut.joueurs.ia.problemes;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlatState extends State {

    List<Aliment> visitees;

    DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu) {
        this.donneesJeu = donneesJeu;
        if (donneesJeu.getJoueur(0).getInventaire() != null) {
            this.visitees = new ArrayList<>(donneesJeu.getJoueur(0).getInventaire().getRecettesComposees());
        } else {
            this.visitees = new ArrayList<>();
        }
        this.coordonneesActuelles = donneesJeu.getJoueur(0).getPosition();
        coutTot = 0;
    }

    public CalculHeuristiquePlatState(DonneesJeu donneesJeu, List<Aliment> visiteesAncien, int[] coordonneesActuelles, int coutTot) {
        this.donneesJeu = donneesJeu;
        this.visitees = new ArrayList<>();
        for (Aliment aliment : visiteesAncien) {
            this.visitees.add(new Aliment(aliment));
        }
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
        if (visitees.size() != etat.visitees.size()) {
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
        if (Objects.equals(a.getNom(), "Decoupe")) {
            visitees.getLast().decouper();
        } else if (Objects.equals(a.getNom(), "Cuisson")) {
            visitees.getLast().cuire();
        } else {
            visitees.add(a);
        }
        coordonneesActuelles = nouvelleCo;
    }

    public int[] getCoordonneesActuelles() {
        return coordonneesActuelles;
    }

    public boolean estAuDepot() {
        return !visitees.isEmpty() && visitees.getLast().getEtatNom() == "Depot";
    }

    public boolean estDepose() {
        return !donneesJeu.getPlatDepose().isEmpty();
    }

    public boolean doitCuire(Plat platBut) {
        if (visitees.isEmpty()) return false;
        return visitees.getLast().doitCuire(platBut);

    }

    public boolean doitEtreCoupe(Plat platBut) {
        if (visitees.isEmpty()) return false;
        return visitees.getLast().doitEtreCoupe(platBut); // this
    }
}
