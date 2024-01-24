package com.overcooked.ptut.joueurs.ia.problemes.decentralisee;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlatStateDecentr extends State {

    public List<Aliment> visitees;

    public DonneesJeu donneesJeu;
    int[] coordonneesActuelles;

    int coutTot;

    public CalculHeuristiquePlatStateDecentr(DonneesJeu donneesJeu, int numJoueur) {
        this.donneesJeu = donneesJeu;
        if (donneesJeu.getJoueur(numJoueur).getInventaire() != null) {
            this.visitees = new ArrayList<>(donneesJeu.getJoueur(numJoueur).getInventaire().getRecettesComposees());
        } else {
            this.visitees = new ArrayList<>();
        }
        this.coordonneesActuelles = donneesJeu.getJoueur(numJoueur).getPosition();
        coutTot = 0;
    }

    public CalculHeuristiquePlatStateDecentr(DonneesJeu donneesJeu, List<Aliment> visiteesAncien, int[] coordonneesActuelles, int coutTot) {
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
        return new CalculHeuristiquePlatStateDecentr(donneesJeu, visitees, coordonneesActuelles, coutTot);
    }

    @Override
    protected boolean equalsState(State o) {
        //on vérifie les coordonnees actuelle et les aliments visités
        CalculHeuristiquePlatStateDecentr etat = (CalculHeuristiquePlatStateDecentr) o;
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
        //on parcout visite et on vérifie si il y a un nom en commun
        for (Aliment aliment : visitees) {
            if (Objects.equals(aliment.getNom(), a.getNom())) return false;
        }
        return true;
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

    public boolean aPoser() {
        return visitees.isEmpty() || Objects.equals(visitees.getLast().getNom(), "pdt");
    }

    public int[] getCoordonneeVisitePDT() { // TODO: pouvoir récupéré pls aliment
        int[] pdt = null;
        for (Aliment a : visitees) {
            if (Objects.equals(a.getNom(), "pdt")) {
                pdt = a.getCoordonnees();
            }else if (Objects.equals(a.getNom(), "pdt2")) {
                pdt = null;
            }
        }
        return pdt;
    }
}
