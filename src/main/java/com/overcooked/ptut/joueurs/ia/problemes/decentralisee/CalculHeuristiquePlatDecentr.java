package com.overcooked.ptut.joueurs.ia.problemes.decentralisee;

import com.overcooked.ptut.constructionCarte.DonneesJeu;
import com.overcooked.ptut.joueurs.Joueur;
import com.overcooked.ptut.joueurs.ia.framework.common.State;
import com.overcooked.ptut.joueurs.ia.framework.recherche.SearchProblemAC;
import com.overcooked.ptut.joueurs.utilitaire.AlimentCoordonnees;
import com.overcooked.ptut.recettes.aliment.Aliment;
import com.overcooked.ptut.recettes.aliment.Plat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CalculHeuristiquePlatDecentr extends SearchProblemAC {

    int[] coordonneesDepart;

    Plat platBut;

    AlimentCoordonnees depot;

    List<int[]> listeCoordonneesCuisson;

    List<int[]> listeCoordonneesPlanche;
    boolean retourDepot;

    int numJoueur;

    DonneesJeu donneesJeu;

    public CalculHeuristiquePlatDecentr(Plat plat, int[] coordonneesDepart, DonneesJeu donneesJeu, int numJoueur) {
        this.numJoueur = numJoueur;
        this.donneesJeu = donneesJeu;
        retourDepot = false;
        platBut = plat;
        List<AlimentCoordonnees> alimentCoordonneesList = new ArrayList<>();
        Plat inventaire = donneesJeu.getJoueur(0).getInventaire();
        int num = 0;
        depot = new AlimentCoordonnees(new Aliment("Depot", "Aliment fictif"), donneesJeu.getCoordonneesElement("Depot").get(0));
        if (inventaire != null && platBut.equals(inventaire)) {
            retourDepot = true;
            return;
        }
        listeCoordonneesCuisson = donneesJeu.getCoordonneesElement("Cuisson");
        listeCoordonneesPlanche = donneesJeu.getCoordonneesElement("Planche");
        for (Aliment a : plat.getRecettesComposees()) {
            if (inventaire != null) {
                boolean continu = false;
                for (Aliment aliment : inventaire.getRecettesComposees()) {
                    if (aliment.equalsType(a)) {
                        continu = true;
                    }
                }
                if (continu) continue;
            }
            List<int[]> listeCoordonneesEtatNom = donneesJeu.getCoordonneesElement(a.getEtatNom());
            if (!listeCoordonneesEtatNom.isEmpty()) {
                for (int[] coordonnees : listeCoordonneesEtatNom) {
                    alimentCoordonneesList.add(new AlimentCoordonnees(a, coordonnees));
                    num++;
                }
            } else {
                if(a.getEtat() == 3){
                    List<int[]> listeCoordonneesEtat2 = donneesJeu.getCoordonneesElement(a.getNom()+"2");//si 3, aller prendre 2
                    if (!listeCoordonneesEtat2.isEmpty()) {
                        for (int[] coordonnees : listeCoordonneesEtat2) {
                            Aliment aliment = new Aliment(a.getNom());
                            aliment.setEtat(2);
                            alimentCoordonneesList.add(new AlimentCoordonnees(aliment, coordonnees));
                            num++;
                        }
                        break;
                    }
                }
                List<int[]> listeCoordonnees = donneesJeu.getCoordonneesElement(a.getNom());
                for (int[] coordonnees : listeCoordonnees) {
                    alimentCoordonneesList.add(new AlimentCoordonnees(new Aliment(a.getNom()), coordonnees));
                    num++;
                }
            }
        }


        ALIMENTCO = alimentCoordonneesList.toArray(new AlimentCoordonnees[0]);


    }

    @Override
    public ArrayList<AlimentCoordonnees> getAlimentCoordonnees(State s) {
        ArrayList<AlimentCoordonnees> actions = new ArrayList<>();
        CalculHeuristiquePlatStateDecentr o = (CalculHeuristiquePlatStateDecentr) s;
        if (isDerniereAction(s)) {
            int[] pdt = o.getCoordonneeVisitePDT();
            if(pdt == null) {
                actions.add(depot);
            }else {
                // On récupère l'aliment sur le plan de travail
                actions.add(new AlimentCoordonnees(new Aliment("pdt2", "Aliment ficitif", pdt), pdt));
            }
            return actions;

        }
        if (o.doitEtreCoupe(platBut)) {
            Aliment alimentFictifDecoupe = new Aliment("Decoupe", "Aliment fictif");
            for (int[] coordonnees : listeCoordonneesPlanche) {
                actions.add(new AlimentCoordonnees(alimentFictifDecoupe, coordonnees));
            }
            return actions;
        }

        if (o.doitCuire(platBut)) {
            Aliment alimentFictifCuisson = new Aliment("Cuisson", "Aliment fictif");
            for (int[] coordonnees : listeCoordonneesCuisson) {
                actions.add(new AlimentCoordonnees(alimentFictifCuisson, coordonnees));
            }
            return actions;
        }
        for (AlimentCoordonnees a : ALIMENTCO) {
            if (o.isLegal(a.getAliment())) {
                if ((a.getAliment().doitCuire(platBut) || a.getAliment().doitEtreCoupe(platBut)) && !o.aPoser()) {
                    int[] coordonnees = donneesJeu.getPlanDeTravailVidePlusProche(o.getCoordonneesActuelles());
                    actions.add(new AlimentCoordonnees(new Aliment("pdt", "Aliment ficitif", coordonnees), coordonnees));
                } else {
                    actions.add(a);
                }
            }
        }
        return actions;
    }

    @Override
    public State doAlimentCoordonnees(State s, AlimentCoordonnees a) {
        CalculHeuristiquePlatStateDecentr o = (CalculHeuristiquePlatStateDecentr) s.clone();
        o.deplacement(a.getCoordonnees(), a.getAliment());
        if(o.doitCuire(platBut)){
            for (int[] coordonnees : listeCoordonneesCuisson){
                if(coordonnees[0] == a.getCoordonnees()[0] && coordonnees[1] == a.getCoordonnees()[1]){
                    o.deplacement(coordonnees, new Aliment("Cuisson", "Aliment fictif"));
                    break;
                }
            }
        }
        if(o.doitEtreCoupe(platBut)){
            for (int[] coordonnees : listeCoordonneesPlanche){
                if(coordonnees[0] == a.getCoordonnees()[0] && coordonnees[1] == a.getCoordonnees()[1]){
                    o.deplacement(coordonnees, new Aliment("Decoupe", "Aliment fictif"));
                    break;
                }
            }
        }
        return o;
    }

    @Override
    public boolean isGoalState(State s) {
        return ((CalculHeuristiquePlatStateDecentr) s).estAuDepot();
    }

    public boolean isDerniereAction(State s) {
        if (retourDepot) {
            return true;
        }
        CalculHeuristiquePlatStateDecentr o = (CalculHeuristiquePlatStateDecentr) s;
        List<Aliment> aliments = o.visitees;
        List<Aliment> alimentBut = platBut.getRecettesComposees();

        List<Aliment> alimentSansPDT = new ArrayList<>();

        for (Aliment a:aliments) {
            if(!(Objects.equals(a.getNom(), "pdt") || Objects.equals(a.getNom(), "pdt2"))){
                alimentSansPDT.add(a);
            }
        }

        if (alimentSansPDT.size() != alimentBut.size()) {
            return false;
        }
        for (Aliment a : alimentBut) {
            if (!alimentSansPDT.contains(a)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public double getAlimentCost(State s, AlimentCoordonnees a) {
        if (((CalculHeuristiquePlatStateDecentr) s).estDepose()) {
            return 0;
        }
        int[] coordonneesActuelle = ((CalculHeuristiquePlatStateDecentr) s).getCoordonneesActuelles();
        int[] coordonneesAliment = a.getCoordonnees();
        return Math.abs(coordonneesActuelle[0] - coordonneesAliment[0]) + Math.abs(coordonneesActuelle[1] - coordonneesAliment[1]);
    }


    public double getJoueurCost(State s, Joueur j) {
        if (((CalculHeuristiquePlatStateDecentr) s).estDepose()) {
            return 0;
        }
        int[] coordonneesActuelle = ((CalculHeuristiquePlatStateDecentr) s).getCoordonneesActuelles();
        int[] coordonneesJoueur = j.getPosition();
        return Math.abs(coordonneesActuelle[0] - coordonneesJoueur[0]) + Math.abs(coordonneesActuelle[1] - coordonneesJoueur[1]);
    }
}
